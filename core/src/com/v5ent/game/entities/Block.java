package com.v5ent.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.v5ent.game.core.Assets;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.Transform;

/**
 * Created by Mignet on 2016/11/19.
 */

public class Block extends Sprite {
    private Texture currentFrame = null;
    private int mapX;
    private int mapY;
    public Block(int x,int y){
//        super(Assets.instance.block);
        currentFrame = Assets.instance.block;
        this.setSize(currentFrame.getWidth()/ Constants.RV_W_RATIO, currentFrame.getHeight()/Constants.RV_H_RATIO);
        // Set origin to sprite's center
//        this.setOrigin(this.getWidth() / 2.0f, 0);
        mapX = x;
        mapY = y;
        this.setPosition(Transform.positionInWorldX(x), Transform.positionInWorldY(y));
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(currentFrame, (Constants.CELL_WIDTH-getWidth())/2+getX(), getY(),getOriginX(), getOriginY(), getWidth(),getHeight(), getScaleX(), getScaleY(),
                getRotation(), 0, 0, currentFrame.getWidth(), currentFrame.getHeight(),false, false);
//        super.draw(batch);
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
