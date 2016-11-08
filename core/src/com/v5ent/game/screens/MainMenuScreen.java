package com.v5ent.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.v5ent.game.SomeWars;

public class MainMenuScreen implements InputProcessor,Screen{
	private static String TAG = MainMenuScreen.class.getName();
	
	private Stage stage;
	private SomeWars gameIns;

	public MainMenuScreen(SomeWars game){
		gameIns = game;
		//creation
		stage = new Stage();
		Table table = new Table();
		table.setFillParent(true);
		Texture tex = new Texture(Gdx.files.internal("menus/sp_button.png"));       
		TextureRegion[][] tmp = TextureRegion.split(tex, 112, 43);
//		Image title = new Image(Utility.STATUSUI_TEXTUREATLAS.findRegion("game_title"));
		Image title = new Image(new Texture(Gdx.files.internal("menus/title.png")));
		ImageButton newGameButton = new  ImageButton(new TextureRegionDrawable(tmp[0][0]), new TextureRegionDrawable(tmp[0][1]));
//		ImageButton loadGameButton = new  ImageButton(new TextureRegionDrawable(tmp[1][0]), new TextureRegionDrawable(tmp[1][1]));
//		ImageButton watchIntroButton = new  ImageButton(new TextureRegionDrawable(tmp[2][0]), new TextureRegionDrawable(tmp[2][1]));
//		ImageButton creditsButton = new  ImageButton(new TextureRegionDrawable(tmp[3][0]), new TextureRegionDrawable(tmp[3][1]));
		ImageButton exitButton = new  ImageButton(new TextureRegionDrawable(tmp[4][0]), new TextureRegionDrawable(tmp[4][1]));
		/*TextButton newGameButton = new TextButton("新的冒险", Utility.STATUSUI_SKIN);
		TextButton loadGameButton = new TextButton("继续征程", Utility.STATUSUI_SKIN);
		TextButton watchIntroButton = new TextButton("观看介绍", Utility.STATUSUI_SKIN);
		TextButton creditsButton = new TextButton("鸣谢名单", Utility.STATUSUI_SKIN);
		TextButton exitButton = new TextButton("退出游戏",Utility.STATUSUI_SKIN);*/

		//Layout
		table.add(title).spaceBottom(20).row();
		table.add(newGameButton).spaceBottom(10).row();
//		table.add(loadGameButton).spaceBottom(10).row();
//		table.add(watchIntroButton).spaceBottom(10).row();
//		table.add(creditsButton).spaceBottom(10).row();
		table.add(exitButton).spaceBottom(10).row();

		stage.addActor(table);

		//Listeners
		newGameButton.addListener(new ClickListener() {
									  @Override
									  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
										  return true;
									  }

									  @Override
									  public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
										  gameIns.setScreen(gameIns.mainGameScreen);
									  }
								  }
		);

		exitButton.addListener(new ClickListener() {

								   @Override
								   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
									   return true;
								   }

								   @Override
								   public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
									   Gdx.app.exit();
								   }

							   }
		);
		
	}
	
	@Override
	public void render(float delta) {
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
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
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
		stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}