package com.v5ent.game.utils;

public class InputTransform
{
    private static final float appWidth = Constants.VIEWPORT_WIDTH;
    private static final float appHeight = Constants.VIEWPORT_HEIGHT;

    public static float getCursorToModelX(int screenX, int cursorX) 
    {
        return (((float)cursorX) * appWidth) / ((float)screenX); 
    }

    public static float getCursorToModelY(int screenY, int cursorY) 
    {
        return ((float)(screenY - cursorY)) * appHeight / ((float)screenY) ; 
    }
}
