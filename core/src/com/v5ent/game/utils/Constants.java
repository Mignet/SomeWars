package com.v5ent.game.utils;

public class Constants {
	//1136 x 640
	// Visible game world is 16 meters wide
	public static final float VIEWPORT_WIDTH = 16.0f;

	// Visible game world is 9 meters tall
	public static final float VIEWPORT_HEIGHT = 9.0f;
	
	//屏幕比率
	public static final float RV_RATIO= 71.0f;
	// Cell is 5 meters wide
	public static final float CELL_WIDTH = 90.f/71.0f;
	// Cell is 5 meters tall
	public static final float CELL_HEIGHT = 70.f/71.0f;
	// Cell is 5 meters wide
	public static final int MAP_ROWS = 7;
	// Cell is 5 meters tall
	public static final int MAP_COLS = 11;

	// Location of image file for level 01
	public static final String BACKGROUND = "battle/background.jpg";

	public static final String MOVE_CELL = "battle/move-cell.png";

	public static final String FIGHT_CELL = "battle/fight-cell.png";

}
