package com.v5ent.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.v5ent.game.core.Assets;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.screens.GameOverScreen;
import com.v5ent.game.screens.LoginGameScreen;
import com.v5ent.game.screens.MainGameScreen;
import com.v5ent.game.screens.PrepareScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SomeWars extends Game {

	 public LoginGameScreen loginGameScreen;
	 public PrepareScreen prepareScreen;
	 public MainGameScreen mainGameScreen;
	 public GameOverScreen gameoverScreen;

	public Map<String,Hero> myHeros = new HashMap<String,Hero>();
//	 public Preferences prefs ;
	
	@Override
	public void create() {
		// Load assets
		Assets.instance.init(new AssetManager());
//		prefs = Gdx.app.getPreferences("Preferences");
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
