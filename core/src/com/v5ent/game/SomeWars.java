package com.v5ent.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.v5ent.game.core.Assets;
import com.v5ent.game.core.WorldController;
import com.v5ent.game.core.WorldRenderer;

public class SomeWars extends Game {

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean paused;

	@Override
	public void create () {
		// Load assets
		Assets.instance.init(new AssetManager());
		// Initialize controller and renderer
		worldController = new WorldController();
		GestureDetector gestureDetector = new GestureDetector(10, 0.5f, 2, 0.15f, worldController);
		Gdx.input.setInputProcessor(gestureDetector);
		worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void render () {
		// Do not update game world when paused.
		if (!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	}

	@Override
	public void resize (int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause () {
		paused = true;
	}

	@Override
	public void resume () {
		Assets.instance.init(new AssetManager());
		paused = false;
	}

	@Override
	public void dispose () {
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

}
