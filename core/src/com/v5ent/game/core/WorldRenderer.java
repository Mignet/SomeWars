package com.v5ent.game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.entities.Block;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.entities.Magic;
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
		//draw blocks
		//draw heros
		List<Sprite> temp =new ArrayList<Sprite>();
		List<Sprite> temp0 =new ArrayList<Sprite>(worldController.myHeros);
		List<Sprite> temp1 =new ArrayList<Sprite>(worldController.enemyHeros);
		List<Sprite> temp2 =new ArrayList<Sprite>(worldController.blocks);
		temp.addAll(temp0);
		temp.addAll(temp1);
		temp.addAll(temp2);
		Collections.sort(temp, new Comparator<Sprite>() {
             @Override
             public int compare(Sprite lhs, Sprite rhs) {
                 // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                 return lhs.getY() > rhs.getY() ? -1 : (lhs.getY() < rhs.getY() ) ? 1 : 0;
             }
         });
		for (Sprite h : temp) {
//			Gdx.app.debug(TAG,"Hero:"+h.getX()+","+h.getY());
			h.draw(batch);
		}
		for(Magic m:worldController.magics){
			if(m.isOver){
				worldController.magics.remove(m);
			}else{
				m.draw(batch);
			}
		}
		batch.end();
		renderGui(batch);
	}
	
	private void renderGui (SpriteBatch batch) {
		batch.setProjectionMatrix(worldController.cameraGUI.combined);
		batch.begin();
		if(worldController.gameState == GameState.PREPARE){
			String s =  worldController.second>0?"Time: "+worldController.second:"Fight!!!";
			Assets.instance.font.draw(batch, s, -10,-2);
		}else if(worldController.gameState == GameState.MOVE){
			String s =  "YOUR TURN!!!";
			Assets.instance.font.draw(batch, s,  -10,-2);
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
