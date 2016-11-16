package com.potato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StartStage extends Stage {

	Texture texture;
	TextureRegion startRegion;
	Image startImage;
	Image newGameBtn;
	TextureRegion newGameRegion;
	
	public StartStage() {
		super();
		init();
	}

	public void init() {

		texture = new Texture(Gdx.files.internal("data/start.png"));
		startRegion = new TextureRegion(texture, 0, 0, 800, 480);
		startImage = new Image(startRegion);
		startImage.setSize(480, 320);

		newGameRegion = new TextureRegion(texture, 924, 0, 100, 50);
		newGameBtn = new Image(newGameRegion);
		newGameBtn.setPosition(40, 230); 
		
		this.addActor(startImage);
		this.addActor(newGameBtn);
		
		newGameBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Constants.Stageflag = Constants.GameStageOn;
				
				return true;
			}			
		});
	}
}
