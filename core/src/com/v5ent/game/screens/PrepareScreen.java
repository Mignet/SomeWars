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
import com.v5ent.game.SomeWars;
import com.v5ent.game.core.Assets;
import com.v5ent.game.entities.Hero;

public class PrepareScreen implements Screen {
	private SomeWars gameIns;
	private Stage stage;
	private Table container;
	// menu
	private Image imgBackground;

	List<Hero> myHeros = new ArrayList<Hero>();

	public PrepareScreen(SomeWars game) {
		gameIns = game;
		stage = new Stage();
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		// Gdx.graphics.setVSync(false);
		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		Table rightTable = new Table();
//        rightTable.debug();
//        rightTable.setFillParent(true);
		final ScrollPane scroll = new ScrollPane(rightTable, skin);
//        table.pad(10).defaults().right().space(4);
		//TODO:从数据库获取
		myHeros.add(new Hero("001"));
		myHeros.add(new Hero("002"));
		for (int i = 0; i < 40; i++) {
			rightTable.row();
			final CheckBox checkBox = new CheckBox("Hero:"+padZero(i+1)+(i+1), skin);
			checkBox.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (checkBox.isChecked()) {
						Gdx.app.log("TAG",actor.getName()+ " is checked");
					} else {
						Gdx.app.log("TAG", "box is unchecked");
					}
				}
			});
			rightTable.add(checkBox).expandX();
			rightTable.add(new Label(i + " some desciption, every hero has desciption", skin));
		}
		Table bottomTable = new Table();
		TextButton startButton = new TextButton("开始", Assets.instance.STATUSUI_SKIN);
		bottomTable.setHeight(startButton.getHeight());
		bottomTable.setWidth(Gdx.graphics.getWidth());
		bottomTable.center();
		bottomTable.add(startButton).padBottom(60);
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
		/**
		 * 以下是Table的相关API的一些个人的解释
		 * row():开启一个新行
		 * right():向右移动一点,默认在最左边
		 * left():在最左边显示,默认在中间
		 * expandX():扩展X轴(最明显的效果就是用了这个以后位置向右移动了)
		 * add()添加一列
		 * right().expandX():可以理解为不断地向右拓展...
		 * space(10): 单元格与单元格之间的距离以及单元格与上边界之间的距离
		 * padBottom(0): 单元格与下边界之间的距离为0
		 * fill():将fillX()、fillY()设置成1
		 */
//      container.add(scroll).expand().fill().colspan(4);
		container.add(scroll).fill().expand().colspan(4);
		container.row().space(10).padBottom(0);
		//container.add(flickButton).right().expandX();
//      container.add(onTopButton);
//      container.add(smoothButton);
		//container.add(fadeButton).left().expandX();
	}
	private Table buildBackgroundLayer() {
		Table layer = new Table();
		// + Background
//		imgBackground = new Image(skinCanyonBunny, "background");
		layer.add(imgBackground);
		return layer;
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

		stage.setDebugAll(true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
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
