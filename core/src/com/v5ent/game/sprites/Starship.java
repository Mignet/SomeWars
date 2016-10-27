package com.v5ent.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 */
public class Starship extends Sprite {
	String id;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	Vector2 previousPosition;
    public Starship(Texture texture){
        super(texture);
        previousPosition = new Vector2(getX(), getY());

    }

    public boolean hasMoved(){
        if(previousPosition.x != getX() || previousPosition.y != getY()){
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }

}
