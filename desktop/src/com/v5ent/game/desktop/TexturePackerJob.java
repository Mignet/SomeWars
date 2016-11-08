package com.v5ent.game.desktop;

//import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;


public class TexturePackerJob {
	private static String DIR = "../android/";
	public static void main(String[] args) {
		TexturePacker.process(DIR+"assets/heros/",DIR+ "assets/heros/", "heros.pack");
	}
}


