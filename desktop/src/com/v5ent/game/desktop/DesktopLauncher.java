package com.v5ent.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.v5ent.game.SomeWars;

public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Some Wars";
		cfg.useGL20 = true;
		cfg.width = 1136;
		cfg.height = 640;
		Gdx.app  = new LwjglApplication(new SomeWars(), cfg);
		 //Gdx.app.setLogLevel(Application.LOG_INFO);
		 Gdx.app.setLogLevel(Application.LOG_DEBUG);
		 //Gdx.app.setLogLevel(Application.LOG_ERROR);
		 //Gdx.app.setLogLevel(Application.LOG_NONE);
	}
}
