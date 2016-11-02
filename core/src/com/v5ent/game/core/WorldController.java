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

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.utils.CameraHelper;
import com.v5ent.game.utils.Constants;

public class WorldController extends InputAdapter {

	private static final String TAG = WorldController.class.getName();

	public Sprite background;
	public Hero[] testSprites;
	public int selectedSprite;

	public CameraHelper cameraHelper;

	public WorldController () {
		init();
	}

	private void init () {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		initTestObjects();
	}

	private void initTestObjects () {
		background = new Sprite(Assets.instance.background);
		background.setSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		// Set origin to sprite's center
		background.setOrigin(background.getWidth() / 2.0f, background.getHeight() / 2.0f);
		background.setPosition(-Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2);
		// Create new array for 5 sprites
		testSprites = new Hero[6];
		// Create a list of texture regions
		// Create new sprites using a random texture region
		for (int i = 0; i < testSprites.length; i++) {
//			Hero spr = new Hero(regions.get(i));
			Hero spr = new Hero(Assets.instance.assetHeros.get(i).stand);
			// Define sprite size to be 1m x 1m in game world
			spr.setSize(spr.getWidth()/80, spr.getHeight()/80);
			// Set origin to sprite's center
			spr.setOrigin(spr.getWidth() / 2.0f, 0);
			// Calculate random position for sprite
//			float randomX = MathUtils.random(-5.0f, 5.0f);
//			float randomY = MathUtils.random(-3.0f, 3.0f);
//			spr.setPosition(randomX, randomY);
			spr.setPosition(-(i)-.5f, -.5f);
			// Put new sprite into array
			testSprites[i] = spr;
		}
		// Set first sprite as selected one
		selectedSprite = 0;
	}

	public void update (float deltaTime) {
		handleDebugInput(deltaTime);
//		updateTestObjects(deltaTime);
		cameraHelper.update(deltaTime);
	}

	private void updateTestObjects (float deltaTime) {
		// Get current rotation from selected sprite
		float rotation = testSprites[selectedSprite].getRotation();
		// Rotate sprite by 90 degrees per second
		rotation += 90 * deltaTime;
		// Wrap around at 360 degrees
		rotation %= 360;
		// Set new rotation value to selected sprite
		testSprites[selectedSprite].setRotation(rotation);
	}

	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Selected Sprite Controls
		float sprMoveSpeed = 5 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(-sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.D)) moveSelectedSprite(sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0, sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0, -sprMoveSpeed);

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}

	private void moveSelectedSprite (float x, float y) {
		testSprites[selectedSprite].translate(x, y);
	}

	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
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
			selectedSprite = (selectedSprite + 1) % testSprites.length;
			// Update camera's target to follow the currently
			// selected sprite
			if (cameraHelper.hasTarget()) {
				cameraHelper.setTarget(testSprites[selectedSprite]);
			}
			Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
		}
		// Toggle camera follow
		else if (keycode == Keys.ENTER) {
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
			Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
		}
		return false;
	}
	
	 @Override
	    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	       /* if(pointer < 5){
	            touches.get(pointer).touchX = screenX;
	            touches.get(pointer).touchY = screenY;
	            touches.get(pointer).touched = true;
	        }*/
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
}
