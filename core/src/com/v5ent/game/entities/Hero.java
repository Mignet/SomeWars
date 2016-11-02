package com.v5ent.game.entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hero{
	private static final String TAG = Hero.class.getSimpleName();
	private static final String defaultSpritePath = "sprites/characters/Warrior.png";
	private Vector2 velocity;
	private String entityId;
	private Direction currentDirection = Direction.RIGHT;
	private Direction previousDirection = Direction.RIGHT;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private Animation walkUpAnimation;
	private Animation walkDownAnimation;
	private Array<TextureRegion> walkLeftFrames;
	private Array<TextureRegion> walkRightFrames;
	private Array<TextureRegion> walkUpFrames;
	private Array<TextureRegion> walkDownFrames;
	protected Vector2 nextPlayerPosition;
	protected Vector2 currentPlayerPosition;
	protected State state = State.IDLE;
	protected float frameTime = 0f;
	protected Sprite frameSprite = null;
	protected TextureRegion currentFrame = null;
	public final int FRAME_WIDTH = 16;
	public final int FRAME_HEIGHT = 16;

	/**待机、行走、施法、攻击、被打、死亡*/
	public enum State {
		IDLE, WALKING,MAGIC,FIGHT,BEATEN,DEAD
	}

	public enum Direction {
		UP, RIGHT, DOWN, LEFT;
	}
	
	private String id;
	private String name;
	private String desc;
	/**(0,0)-(1,0),(2,0) */
	private List<Vector2> moveRange;
	/**(0,0)-(1,0),(2,0) */
	private List<Vector2> fightRange;
	private int X;
	private int Y;
	
//	private Vector2 mapLoc;
	public void update(float delta) {
		frameTime = (frameTime + delta) % 5; // Want to avoid overflow
		// We want the hitbox to be at the feet for a better feel
//		setBoundingBoxSize(0f, 0.5f);
	}
	private void loadDefaultSprite() {
		Texture texture = Utility.getTextureAsset(defaultSpritePath);
		TextureRegion[][] textureFrames = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);
		frameSprite = new Sprite(textureFrames[0][0].getTexture(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		currentFrame = textureFrames[0][0];
	}

	private void loadAllAnimations() {
		// Walking animation
		Texture texture = Utility.getTextureAsset(defaultSpritePath);
		TextureRegion[][] textureFrames = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);
		walkDownFrames = new Array<TextureRegion>(4);
		walkLeftFrames = new Array<TextureRegion>(4);
		walkRightFrames = new Array<TextureRegion>(4);
		walkUpFrames = new Array<TextureRegion>(4);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				TextureRegion region = textureFrames[i][j];
				if (region == null) {
					Gdx.app.debug(TAG, "Got null animation frame " + i + "," + j);
				}
				switch (i) {
				case 0:
					walkDownFrames.insert(j, region);
					break;
				case 1:
					walkLeftFrames.insert(j, region);
					break;
				case 2:
					walkRightFrames.insert(j, region);
					break;
				case 3:
					walkUpFrames.insert(j, region);
					break;
				}
			}
		}
		walkDownAnimation = new Animation(0.25f, walkDownFrames, Animation.PlayMode.LOOP);
		walkLeftAnimation = new Animation(0.25f, walkLeftFrames, Animation.PlayMode.LOOP);
		walkRightAnimation = new Animation(0.25f, walkRightFrames, Animation.PlayMode.LOOP);
		walkUpAnimation = new Animation(0.25f, walkUpFrames, Animation.PlayMode.LOOP);
	}

	/*public void dispose() {
		Assets.unloadAsset(_defaultSpritePath);
	}*/

	public void setState(State state) {
		this.state = state;
	}

	public Sprite getFrameSprite() {
		return frameSprite;
	}

	public TextureRegion getFrame() {
		return currentFrame;
	}

	public Vector2 getCurrentPosition() {
		return currentPlayerPosition;
	}

	public void setCurrentPosition(float currentPositionX, float currentPositionY) {
		frameSprite.setX(currentPositionX);
		frameSprite.setY(currentPositionY);
		this.currentPlayerPosition.x = currentPositionX;
		this.currentPlayerPosition.y = currentPositionY;
	}

	public void setDirection(Direction direction, float deltaTime) {
		this.previousDirection = this.currentDirection;
		this.currentDirection = direction;
		// Look into the appropriate variable when changing position
		switch (currentDirection) {
		case DOWN:
			currentFrame = walkDownAnimation.getKeyFrame(frameTime);
			break;
		case LEFT:
			currentFrame = walkLeftAnimation.getKeyFrame(frameTime);
			break;
		case UP:
			currentFrame = walkUpAnimation.getKeyFrame(frameTime);
			break;
		case RIGHT:
			currentFrame = walkRightAnimation.getKeyFrame(frameTime);
			break;
		default:
			break;
		}
	}

	public void setNextPositionToCurrent() {
		setCurrentPosition(nextPlayerPosition.x, nextPlayerPosition.y);
	}

	public void calculateNextPosition(Direction currentDirection, float deltaTime) {
		float testX = currentPlayerPosition.x;
		float testY = currentPlayerPosition.y;
		velocity.scl(deltaTime);
		switch (currentDirection) {
		case LEFT:
			testX -= velocity.x;
			break;
		case RIGHT:
			testX += velocity.x;
			break;
		case UP:
			testY += velocity.y;
			break;
		case DOWN:
			testY -= velocity.y;
			break;
		default:
			break;
		}
		nextPlayerPosition.x = testX;
		nextPlayerPosition.y = testY;
		// velocity
		velocity.scl(1 / deltaTime);
	}
}
