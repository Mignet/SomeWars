package com.v5ent.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Transform
{
    private static final float offsetX = - 6.97f;
    private static final float offsetY = - 3.75f;
    public Vector2 positionInWorld(Vector2 position){
		return positionInWorld(position.x,position.y);
	}
	public Vector2 positionInWorld(float x,float y){
		return new Vector2(offsetX+x*Constants.CELL_WIDTH,offsetY+y*Constants.CELL_HEIGHT);
	}
	public static float positionInWorldX(float x){
		return offsetX+x*Constants.CELL_WIDTH;
	}
	public static float positionInWorldY(float y){
		return offsetY+y*Constants.CELL_HEIGHT;
	}
	//真实精灵坐标到地图
	/*public Vector2 positionInMap(float x,float y){
		return new Vector2(positionInMapX(x),positionInMapY(y));
	}
	public static int positionInMapX(float x){
		return MathUtils.floor((x - offsetX)/Constants.CELL_WIDTH);
	}
	public static int positionInMapY(float y){
		return MathUtils.floor((y - offsetY)/Constants.CELL_HEIGHT);
	}*/
	
	public static Vector2 mouseInWorld(Vector2 position){
		return mouseInWorld(position.x,position.y);
	}
	
	public static Vector2 mouseInWorld(float x1,float y1){
		float x = x1/Constants.RV_RATIO - (Constants.VIEWPORT_WIDTH/2+offsetX) ;
		float y = Constants.VIEWPORT_HEIGHT - y1/Constants.RV_RATIO -(Constants.VIEWPORT_HEIGHT/2+offsetY);
		return new Vector2(MathUtils.floor(x/Constants.CELL_WIDTH),MathUtils.floor(y/Constants.CELL_HEIGHT));
	}
	public static int mouseInWorldY(float y1){
		float y = Constants.VIEWPORT_HEIGHT - y1/Constants.RV_RATIO -(Constants.VIEWPORT_HEIGHT/2+offsetY);
		return MathUtils.floor(y/Constants.CELL_HEIGHT);
	}
	public static int mouseInWorldX(float x1){
		float x = x1/Constants.RV_RATIO - (Constants.VIEWPORT_WIDTH/2+offsetX) ;
		return MathUtils.floor(x/Constants.CELL_WIDTH);
	}
}
