package com.v5ent.game.utils;


public class Constants {

	// Visible game world is 16 meters wide
	public static final float GAME_WIDTH = 1136.0f;//1136/71

	// Visible game world is 9 meters tall
	public static final float GAME_HEIGHT = 640.0f;//640/71

	//1136 x 640
	// Visible game world is 16 meters wide
	public static final float VIEWPORT_WIDTH = 16.0f;//1136/71

	// Visible game world is 9 meters tall
	public static final float VIEWPORT_HEIGHT = 9.0f;//640/71
	
	//屏幕比率
	public static float RV_W_RATIO= GAME_WIDTH/VIEWPORT_WIDTH;// 71
	public static float RV_H_RATIO = GAME_HEIGHT/VIEWPORT_HEIGHT;// 71
	// Cell is 5 meters wide
	public static final float CELL_WIDTH = 140.f/RV_W_RATIO;
	// Cell is 5 meters tall
	public static final float CELL_HEIGHT = 90.f/RV_H_RATIO;
	// Cell is 5 meters wide
	public static final int MAP_ROWS = 5;
	// Cell is 5 meters tall
	public static final int MAP_COLS = 7;

	// Location of image file for level 01
	public static final String BACKGROUND = "battle/background.png";
	public static final String BLOCK = "battle/block.png";
	public static final String SELECTBG = "battle/select.png";
	public static final String CARDBACKGROUND = "cards/card-bg.png";
	public static final String CARDLIGHT = "cards/card-light.png";
	public static final String CARDDISABLE = "cards/card-disable.png";

	public static final String MOVE_CELL = "battle/move-cell.png";

	public static final String FIGHT_CELL = "battle/fight-cell.png";

}
