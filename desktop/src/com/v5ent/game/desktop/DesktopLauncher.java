package com.v5ent.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.v5ent.game.MultiplayerDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new ScrollPaneTest(), config);
		Gdx.app = new LwjglApplication(new MultiplayerDemo(), config);
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

	}
}
