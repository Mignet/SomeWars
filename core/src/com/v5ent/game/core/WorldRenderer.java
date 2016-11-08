package com.v5ent.game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.GameState;

public class WorldRenderer implements Disposable {

	private static final String TAG = WorldRenderer.class.getName();

	private SpriteBatch batch;
	private SpriteBatch guiBatch;
	private WorldController worldController;

	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init () {
		batch = new SpriteBatch();
	}

	public void render () {
		renderObjects();
	}

	private void renderObjects () {
		batch.setProjectionMatrix(worldController.camera.combined);
		batch.begin();
		//draw background
		worldController.background.draw(batch);
		//draw move cell
		for(Sprite s:worldController.moveCells){
			 s.draw(batch);
		}
		for(Sprite s:worldController.fightCells){
			s.draw(batch);
		}
		//draw heros
		List<Hero> temp =new ArrayList<Hero>();
		List<Hero> temp0 =new ArrayList<Hero>(worldController.myHeros);
		List<Hero> temp1 =new ArrayList<Hero>(worldController.enemyHeros);
		temp.addAll(temp0);
		temp.addAll(temp1);
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
		batch.end();
		renderGui(batch);
	}
	
	private void renderGui (SpriteBatch batch) {
		batch.setProjectionMatrix(worldController.cameraGUI.combined);
		batch.begin();
		// draw collected gold coins icon + text
		// (anchored to top left edge)
//		renderGuiScore(batch);
//		// draw collected feather icon (anchored to top left edge)
//		renderGuiFeatherPowerup(batch);
//		// draw extra lives icon + text (anchored to top right edge)
//		renderGuiExtraLive(batch);
//		// draw FPS text (anchored to bottom right edge)
//		renderGuiFpsCounter(batch);
//		// draw game over text
//		renderGuiGameOverMessage(batch);
		if(worldController.gameState == GameState.PREPARE){
			BitmapFont  seconds = new BitmapFont();
			seconds.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			seconds.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			seconds.setScale(4);
			String s =  worldController.second>0?"Time: "+worldController.second:"Fight!!!";
			seconds.draw(batch, s, -10,-2);
		}else if(worldController.gameState == GameState.MOVE){
			BitmapFont  seconds = new BitmapFont();
			seconds.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			seconds.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			seconds.setScale(4);
			String s =  "YOUR TURN!!!";
			seconds.draw(batch, s,  -10,-2);
		}
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
