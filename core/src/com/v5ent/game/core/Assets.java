package com.v5ent.game.core;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.utils.Constants;


import static com.v5ent.game.utils.Constants.CARDLIGHT;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	public Skin STATUSUI_SKIN ;

	private AssetManager assetManager;

	public BitmapFont font;

	public Texture background;
	public Texture selectBg;
	public Texture block;
	public Texture cardBackground;
	public Texture cardLight;
	public Texture cardDisable;

	public Texture moveCell;
	public Texture fightCell;
	
	public Map<String,AssetHero> assetHeros = new HashMap<String, AssetHero>();

	public Map<String,Texture> assetCards = new HashMap<String, Texture>();

	// singleton: prevent instantiation from other classes
	private Assets () {
	}

	public class AssetHero {
		public final Animation magicRightAnimation;

		public final Animation idleRightAnimation;
		public final Animation walkRightAnimation;
		public final Animation fightRightAnimation;
		public final Animation beatenRightAnimation;

		public AssetHero (TextureAtlas atlas) {
			Array<TextureRegion> idleRightFrames = new Array<TextureRegion>(6);
			for (int i = 0; i < 6; i++) {
				idleRightFrames.insert(i, atlas.findRegion("idleRight"+i));
			}
			idleRightAnimation = new Animation(0.2f, idleRightFrames, Animation.PlayMode.LOOP);
			Array<TextureRegion> walkRightFrames = new Array<TextureRegion>(5);
			for (int i = 0; i < 5; i++) {
				walkRightFrames.insert(i, atlas.findRegion("walkRight"+i));
			}
			walkRightAnimation = new Animation(0.1f, walkRightFrames, Animation.PlayMode.LOOP);
			Array<TextureRegion> fightRightFrames = new Array<TextureRegion>(6);
			for (int i = 0; i < 6; i++) {
				fightRightFrames.insert(i, atlas.findRegion("fightRight"+i));
			}
			fightRightAnimation = new Animation(0.05f, fightRightFrames, Animation.PlayMode.NORMAL);
			Array<TextureRegion> beatenRightFrames = new Array<TextureRegion>(3);
			for (int i = 0; i < 3; i++) {
				beatenRightFrames.insert(i, atlas.findRegion("beatenRight"+i));
			}
			beatenRightAnimation = new Animation(0.2f, beatenRightFrames, Animation.PlayMode.NORMAL);
			Array<TextureRegion> magicRightFrames = new Array<TextureRegion>(3);
			for (int i = 0; i < 3; i++) {
				magicRightFrames.insert(i, atlas.findRegion("magicRight"+i));
			}
			magicRightAnimation = new Animation(0.2f, magicRightFrames, Animation.PlayMode.NORMAL);
		}
	}


	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.BACKGROUND, Texture.class);
		assetManager.load(Constants.SELECTBG, Texture.class);
		assetManager.load(Constants.BLOCK, Texture.class);
		assetManager.load(Constants.CARDBACKGROUND, Texture.class);
		assetManager.load(Constants.CARDDISABLE, Texture.class);
		assetManager.load(Constants.CARDLIGHT, Texture.class);
		assetManager.load(Constants.MOVE_CELL, Texture.class);
		assetManager.load(Constants.FIGHT_CELL, Texture.class);
		font = new BitmapFont(Gdx.files.internal("data/num.fnt"));
		STATUSUI_SKIN = new Skin(Gdx.files.internal("skins/statusui.json"));
		//TODO:当前提供的所有英雄
		int heroCnt = 1;
		//look all hero's pack
		for(int i=1;i<=heroCnt ;i++){
			assetManager.load("heros/00"+i+".pack", TextureAtlas.class);
			assetManager.load("cards/00"+i+".png", Texture.class);
		}
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		background = assetManager.get(Constants.BACKGROUND);
		block = assetManager.get(Constants.BLOCK);
		selectBg = assetManager.get(Constants.SELECTBG);
		cardBackground = assetManager.get(Constants.CARDBACKGROUND);
		cardLight = assetManager.get(Constants.CARDLIGHT);
		cardDisable = assetManager.get(Constants.CARDDISABLE);
		moveCell = assetManager.get(Constants.MOVE_CELL);
		fightCell = assetManager.get(Constants.FIGHT_CELL);
		for(int i=1;i<=heroCnt ;i++){
			// create game resource objects
			TextureAtlas atlas = assetManager.get("heros/00"+i+".pack");
			assetHeros.put("00"+i,new AssetHero(atlas));
			Texture card = assetManager.get("cards/00"+i+".png");
			assetCards.put("00"+i,card);
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
