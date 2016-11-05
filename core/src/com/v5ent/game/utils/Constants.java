/*******************************************************************************
 * Copyright 2013 Andreas Oehlke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


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

	// Location of description file for texture atlas
//	public static final String HEROS = "heros/heros.pack";

	// Location of image file for level 01
	public static final String BACKGROUND = "battle/background.jpg";

	public static final String MOVE_CELL = "battle/move-cell.png";

	public static final String FIGHT_CELL = "battle/fight-cell.png";

}
