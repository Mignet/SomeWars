package com.v5ent.game.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.core.Assets;
import com.v5ent.game.core.Assets.AssetHero;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.Transform;

public class Hero extends Sprite{
	
	private static final String TAG = Hero.class.getName();
	/**---待机、行走、施法、攻击、被打、死亡、僵直---**/
	public enum State {
		IDLE, WALKING,MAGIC,FIGHT,BEATEN,DEAD,STIFF
	}
	
	public enum Buff {
		ICE,STONE, TOXICOSIS,FIRE
	}

	public enum Direction {
		UP,DOWN,RIGHT, LEFT;
	}
	
	private String id;
	private String name;
	private String desc;
	/**可移动范围(0,0)-(1,0),(2,0) */
	private List<Vector2> moveRange = new ArrayList<Vector2>();
	/**攻击范围(0,0)-(1,0),(2,0) */
	private List<Vector2> fightRange = new ArrayList<Vector2>();
	/**在地图上的位置坐标*/
	private int mapX;
	private int mapY;
	
	/** 是否被选中 */
	private boolean selected;
	/** 我方英雄*/
	private boolean good;
	protected float frameTime = 0f;
	/**当前帧*/
	private TextureRegion currentFrame = null;
	/**当前状态 */
	private State currentState = State.IDLE;
	
	/** 当前朝向 */
	private Direction currentDir = Direction.RIGHT;
	private Vector2 nextPosition;
	/** 速度 */
	private float speed = 1f;//一格一秒
	//LVL - The tile's level. The tiles level up when it gain some experience.
	private int	level = 1;
	//STR - The tile's Strength. Strength affects the damages.
	private int	strength = 100;
	private int	life = 100;
	private int	maxlife = 100;
	//DEF - The tile's defence. It represents avarage defence against physical attack.
	//The tiles also have magic deffence.
	private int	defend = 50;
	//DEXterity - decide the order of actions
	private int	dexterity = 20;
	
	private Animation idleRightAnimation;
	private Animation walkRightAnimation;
//	private Animation walkRightAnimation;

	public Hero(String id) {
		AssetHero ah = Assets.instance.assetHeros.get(id);
		idleRightAnimation = ah.idleRightAnimation;
		walkRightAnimation = ah.walkRightAnimation;
		currentFrame =idleRightAnimation.getKeyFrame(0);
		// Define sprite size to be 1m x 1m in game world
		this.setSize(currentFrame.getRegionWidth()/Constants.RV_RATIO, currentFrame.getRegionHeight()/Constants.RV_RATIO);
		// Set origin to sprite's center
		this.setOrigin(this.getWidth() / 2.0f, 0);
		nextPosition = new Vector2(this.getX(),this.getY());
		good = true;
		moveRange.add(new Vector2(0,1));
		moveRange.add(new Vector2(0,-1));
		moveRange.add(new Vector2(1,0));
		moveRange.add(new Vector2(-1,0));
		fightRange.add(new Vector2(0,2));
		fightRange.add(new Vector2(1,1));
		fightRange.add(new Vector2(2,0));
		fightRange.add(new Vector2(1,-1));
		fightRange.add(new Vector2(0,-2));
	}
	float elapsed = 0.01f;
	/** 需要移动到的目标位置**/
	private float targetX;
	private float targetY;
	private int targetMapX;
	private int targetMapY;
	public void update(float delta) {
		frameTime = (frameTime + delta) % 4; // Want to avoid overflow
		if(this.currentState==State.WALKING){
			calculateNextPosition(delta);
			setNextPositionToCurrent();
			if(Math.abs(this.nextPosition.x-this.targetX)<speed*delta && Math.abs(this.nextPosition.y-this.targetY)<speed*delta){
				this.currentState= State.IDLE;
				this.setMapPosition(targetMapX,targetMapY);
			}
		}
	}
	
	@Override
		public void draw(SpriteBatch batch) {
	//		super.draw(batch);
			TextureRegion reg = null;
	
			// Draw Particles
	//		dustParticles.draw(batch);
	
			// Set special color when game object has a feather power-up
			if (selected) {
				batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
			}
	
			// Draw image
			updateCurrentFrame();
			reg = currentFrame;
	//		batch.draw(currentFrame.getTexture(),getX(), getY(),getWidth(),getHeight());
			batch.draw(reg.getTexture(), (Constants.CELL_WIDTH-getWidth())/2+getX(), getY(),getOriginX(), getOriginY(), getWidth(),getHeight(), getScaleX(), getScaleY(),
				getRotation(), reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),!good, false);
//			Gdx.app.debug(TAG, "hero's coor:"+getX()+","+getY());
			// Reset color to white
			batch.setColor(1, 1, 1, 1);
		}

	public void updateCurrentFrame() {
		// Look into the appropriate variable when changing position
		switch (currentState) {
		case IDLE:
			currentFrame = idleRightAnimation.getKeyFrame(frameTime);
			break;
		case WALKING:
			currentFrame = walkRightAnimation.getKeyFrame(frameTime);
			break;
		default:
			currentFrame = idleRightAnimation.getKeyFrame(frameTime);
			break;
		}
	}
	//当没有障碍物时使用
	public void setNextPositionToCurrent() {
		setPosition(nextPosition.x, nextPosition.y);
//		setMapPosition(Transform.positionInMapX(nextPosition.x),Transform.positionInMapY(nextPosition.y));
	}

	public void calculateNextPosition(float deltaTime) {
		float testX = this.getX();
		float testY = this.getY();
		speed *=(deltaTime);
		switch (currentDir) {
		case LEFT:
			testX -= speed;
			break;
		case RIGHT:
			testX += speed;
			break;
		case UP:
			testY += speed;
			break;
		case DOWN:
			testY -= speed;
			break;
		default:
			break;
		}
		nextPosition.x = testX;
		nextPosition.y = testY;
		Gdx.app.debug(TAG, "nextPosition:"+nextPosition);
		// velocity
		speed *=(1 / deltaTime);
	}
	
	public int getMapX() {
		return mapX;
	}
	public void setMapX(int mapX) {
		this.mapX = mapX;
	}
	public int getMapY() {
		return mapY;
	}
	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public List<Vector2> getMoveRange() {
		return moveRange;
	}
	public List<Vector2> getFightRange() {
		return fightRange;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the good
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * @param good the good to set
	 */
	public void setGood(boolean good) {
		this.good = good;
	}

	public void setMapPosition(int x, int y) {
		this.mapX = x;
		this.mapY = y;
		this.setPosition(Transform.positionInWorldX(x), Transform.positionInWorldY(y));
	}

	public void setDirection(Direction dir) {
		this.currentDir = dir;
	}

	public void moveTo(int x, int y) {
		if(x>mapX){
			currentDir = Direction.RIGHT;
		}
		if(x<mapX){
			currentDir = Direction.LEFT;
		}
		if(y>mapY){
			currentDir = Direction.UP;
		}
		if(y<mapY){
			currentDir = Direction.DOWN;
		}
		Gdx.app.debug(TAG, "("+this.mapX+","+this.mapY+") move to:("+x+","+y+")"+currentDir);
		this.currentState=State.WALKING;
		this.targetMapX = x;
		this.targetMapY = y;
		this.targetX = Transform.positionInWorldX(x);
		this.targetY = Transform.positionInWorldY(y);
		Gdx.app.debug(TAG, "("+this.getX()+","+this.getY()+") move to target:("+this.targetX+","+this.targetY+")");
	}
	
}
