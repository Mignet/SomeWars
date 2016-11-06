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
    /** (0,0) to world**/
	public Vector2 positionInWorld(float x,float y){
		return new Vector2(positionInWorldX(x),positionInWorldY(y));
	}
	public static float positionInWorldX(float x){
		return offsetX+x*Constants.CELL_WIDTH;
	}
	public static float positionInWorldY(float y){
		return offsetY+y*Constants.CELL_HEIGHT;
	}
	/** mouse **/
	public static Vector2 mouseInMap(Vector2 position){
		return mouseInMap(position.x,position.y);
	}
	
	public static Vector2 mouseInMap(float x,float y){
		return new Vector2(mouseInMapX(x),mouseInMapY(y));
	}
	public static int mouseInMapX(float x1){
		float x = x1 - offsetX ;
		return MathUtils.floor(x/Constants.CELL_WIDTH);
	}
	public static int mouseInMapY(float y1){
		float y = y1 - offsetY;
		return MathUtils.floor(y/Constants.CELL_HEIGHT);
	}
}
