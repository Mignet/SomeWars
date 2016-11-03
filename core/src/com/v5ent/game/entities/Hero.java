package com.v5ent.game.entities;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.core.Assets;
import com.v5ent.game.core.Assets.AssetHero;

public class Hero extends Sprite{
	
	private static final String TAG = Hero.class.getName();
	private static final String defaultSpritePath = "sprites/characters/Warrior.png";
	/**---僵直**/
	public enum State {
		IDLE, WALKING,MAGIC,FIGHT,BEATEN,DEAD,STIFF
	}

	public enum Direction {
		UP, RIGHT, DOWN, LEFT;
	}
	
	private String id;
	private String name;
	private String desc;
	/**可移动范围(0,0)-(1,0),(2,0) */
	private List<Vector2> moveRange;
	/**攻击范围(0,0)-(1,0),(2,0) */
	private List<Vector2> fightRange;
	/**在地图上的位置坐标*/
	private int mapX;
	private int mapY;
	
	/** 是否被选中 */
	private boolean selected;
	protected float frameTime = 0f;
	/**当前帧*/
	private TextureRegion currentFrame = null;

	
	public Hero(String id) {
		AssetHero ah = Assets.instance.assetHeros.get(id);
		
//		super(ah.stand);
	}
	
	/** 朝向 */
	private Direction dir;

	private void loadAllAnimations() {
		// Walking animation
		Texture texture = Utility.getTextureAsset(_defaultSpritePath);
		TextureRegion[][] textureFrames = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);
		_walkDownFrames = new Array<TextureRegion>(4);
		_walkLeftFrames = new Array<TextureRegion>(4);
		_walkRightFrames = new Array<TextureRegion>(4);
		_walkUpFrames = new Array<TextureRegion>(4);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				TextureRegion region = textureFrames[i][j];
				if (region == null) {
					Gdx.app.debug(TAG, "Got null animation frame " + i + "," + j);
				}
				switch (i) {
				case 0:
					_walkDownFrames.insert(j, region);
					break;
				case 1:
					_walkLeftFrames.insert(j, region);
					break;
				case 2:
					_walkRightFrames.insert(j, region);
					break;
				case 3:
					_walkUpFrames.insert(j, region);
					break;
				}
			}
		}
		_walkDownAnimation = new Animation(0.25f, _walkDownFrames, Animation.PlayMode.LOOP);
		_walkLeftAnimation = new Animation(0.25f, _walkLeftFrames, Animation.PlayMode.LOOP);
		_walkRightAnimation = new Animation(0.25f, _walkRightFrames, Animation.PlayMode.LOOP);
		_walkUpAnimation = new Animation(0.25f, _walkUpFrames, Animation.PlayMode.LOOP);
	}

	public void setDirection(Direction direction, float deltaTime) {
		this._previousDirection = this._currentDirection;
		this._currentDirection = direction;
		// Look into the appropriate variable when changing position
		switch (_currentDirection) {
		case DOWN:
			_currentFrame = _walkDownAnimation.getKeyFrame(_frameTime);
			break;
		case LEFT:
			_currentFrame = _walkLeftAnimation.getKeyFrame(_frameTime);
			break;
		case UP:
			_currentFrame = _walkUpAnimation.getKeyFrame(_frameTime);
			break;
		case RIGHT:
			_currentFrame = _walkRightAnimation.getKeyFrame(_frameTime);
			break;
		default:
			break;
		}
	}

	public void setNextPositionToCurrent() {
		setCurrentPosition(_nextPlayerPosition.x, _nextPlayerPosition.y);
	}

	public void calculateNextPosition(Direction currentDirection, float deltaTime) {
		float testX = _currentPlayerPosition.x;
		float testY = _currentPlayerPosition.y;
		_velocity.scl(deltaTime);
		switch (currentDirection) {
		case LEFT:
			testX -= _velocity.x;
			break;
		case RIGHT:
			testX += _velocity.x;
			break;
		case UP:
			testY += _velocity.y;
			break;
		case DOWN:
			testY -= _velocity.y;
			break;
		default:
			break;
		}
		_nextPlayerPosition.x = testX;
		_nextPlayerPosition.y = testY;
		// velocity
		_velocity.scl(1 / deltaTime);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		TextureRegion reg = null;

		// Draw Particles
//		dustParticles.draw(batch);

		// Set special color when game object has a feather power-up
		if (selected) {
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}

		// Draw image
		reg = stand;
		batch.draw(reg.getTexture(), getX(), getY(),getOriginX(), getOriginY(), getRegionX(), getRegionY(), getScaleX(), getScaleY(),
			getRotation(), reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
			dir == Direction.LEFT, false);

		// Reset color to white
		batch.setColor(1, 1, 1, 1);
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
	
}
