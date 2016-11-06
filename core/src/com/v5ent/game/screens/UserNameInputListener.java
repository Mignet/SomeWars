package com.v5ent.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.v5ent.game.SomeWars;

public class UserNameInputListener implements TextInputListener {
	
	   private String TAG = UserNameInputListener.class.getName();
	   SomeWars game;
	public UserNameInputListener(SomeWars gameIns) {
		this.game = gameIns;
	}

	@Override
	   public void input (String text) {
			game.prefs.putString("account", text);
			game.prefs.flush();
		   Gdx.app.debug(TAG , text);
	   }

	   @Override
	   public void canceled () {
		   Gdx.app.debug(TAG , "canceled");
	   }
	}