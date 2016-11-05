package com.v5ent.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.v5ent.game.SomeWars;
import com.v5ent.game.core.Assets;

public class NewGameScreen implements Screen {

	private Stage _stage;
	private SomeWars _game;
	private TextField account;

	public NewGameScreen(SomeWars game){
		_game = game;

		//create
		_stage = new Stage();
		// + Background
        Image imgBackground = new Image(new Texture(Gdx.files.internal("menus/1989C5FC.jpg")));
        _stage.addActor(imgBackground);
		Texture tex = new Texture(Gdx.files.internal("menus/new-button.png"));       
		TextureRegion[][] tmp = TextureRegion.split(tex, 112, 43);
		ImageButton startButton = new  ImageButton(new TextureRegionDrawable(tmp[0][0]), new TextureRegionDrawable(tmp[0][1]));
		ImageButton backButton = new  ImageButton(new TextureRegionDrawable(tmp[1][0]), new TextureRegionDrawable(tmp[1][1]));
		
		Label profileName = new Label("请输入您的用户名: ", Assets.instance.STATUSUI_SKIN);
		account  = new TextField("",Assets.instance.STATUSUI_SKIN, "inventory");
		account.setMaxLength(20);

		MyTextInputListener listener = new MyTextInputListener();
		Gdx.input.getTextInput(listener, "Dialog Title", "Initial Textfield Value");
//		TextButton startButton = new TextButton("开始", Utility.STATUSUI_SKIN);
//		TextButton backButton = new TextButton("返回", Utility.STATUSUI_SKIN);

		//Layout
		Table topTable = new Table();
		topTable.setFillParent(true);
		topTable.add(profileName).center();
		topTable.add(account).center();

		Table bottomTable = new Table();
		bottomTable.setHeight(startButton.getHeight());
		bottomTable.setWidth(Gdx.graphics.getWidth());
		bottomTable.center();
		bottomTable.add(startButton).padRight(50);
		bottomTable.add(backButton);

		_stage.addActor(topTable);
		_stage.addActor(bottomTable);

		startButton.addListener(new ClickListener() {

									@Override
									public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
										return true;
									}

									@Override
									public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
										String messageText = account.getText();
										//如果什么都没有输入，直接返回
										if("".equals(messageText.trim()))return;
										//check to see if the current profile matches one that already exists
										boolean exists = false;
											_game.setScreen(_game.mainGameScreen);
									}
								}
		);

		backButton.addListener(new ClickListener() {

								   @Override
								   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
									   return true;
								   }

								   @Override
								   public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
									   _game.setScreen(_game.mainMenuScreen);
								   }
							   }
		);

	}

	@Override
	public void render(float delta) {
		if( delta == 0){
			return;
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.act(delta);
        _stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		_stage.setViewport(width, height,false);
	}

	@Override
	public void show() {
		account.setText("");
		Gdx.input.setInputProcessor(_stage);
	}

	@Override
	public void hide() {
		account.setText("");
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		_stage.clear();
		_stage.dispose();
	}

}