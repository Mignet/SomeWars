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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.v5ent.game.SomeWars;
import com.v5ent.game.core.Assets;
import com.v5ent.game.utils.Constants;

public class GameOverScreen implements Screen {
    private Stage _stage;
    private SomeWars _game;
    private static final String DEATH_MESSAGE = "你很英勇的战斗，但是，你还是失败了.";
    private static final String GAMEOVER = "GAME OVER";

    public GameOverScreen(SomeWars game){
        _game = game;

        //create
        ExtendViewport viewport = new ExtendViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        _stage = new Stage(viewport);
    	// + Background
        Image imgBackground = new Image(new Texture(Gdx.files.internal("menus/gameover.jpg")));
        imgBackground.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        _stage.addActor(imgBackground);
        Texture tex = new Texture(Gdx.files.internal("menus/gameover-button.png"));       
		TextureRegion[][] tmp = TextureRegion.split(tex, 112, 43);
//		ImageButton continueButton = new  ImageButton(new TextureRegionDrawable(tmp[0][0]), new TextureRegionDrawable(tmp[0][1]));
		ImageButton mainMenuButton = new  ImageButton(new TextureRegionDrawable(tmp[1][0]), new TextureRegionDrawable(tmp[1][1]));
//        TextButton continueButton = new TextButton("继续", Utility.STATUSUI_SKIN);
//        TextButton mainMenuButton = new TextButton("返回主菜单", Utility.STATUSUI_SKIN);
        Label messageLabel = new Label(DEATH_MESSAGE, Assets.instance.STATUSUI_SKIN);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        Label gameOverLabel = new Label(GAMEOVER, Assets.instance.STATUSUI_SKIN);
        gameOverLabel.setAlignment(Align.center);

        Table table = new Table();

        //Layout
        table.setFillParent(true);
        table.add(messageLabel).pad(50, 50,50,50).expandX().fillX().row();
        table.add(gameOverLabel).pad(10,50,250,50);
//        table.row();
//        table.add(continueButton).pad(50,50,10,50);
        table.row();
        table.add(mainMenuButton).pad(10,50,0,50);

        _stage.addActor(table);

        //Listeners
        mainMenuButton.addListener(new ClickListener() {

                                       @Override
                                       public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                           return true;
                                       }

                                       @Override
                                       public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                                           _game.setScreen(_game.prepareScreen);
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
        _stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_stage);
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
        _stage.clear();
        _stage.dispose();
    }
}
