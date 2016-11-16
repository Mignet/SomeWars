package com.potato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Mario extends Actor {

	public static float x;
	public static float y;
	public static float statetime;

	Texture texture;
	TextureRegion currentFrame;

	ImageButton buttonL;
	ImageButton buttonR;

	Animation aniRight;
	Animation aniLeft;
	Animation aniIdle;
	
	
	public static int LeftState  = 1;
	public static int IdeltState = 2;
	public static int RightState = 3;
	public static int state = 2;

	
	public Mario(float x, float y) {
		this.x = x;
		this.y = y;
		this.show();
	}

	public void show() {
		texture = new Texture(Gdx.files.internal("data/mario.png"));
		TextureRegion[][] spilt = TextureRegion.split(texture, 64, 64);
		TextureRegion[][] miror = TextureRegion.split(texture, 64, 64);
		for (TextureRegion[] region1 : miror) {

			for (TextureRegion region2 : region1) {
				region2.flip(true, false);
			}
		}

		// ÓÒ
		TextureRegion[] regionR = new TextureRegion[3];
		regionR[0] = spilt[0][1];
		regionR[1] = spilt[0][2];
		regionR[2] = spilt[0][0];
		aniRight = new Animation(0.1f, regionR);
		// ×ó
		TextureRegion[] regionL = new TextureRegion[3];
		regionL[0] = miror[0][1];
		regionL[1] = miror[0][2];
		regionL[2] = miror[0][0];
		aniLeft = new Animation(0.1f, regionL);
		// ¿ÕÏÐ
		TextureRegion[] regionI = new TextureRegion[1];
		regionI[0] = spilt[0][0];

		aniIdle = new Animation(0.1f, regionI);

		buttonL = new ImageButton(new TextureRegionDrawable(spilt[1][0]),
				new TextureRegionDrawable(spilt[1][1]));
		buttonR = new ImageButton(new TextureRegionDrawable(miror[1][0]),
				new TextureRegionDrawable(miror[1][1]));

		buttonL.setPosition(20, 20);
		buttonR.setPosition(100, 20);

		buttonL.addListener(new InputListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				state = IdeltState;
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state = LeftState;
				return true;
			}
		});

		buttonR.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				state = IdeltState;
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				state = RightState;
				return true;
			}

		});

	}
	
	public void update(){
		if(state == LeftState){
			this.x -=1.5f;
			if(x<20) this.x = 20;
		}else if(state == RightState){
			this.x +=1.5f;
			if(x>400) this.x = 400;
		}
		this.x = x;
	}
	
	public void aniCheck(){
		if(state == LeftState){
			currentFrame = aniLeft.getKeyFrame(statetime, true);
		}else if(state == RightState){
			currentFrame = aniRight.getKeyFrame(statetime, true);
		}else if (state == IdeltState) {
			currentFrame = aniIdle.getKeyFrame(statetime,true);
		}
	}
	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
			statetime+=Gdx.graphics.getDeltaTime();
			this.update();
			this.aniCheck();
			
			batch.draw(currentFrame, x, y);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
