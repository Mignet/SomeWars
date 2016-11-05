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


package com.v5ent.game.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.Transform;

public class WorldController extends InputAdapter implements GestureListener {

	private static final String TAG = WorldController.class.getName();

	public OrthographicCamera camera;
	
	public Sprite background;
	
	public Sprite moveCell;
	public Sprite moveCell2;
	public Sprite fightCell;
	public List<Hero> testSprites;
	public int selectedIndex;


	public WorldController () {
		init();
	}

	private void init () {
		Gdx.input.setInputProcessor(this);
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		// we want the camera to setup a viewport with pixels as units, with the
		// y-axis pointing upwards. The origin will be in the lower left corner
		// of the screen.
//		camera.setToOrtho(false);
		initTestObjects();
	}

	private void initTestObjects () {
		background = new Sprite(Assets.instance.background);
		background.setSize(background.getWidth()/Constants.RV_RATIO, background.getHeight()/Constants.RV_RATIO);
		// Set origin to sprite's center
		background.setOrigin(background.getWidth() / 2.0f, background.getHeight() / 2.0f);
		background.setPosition(-Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2);
		moveCell =  new Sprite(Assets.instance.moveCell);
		moveCell.setSize(moveCell.getWidth()/Constants.RV_RATIO, moveCell.getHeight()/Constants.RV_RATIO);
		moveCell.setPosition(Transform.positionInWorldX(0), Transform.positionInWorldY(0));
		moveCell2 =  new Sprite(Assets.instance.moveCell);
		moveCell2.setSize(moveCell2.getWidth()/Constants.RV_RATIO, moveCell2.getHeight()/Constants.RV_RATIO);
		moveCell2.setPosition(Transform.positionInWorldX(10), Transform.positionInWorldY(6));
		fightCell =  new Sprite(Assets.instance.fightCell);
		fightCell.setSize(1, 1);
		// Create new array for 5 sprites
		testSprites = new ArrayList<Hero>();
		int myHerosCnt = 5;
		// Create a list of texture regions
		// Create new sprites using a random texture region
		for (int i = 0; i < myHerosCnt ; i++) {
//			create hero by id
			Hero spr = new Hero("001");
			// Calculate random position for sprite
			spr.setMapPosition(i, 3);
			// Put new sprite into array
			testSprites.add(spr);
		}
		// Set first sprite as selected one
		selectedIndex = 0;
	}

	public void update (float deltaTime) {
		handleDebugInput(deltaTime);
		updateTestObjects(deltaTime);
	}

	private void updateTestObjects (float deltaTime) {
		for(int i=0;i<testSprites.size();i++){
			testSprites.get(i).update(deltaTime);
		}
	}

	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Selected Sprite Controls
		float sprMoveSpeed = 5 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(-sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.D)) moveSelectedSprite(sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0, sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0, -sprMoveSpeed);
	}

	private void moveSelectedSprite (float x, float y) {
		testSprites.get(selectedIndex).translate(x, y);
	}

	@Override
	public boolean keyUp (int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		// Select next sprite
		else if (keycode == Keys.SPACE) {
			testSprites.get(selectedIndex).setSelected(false);
			selectedIndex = (selectedIndex + 1) % testSprites.size();
			testSprites.get(selectedIndex).setSelected(true);
			Gdx.app.debug(TAG, "Sprite #" + selectedIndex + " selected");
		}
		return false;
	}
	
	 @Override
	    public boolean touchDown(float screenX, float screenY, int pointer, int button) {
		 int x1 = Gdx.input.getX();
		 int y1 = Gdx.input.getY();
		 Vector3 input = new Vector3(x1, y1, 0);
		 camera.unproject(input);
		 Gdx.app.debug(TAG, "clicked # (" + x1+","+ y1 + " )");
		 Gdx.app.debug(TAG, "clicked # (" + x1/Constants.RV_RATIO+","+ y1/Constants.RV_RATIO + " )");
		 Gdx.app.debug(TAG, "clicked # (" +Transform.mouseInWorld( x1, y1)+ " )");
		 Gdx.app.debug(TAG, "Game Screen # (" + Gdx.graphics.getWidth()+","+ Gdx.graphics.getHeight() + " )");
		 //Now you can use input.x and input.y, as opposed to x1 and y1, to determine if the moving
		 //sprite has been clicked
		 for(int i=0;i<testSprites.size();i++){
			 Hero h = testSprites.get(i);
			 if(h.getBoundingRectangle().contains(input.x, input.y)) {
				 //Do whatever you want to do with the sprite when clicked
				 testSprites.get(selectedIndex).setSelected(false);
				 selectedIndex = i;
				 Gdx.app.debug(TAG, " # (Sprite #" + i + " clicked)");
				 h.setSelected(true);
			 }
		 }
	        return true;
	    }

	    @Override
	    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	        /*if(pointer < 5){
	            touches.get(pointer).touchX = 0;
	            touches.get(pointer).touchY = 0;
	            touches.get(pointer).touched = false;
	        }*/
	        return true;
	    }

	    @Override
	    public boolean touchDragged(int screenX, int screenY, int pointer) {
	        // TODO Auto-generated method stub
	        return false;
	    }

		@Override
		public boolean tap(float x, float y, int count, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", " + velocityY);
			if(velocityX>1000){
				Gdx.app.debug(TAG, "RIGHT");
			}
			if(velocityX<-1000){
				Gdx.app.debug(TAG, "LEFT");
			}
			if(velocityY>1000){
				Gdx.app.debug(TAG, "DOWN");
			}
			if(velocityY<-1000){
				Gdx.app.debug(TAG, "UP");
			}
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			// TODO Auto-generated method stub
			return false;
		}
}
