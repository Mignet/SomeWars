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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.utils.Constants;

public class WorldRenderer implements Disposable {

	private static final String TAG = WorldRenderer.class.getName();

	private SpriteBatch batch;
	private WorldController worldController;

	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init () {
		batch = new SpriteBatch();
	}

	public void render () {
		renderTestObjects();
	}

	private void renderTestObjects () {
		batch.setProjectionMatrix(worldController.camera.combined);
		batch.begin();
		//draw background
		worldController.background.draw(batch);
		//draw heros
		List<Hero> temp =new ArrayList<Hero>(worldController.myHeros);
		Collections.sort(temp, new Comparator<Hero>() {
             @Override
             public int compare(Hero lhs, Hero rhs) {
                 // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                 return lhs.getY() > rhs.getY() ? -1 : (lhs.getY() < rhs.getY() ) ? 1 : 0;
             }
         });
		for (Hero sprite : temp) {
			sprite.draw(batch);
		}
		//draw move cell
		worldController.moveCell.draw(batch);
		worldController.moveCell2.draw(batch);
		batch.end();
	}
	
	public void resize (int width, int height) {
		worldController.camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		worldController.camera.update();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

}
