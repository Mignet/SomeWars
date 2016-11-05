package com.v5ent.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;

public class UserNameInputListener implements TextInputListener {
	
	   private String TAG = UserNameInputListener.class.getName();

	@Override
	   public void input (String text) {
		   Gdx.app.debug(TAG , text);
		   
	   }

	   @Override
	   public void canceled () {
		   Gdx.app.debug(TAG , "canceled");
	   }
	}