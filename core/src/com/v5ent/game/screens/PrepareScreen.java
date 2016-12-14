package com.v5ent.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.v5ent.game.SomeWars;
import com.v5ent.game.core.Assets;
import com.v5ent.game.entities.Card;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.utils.Constants;

public class PrepareScreen implements Screen {
	private static final String TAG = PrepareScreen.class.getName();
	private SomeWars gameIns;
	private Stage stage;
	private Table rightTable;
	private Skin skin;

	public PrepareScreen(SomeWars game) {
		gameIns = game;
		ExtendViewport viewport = new ExtendViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		stage = new Stage(viewport);
		stage.addActor(new Image(Assets.instance.background));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		// Gdx.graphics.setVSync(false);
		Table container = new Table();
//		container.add(new Image(Assets.instance.selectBg));
		stage.addActor(container);
		container.setFillParent(true);

		rightTable = new Table();
//        rightTable.debug();
//        rightTable.setFillParent(true);
		final ScrollPane scroll = new ScrollPane(rightTable, skin);
//        table.pad(10).defaults().right().space(4);
		initCards();
		Table bottomTable = new Table();
		TextButton startButton = new TextButton("开始战斗", Assets.instance.STATUSUI_SKIN);
		bottomTable.setFillParent(true);
		bottomTable.row();
		bottomTable.add(startButton).padTop(stage.getHeight()-40);
		stage.addActor(bottomTable);
		startButton.addListener(new ClickListener() {
									@Override
									public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
										return true;
									}

									@Override
									public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
										gameIns.setScreen(gameIns.mainGameScreen);
									}
								}
		);
//      container.add(scroll).expand().fill().colspan(4);
		container.add(scroll).fill().expand().colspan(4);
		container.row().space(10).padBottom(0);
		//container.add(flickButton).right().expandX();
//      container.add(onTopButton);
//      container.add(smoothButton);
		//container.add(fadeButton).left().expandX();
	}
	private void initCards(){
		//TODO:从数据库获取
//		myHeros.add(new Hero("001"));
//		myHeros.add(new Hero("002"));
		rightTable.clear();
		for (int i = 1; i < 10; i++) {
			rightTable.row();
			final Card card = new Card("00"+i, skin);
			card.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (card.isChecked()) {
						Gdx.app.log(TAG,actor.getUserObject()+ " is checked");
						gameIns.myHeros.put(actor.getUserObject().toString(),new Hero(actor.getUserObject().toString()));
					} else {
						gameIns.myHeros.remove(actor.getUserObject().toString());
						Gdx.app.log(TAG, actor.getUserObject()+ " is unchecked");
					}
					Gdx.app.debug(TAG,"gameIns.myHeros:"+gameIns.myHeros);
				}
			});
			rightTable.add(card).width(196f).height(250f);
			rightTable.add(new Label(i + " some desciption, every hero has desciption", skin));
		}
	}
	private String padZero(int n) {
		if(n<10){
			return "00";
		}else if(n>=10&&n<100){
			return "0";
		}else{
			return "";
		}
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void dispose () {
		stage.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

//		stage.setDebugAll(true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		initCards();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
//		stage.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
