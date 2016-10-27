package com.v5ent.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;
import com.v5ent.game.screens.DirectedGame;
import com.v5ent.game.screens.MenuScreen;
import com.v5ent.game.screens.transitions.ScreenTransition;
import com.v5ent.game.screens.transitions.ScreenTransitionSlice;
import com.v5ent.game.utils.Assets;
import com.v5ent.game.utils.AudioManager;
import com.v5ent.game.utils.GamePreferences;

public class SomeWars extends DirectedGame {

	@Override
	public void create () {
		// Load assets
		Assets.instance.init(new AssetManager());

		// Load preferences for audio settings and start playing music
		GamePreferences.instance.load();
		AudioManager.instance.play(Assets.instance.music.song01);

		// Start game at menu screen
		ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
		setScreen(new MenuScreen(this), transition);
	}

}
