package com.v5ent.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.v5ent.game.core.Assets;

import static java.awt.SystemColor.text;

public class Card extends CheckBox {

	String heroId;
	Texture backgroud;
	Texture cardLight;
	Texture card;
	Texture cardDisable;
	public Card(String heroid, Skin skin) {
		super("Hero:"+heroid, skin);
		this.setWidth(196);
		this.setHeight(250);
		this.setUserObject(heroid);
		backgroud = Assets.instance.cardBackground;
		cardLight = Assets.instance.cardLight;
		heroId = heroid;
		card = Assets.instance.assetCards.get(heroId);
		cardDisable = Assets.instance.cardDisable;
		if(card == null){
			this.setDisabled(true);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(isChecked()){
			batch.draw(cardLight,getX(),getY());
		}
		batch.draw(backgroud,getX(),getY());
		if(card!=null){
			batch.draw(card,getX()+18,getY()+50);
		}else{
			batch.draw(cardDisable,getX(),getY());
		}
//		super.draw(batch, parentAlpha);
	}

}
