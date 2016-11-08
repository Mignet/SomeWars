package com.v5ent.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.v5ent.game.SomeWars;
import com.v5ent.game.core.Assets;

public class LoginGameScreen implements Screen {
	private static final String TAG = LoginGameScreen.class.getName();
	private Stage stage;
	private SomeWars gameIns;
	private TextField account;
	private TextField password;

	public LoginGameScreen(SomeWars game){
		gameIns = game;

		//create
		stage = new Stage();
		// + Background
        Image imgBackground = new Image(new Texture(Gdx.files.internal("menus/welcome.jpg")));
        imgBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        stage.addActor(imgBackground);
		Texture tex = new Texture(Gdx.files.internal("menus/new-button.png"));       
		TextureRegion[][] tmp = TextureRegion.split(tex, 112, 43);
//		ImageButton startButton = new  ImageButton(new TextureRegionDrawable(tmp[0][0]), new TextureRegionDrawable(tmp[0][1]));
//		ImageButton backButton = new  ImageButton(new TextureRegionDrawable(tmp[1][0]), new TextureRegionDrawable(tmp[1][1]));
		
		Label profileName = new Label("请输入您的用户名: ", Assets.instance.STATUSUI_SKIN);
		account  = new TextField("",Assets.instance.STATUSUI_SKIN, "inventory");
		account.setMaxLength(20);
//		account.setMessageText(gameIns.prefs.getString("account", "test"));
		/*account.addListener(new ClickListener(){
	        public void clicked(InputEvent e, float x, float y) {
	            //perform some action once it is clicked.
				UserNameInputListener listener = new UserNameInputListener(gameIns);
				Gdx.input.getTextInput(listener, "请输入您的用户名:", ""); 
			}
		});*/
		// configures an example of a TextField in password mode.
		Label profilePwd = new Label("请输入您的密码: ", Assets.instance.STATUSUI_SKIN);
		password  = new TextField("",Assets.instance.STATUSUI_SKIN, "inventory");
		password.setMaxLength(20);
//		password.setMessageText("password");
		password.setPasswordCharacter('*');
		password.setPasswordMode(true);
		/*password.addListener(new ClickListener(){
			public void clicked(InputEvent e, float x, float y) {
				//perform some action once it is clicked.
				PasswordInputListener listener = new PasswordInputListener();
				Gdx.input.getTextInput(listener, "请输入您的密码:", ""); 
			}
		});*/

		TextButton startButton = new TextButton("开始", Assets.instance.STATUSUI_SKIN);
//		TextButton backButton = new TextButton("返回", Assets.instance.STATUSUI_SKIN);

		//Layout
		Table topTable = new Table();
		topTable.setFillParent(true);
		topTable.add(profileName).center();
		topTable.add(account).center();
		topTable.row();
		topTable.add(profilePwd).center();
		topTable.add(password).center();

		Table bottomTable = new Table();
		bottomTable.setHeight(startButton.getHeight());
		bottomTable.setWidth(Gdx.graphics.getWidth());
		bottomTable.center();
		bottomTable.add(startButton).padBottom(60);
//		bottomTable.add(backButton);

		stage.addActor(topTable);
		stage.addActor(bottomTable);

		startButton.addListener(new ClickListener() {

									@Override
									public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
										return true;
									}

									@Override
									public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
										//TODO:
										Gdx.app.debug(TAG, "连接服务器，校验密码..登录...");
//										account.setText(gameIns.prefs.getString("account"));
										String messageText = account.getText();
										//如果什么都没有输入，直接返回
										if("".equals(messageText.trim()))return;
										//check to see if the current profile matches one that already exists
										boolean exists = false;
//										gameIns.setScreen(gameIns.prepareScreen);
										gameIns.setScreen(gameIns.mainGameScreen);
									}
								}
		);

		/*backButton.addListener(new ClickListener() {

								   @Override
								   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
									   return true;
								   }

								   @Override
								   public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//									   gameIns.setScreen(gameIns.mainMenuScreen);
									   
								   }
							   }
		);*/

	}

	@Override
	public void render(float delta) {
		if( delta == 0){
			return;
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void show() {
		account.setText("");
		Gdx.input.setInputProcessor(stage);
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
		stage.clear();
		stage.dispose();
	}

}
