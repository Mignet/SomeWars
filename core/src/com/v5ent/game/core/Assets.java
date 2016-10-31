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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;

	public Texture background;
	
	public Hero1 bunny1;
	public Hero2 bunny2;
	public Hero3 bunny3;
	public Hero4 bunny4;
	public Hero5 bunny5;
	public Hero6 bunny6;

	// singleton: prevent instantiation from other classes
	private Assets () {
	}

	public class Hero1 {
		public final AtlasRegion head;
		public final AtlasRegion body;

		public Hero1 (TextureAtlas atlas) {
			head = atlas.findRegion("1");
			body = atlas.findRegion("1");
		}
	}
	public class Hero2 {
		public final AtlasRegion stand;
		
		public Hero2 (TextureAtlas atlas) {
			stand = atlas.findRegion("2");
		}
	}
	public class Hero3 {
		public final AtlasRegion stand;
		
		public Hero3 (TextureAtlas atlas) {
			stand = atlas.findRegion("3");
		}
	}
	public class Hero4 {
		public final AtlasRegion stand;
		
		public Hero4 (TextureAtlas atlas) {
			stand = atlas.findRegion("4");
		}
	}
	public class Hero5 {
		public final AtlasRegion stand;
		
		public Hero5 (TextureAtlas atlas) {
			stand = atlas.findRegion("5");
		}
	}
	public class Hero6 {
		public final AtlasRegion stand;
		
		public Hero6 (TextureAtlas atlas) {
			stand = atlas.findRegion("6");
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
		bunny1 = new Hero1(atlas);
		bunny2 = new Hero2(atlas);
		bunny3 = new Hero3(atlas);
		bunny4 = new Hero4(atlas);
		bunny5 = new Hero5(atlas);
		bunny6 = new Hero6(atlas);
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
