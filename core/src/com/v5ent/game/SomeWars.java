package com.v5ent.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.v5ent.game.core.Assets;
import com.v5ent.game.screens.GameOverScreen;
import com.v5ent.game.screens.LoginGameScreen;
import com.v5ent.game.screens.MainGameScreen;
import com.v5ent.game.screens.PrepareScreen;

public class SomeWars extends Game {

	 public LoginGameScreen loginGameScreen;
	 public PrepareScreen prepareScreen;
	 public MainGameScreen mainGameScreen;
	 public GameOverScreen gameoverScreen;
	 public Preferences prefs ;
	
	@Override
	public void create() {
		// Load assets
		Assets.instance.init(new AssetManager());
		prefs = Gdx.app.getPreferences("Preferences");
		loginGameScreen = new LoginGameScreen(this);
		prepareScreen = new PrepareScreen(this);
		mainGameScreen = new MainGameScreen(this);
		gameoverScreen = new GameOverScreen(this);
        setScreen(loginGameScreen);              
	}

	@Override
	public void dispose() {
		mainGameScreen.dispose();
		Assets.instance.dispose();
	}
	

}
