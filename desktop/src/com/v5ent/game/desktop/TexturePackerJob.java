package com.v5ent.game.desktop;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;


public class TexturePackerJob {
	private static String DIR = "../android/";
	public static void main(String[] args) {
		TexturePacker2.process(DIR+"assets/heros/",DIR+ "assets/heros/", "heros.pack");
	}
}


