package com.v5ent.game.entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero extends Sprite implements InputProcessor{
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
		Gdx.input.setInputProcessor(this);
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
	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float pointerX = InputTransform.getCursorToModelX(windowWidth, screenX);
	      float pointerY = InputTransform.getCursorToModelY(windowHeight, screenY);
	      for(int i = 0; i < balloons.size(); i++)
	      {
	          if(balloons.get(i).contains(pointerX, pointerY))
	          {
	              balloons.get(i).setSelected(true);
	          }
	      }
	      return true;
		return false;
	}
	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
