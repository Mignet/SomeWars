package com.v5ent.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.v5ent.game.core.Assets;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.Transform;

/**
 * Created by Mignet on 2016/11/19.
 */

public class Magic extends Sprite {
    private TextureRegion currentFrame = null;
    private Animation magicAnimation;
    private Direction currentDir = Direction.RIGHT;

    public boolean isOver = false;
    public enum Direction {
        RIGHT, LEFT
    }
    private int mapX;
    private int mapY;
    private int targetMapX;
    private int targetMapY;
    public Magic(Animation m,int x, int y){
        magicAnimation = m;
        currentFrame = m.getKeyFrame(0);
        this.setSize(currentFrame.getRegionWidth()/ Constants.RV_W_RATIO, currentFrame.getRegionHeight()/Constants.RV_H_RATIO);
        // Set origin to sprite's center
//        this.setOrigin(this.getWidth() / 2.0f, 0);
        mapX = x;
        mapY = y;
        this.setPosition(Transform.positionInWorldX(x), Transform.positionInWorldY(y));
    }
    protected float frameTime = 0f;

    public void update(float delta) {
        frameTime = (frameTime + delta) % 4; // Want to avoid overflow
        currentFrame = magicAnimation.getKeyFrame(frameTime);
        if(Transform.positionInWorldX(targetMapX) == getX() && Transform.positionInWorldY(targetMapY)==getY()){
            isOver = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(currentFrame.getTexture(), (Constants.CELL_WIDTH-getWidth())/2+getX(), getY(),getOriginX(), getOriginY(), getWidth(),getHeight(), getScaleX(), getScaleY(),
                getRotation(), currentFrame.getRegionX(), currentFrame.getRegionY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight(),currentDir==Direction.LEFT, false);
//        super.draw(batch);
    }

    public void MoveTo(int mapX, int mapY) {
        if(mapX > this.getMapX()){
            currentDir = Direction.RIGHT;
        }else{
            currentDir = Direction.LEFT;
        }
        targetMapX = mapX;
        targetMapY = mapY;
        translate(Transform.positionInWorldX(mapX), Transform.positionInWorldY(mapY));
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
