package com.v5ent.game.core;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	public Skin STATUSUI_SKIN ;

	private AssetManager assetManager;

	public Texture background;
	public Texture moveCell;
	public Texture fightCell;
	
	public Map<String,AssetHero> assetHeros = new HashMap<String, AssetHero>();

	// singleton: prevent instantiation from other classes
	private Assets () {
	}

	public class AssetHero {
		public final Animation idleRightAnimation;
		public final Animation walkRightAnimation;

		public AssetHero (TextureAtlas atlas) {
			Array<TextureRegion> idleRightFrames = new Array<TextureRegion>(4);
			for (int i = 0; i < 6; i++) {
				idleRightFrames.insert(i, atlas.findRegion("idleRight"+i));
			}
			idleRightAnimation = new Animation(1/6f, idleRightFrames, Animation.PlayMode.LOOP);
			Array<TextureRegion> walkRightFrames = new Array<TextureRegion>(4);
			for (int i = 0; i < 5; i++) {
				walkRightFrames.insert(i, atlas.findRegion("walkRight"+i));
			}
			walkRightAnimation = new Animation(0.2f, walkRightFrames, Animation.PlayMode.LOOP);
		}
	}


	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.BACKGROUND, Texture.class);
		assetManager.load(Constants.MOVE_CELL, Texture.class);
		assetManager.load(Constants.FIGHT_CELL, Texture.class);
		STATUSUI_SKIN = new Skin(Gdx.files.internal("skins/statusui.json"));
		//TODO:当前提供的所有英雄
		int heroCnt = 2;
		//look all hero's pack
		for(int i=1;i<=heroCnt ;i++){
			assetManager.load("heros/00"+i+".pack", TextureAtlas.class);
		}
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		background = assetManager.get(Constants.BACKGROUND);
		moveCell = assetManager.get(Constants.MOVE_CELL);
		fightCell = assetManager.get(Constants.FIGHT_CELL);
		for(int i=1;i<=heroCnt ;i++){
			TextureAtlas atlas = assetManager.get("heros/00"+i+".pack");
			// create game resource objects
			assetHeros.put("00"+i,new AssetHero(atlas));
		}
	}

	@Override
	public void dispose () {
		assetManager.dispose();
	}

	@Override
	public void error(AssetDescriptor assetDesc, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + assetDesc.fileName + "'", throwable);
	}

}
