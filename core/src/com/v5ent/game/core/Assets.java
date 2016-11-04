/*******************************************************************************
 * Copyright 2013 Andreas Oehlke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package com.v5ent.game.core;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Array;
import com.v5ent.game.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;

	public Texture background;
	
	public Map<String,AssetHero> assetHeros = new HashMap<String, AssetHero>();

	// singleton: prevent instantiation from other classes
	private Assets () {
	}

	public class AssetHero {
		public final Animation idleLeftAnimation;
		public final Animation walkLeftAnimation;

		public AssetHero (TextureAtlas atlas) {
			Array<TextureRegion> idleLeftFrames = new Array<TextureRegion>(4);
			for (int i = 0; i < 4; i++) {
				idleLeftFrames.insert(i, atlas.findRegion("idleLeft"+i));
			}
			idleLeftAnimation = new Animation(0.25f, idleLeftFrames, Animation.LOOP);
			Array<TextureRegion> walkLeftFrames = new Array<TextureRegion>(4);
			for (int i = 0; i < 4; i++) {
				walkLeftFrames.insert(i, atlas.findRegion("walkLeft"+i));
			}
			walkLeftAnimation = new Animation(0.25f, walkLeftFrames, Animation.LOOP);
		}
	}


	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.HEROS, TextureAtlas.class);
		assetManager.load(Constants.BACKGROUND, Texture.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		TextureAtlas atlas = assetManager.get(Constants.HEROS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		background = assetManager.get(Constants.BACKGROUND);
		// create game resource objects
		assetHeros.put("hero1",new AssetHero(atlas));
	}

	@Override
	public void dispose () {
		assetManager.dispose();
	}

	@Override
	public void error(AssetDescriptor assetDesc, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + assetDesc.fileName + "'", (Exception)throwable);
	}

}
