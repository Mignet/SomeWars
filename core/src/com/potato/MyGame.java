package com.potato;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyGame implements ApplicationListener {

	GameStage gameStage;
	StartStage startStage;
	StoreStage storeStage;
	
	
	@Override
	public void create() {		
		gameStage = new GameStage();
		startStage = new StartStage();
		storeStage = new StoreStage();
		
	}

	public void selectStageRender(){
		
		if(Constants.Stageflag == Constants.StartStageOn){
			Gdx.input.setInputProcessor(startStage);
			startStage.act();
			startStage.draw();
		}else if (Constants.Stageflag == Constants.GameStageOn) {
			Gdx.input.setInputProcessor(gameStage);
			gameStage.act();
			gameStage.draw();
		}else if (Constants.Stageflag == Constants.StoreStageOn) {
			Gdx.input.setInputProcessor(storeStage);
			storeStage.act();
			storeStage.draw();
		}
	}
	@Override
	public void dispose() {

	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		selectStageRender();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
