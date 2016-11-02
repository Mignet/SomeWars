package com.v5ent.game.entities;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero extends Sprite{
	private static final String TAG = Hero.class.getSimpleName();
	private static final String defaultSpritePath = "sprites/characters/Warrior.png";
	
	public enum State {
		IDLE, WALKING,MAGIC,FIGHT,BEATEN,DEAD
	}

	public enum Direction {
		UP, RIGHT, DOWN, LEFT;
	}
	
	private String id;
	private String name;
	private String desc;
	/**(0,0)-(1,0),(2,0) */
	private List<Vector2> moveRange;
	/**(0,0)-(1,0),(2,0) */
	private List<Vector2> fightRange;
	
	private int mapX;
	private int mapY;
	
	public Hero(AtlasRegion stand) {
		super(stand);
	}
	public int getMapX() {
		return mapX;
	}
	public void setMapX(int mapX) {
		this.mapX = mapX;
	}
	public int getMapY() {
		return mapY;
	}
	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
//	private Vector2 mapLoc;
	public List<Vector2> getMoveRange() {
		return moveRange;
	}
	public List<Vector2> getFightRange() {
		return fightRange;
	}
}
