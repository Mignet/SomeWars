package com.v5ent.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.v5ent.game.core.Assets;
import com.v5ent.game.screens.GameOverScreen;
import com.v5ent.game.screens.MainGameScreen;
import com.v5ent.game.screens.MainMenuScreen;

public class SomeWars extends Game {

	 public MainMenuScreen mainMenuScreen;
	 public MainGameScreen mainGameScreen;
	 public GameOverScreen gameoverScreen;
	
	@Override
	public void create() {
		// Load assets
		Assets.instance.init(new AssetManager());
		
		mainMenuScreen = new MainMenuScreen(this);
		mainGameScreen = new MainGameScreen(this);
		gameoverScreen = new GameOverScreen(this);
        setScreen(mainMenuScreen);              
	}

	@Override
	public void dispose() {
		mainGameScreen.dispose();
	}
	

}
