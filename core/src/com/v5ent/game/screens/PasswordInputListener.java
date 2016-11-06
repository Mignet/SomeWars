package com.v5ent.game.screens;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.v5ent.game.utils.PasswordHash;

public class PasswordInputListener implements TextInputListener {
	
	   private String TAG = PasswordInputListener.class.getName();

	@Override
	   public void input (String password) {
		   try {
			Gdx.app.debug(TAG , PasswordHash.createHash(password));
			} catch (NoSuchAlgorithmException e) {
				Gdx.app.error(TAG , e.getMessage());
			} catch (InvalidKeySpecException e) {
				Gdx.app.error(TAG , e.getMessage());
			}
	   }

	   @Override
	   public void canceled () {
		   Gdx.app.debug(TAG , "canceled");
	   }
	}