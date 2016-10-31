package com.v5ent.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.v5ent.game.SomeWars;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = true;
		initialize(new SomeWars(), config);
//		initialize(new ScrollPaneTest(), config);
//		initialize(new MultiplayerDemo(), config);
	}
}
