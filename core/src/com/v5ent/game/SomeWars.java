package com.v5ent.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.v5ent.game.core.Assets;
import com.v5ent.game.screens.GameOverScreen;
import com.v5ent.game.screens.LoginGameScreen;
import com.v5ent.game.screens.MainGameScreen;

public class SomeWars extends Game {

	 public LoginGameScreen loginGameScreen;
	 public MainGameScreen mainGameScreen;
	 public GameOverScreen gameoverScreen;
	
	@Override
	public void create() {
		// Load assets
		Assets.instance.init(new AssetManager());
		
		loginGameScreen = new LoginGameScreen(this);
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
