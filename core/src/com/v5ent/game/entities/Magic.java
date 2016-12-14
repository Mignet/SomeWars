package com.v5ent.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.core.Assets;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.Transform;

/**
 * Created by Mignet on 2016/11/19.
 */

public class Magic extends Sprite {
    private TextureRegion currentFrame = null;
    private Animation magicAnimation;

    public Direction getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(Direction currentDir) {
        this.currentDir = currentDir;
    }

    private Direction currentDir = Direction.RIGHT;

    public boolean isOver = false;
    public enum Direction {
        RIGHT, LEFT
    }
    private int mapX;
    private int mapY;
    /** 需要移动到的目标位置**/
    private float targetX;
    private float targetY;
    private int targetMapX;
    private int targetMapY;
    public Magic(Animation m,int x, int y){
        magicAnimation = m;
        currentFrame = m.getKeyFrame(0);
        this.setSize(currentFrame.getRegionWidth()/ Constants.RV_W_RATIO, currentFrame.getRegionHeight()*4/Constants.RV_H_RATIO);
        // Set origin to sprite's center
//        this.setOrigin(this.getWidth() / 2.0f, 0);
        mapX = x;
        mapY = y;
        nextPosition = new Vector2(Transform.positionInWorldX(x), Transform.positionInWorldY(y));
        this.setPosition(Transform.positionInWorldX(x), Transform.positionInWorldY(y));
    }
    protected float frameTime = 0f;

    public void update(float delta) {
        frameTime = (frameTime + delta) % 4; // Want to avoid overflow
        currentFrame = magicAnimation.getKeyFrame(frameTime);
        /*if(magicAnimation.isAnimationFinished(frameTime)){
            isOver = true;
        }*/
        calculateNextPosition(delta);
        setNextPositionToCurrent();
        if(Math.abs(this.nextPosition.x-this.targetX)<speed*delta && Math.abs(this.nextPosition.y-this.targetY)<speed*delta){
            isOver = true;
        }
       /* if(Transform.positionInWorldX(targetMapX) == getX() && Transform.positionInWorldY(targetMapY)==getY()){
            isOver = true;
        }*/
    }
    //当没有障碍物时使用
    public void setNextPositionToCurrent() {
        setPosition(nextPosition.x, nextPosition.y);
    }
    /** 速度 */
    private float speed = 4f;//4格一秒
    private Vector2 nextPosition;
    public void calculateNextPosition(float deltaTime) {
        float testX = this.getX();
        float testY = this.getY();
        speed *= (deltaTime);
        Vector2 v = new Vector2(targetX-testX,targetY-testY).nor();
        nextPosition.x = testX + v.x*speed;
        nextPosition.y = testY + v.y*speed;
//		Gdx.app.debug(TAG, "nextPosition:"+nextPosition);
        // velocity
        speed *=(1 / deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        float offsetX = 0;//currentDir==Direction.RIGHT?-Constants.CELL_WIDTH/2:Constants.CELL_WIDTH/2;
        batch.draw(currentFrame.getTexture(), offsetX+(Constants.CELL_WIDTH-getWidth())/2+getX(), (Constants.CELL_HEIGHT-getHeight())/2+getY(),getOriginX(), getOriginY(), getWidth(),getHeight(), getScaleX(), getScaleY(),
                getRotation(), currentFrame.getRegionX(), currentFrame.getRegionY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight(),currentDir==Direction.LEFT, false);
//        super.draw(batch);
    }

    public void moveTo(int mapX, int mapY) {
        if(mapX > this.getMapX()){
            currentDir = Direction.RIGHT;
        }else{
            currentDir = Direction.LEFT;
        }
        this.targetMapX = mapX;
        this.targetMapY = mapY;
        this.targetX = Transform.positionInWorldX(mapX);
        this.targetY = Transform.positionInWorldY(mapY);
//        translate(Transform.positionInWorldX(mapX), Transform.positionInWorldY(mapY));
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

}
