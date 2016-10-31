package com.v5ent.game.desktop;

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
		new LwjglApplication(new SomeWars(), cfg);
	}
}
