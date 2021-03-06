package com.v5ent.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.v5ent.game.SomeWars;
import com.v5ent.game.utils.Constants;

public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Some Wars";
		cfg.useGL30 = false;
		cfg.width = (int)Constants.GAME_WIDTH;
		cfg.height = (int)Constants.GAME_HEIGHT;
		cfg.addIcon("icon.png", Files.FileType.Internal);
		Gdx.app  = new LwjglApplication(new SomeWars(), cfg);
		 //Gdx.app.setLogLevel(Application.LOG_INFO);
		 Gdx.app.setLogLevel(Application.LOG_DEBUG);
		 //Gdx.app.setLogLevel(Application.LOG_ERROR);
		 //Gdx.app.setLogLevel(Application.LOG_NONE);
	}
}
