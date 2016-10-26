package com.v5ent.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.v5ent.game.ScrollPaneTest;
import com.v5ent.game.SomeWars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new SomeWars(), config);
		new LwjglApplication(new ScrollPaneTest(), config);
	}
}
