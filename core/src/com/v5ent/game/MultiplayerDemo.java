package com.v5ent.game;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Json;
import com.v5ent.game.entities.Starship;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class MultiplayerDemo extends Game {
	SpriteBatch batch;
	private Socket socket;
	String id;
	Starship player;
	Texture playerShip;
	Texture friendlyShip;
	HashMap<String, Starship> friendlyPlayers;
	Json json = new Json();

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerShip = new Texture("playerShip2.png");
		friendlyShip = new Texture("playerShip.png");
		friendlyPlayers = new HashMap<String, Starship>();
		connectSocket();
		configSocketEvents();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void handleInput(float dt){
		if(player != null) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				player.setPosition(player.getX() + (-200 * dt), player.getY());
			} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				player.setPosition(player.getX() + (+200 * dt), player.getY());
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput(Gdx.graphics.getDeltaTime());

		batch.begin();
		if(player != null){
			player.draw(batch);
		}
		for(HashMap.Entry<String, Starship> entry : friendlyPlayers.entrySet()){
			entry.getValue().draw(batch);
		}
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		playerShip.dispose();
		friendlyShip.dispose();
	}

	public void connectSocket(){
		try {
			socket = IO.socket("http://localhost:8080");
			socket.connect();
		} catch(Exception e){
			System.out.println(e);
		}
	}
	public void configSocketEvents(){
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
				player = new Starship(playerShip);
			}
		}).on("socketID", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				String id =((org.json.JSONObject)(args[0])).getString("id");
				Gdx.app.log("SocketIO", "My ID: " + id);
			}
		}).on("newPlayer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				String id =((org.json.JSONObject)(args[0])).getString("id");
				try {
					Gdx.app.log("SocketIO", "New Player Connect: " + id);
					friendlyPlayers.put(id, new Starship(friendlyShip));
				}catch(Exception e){
					Gdx.app.log("SocketIO", "Error getting New PlayerID");
				}
			}
		}).on("playerDisconnected", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				String id =((org.json.JSONObject)(args[0])).getString("id");
				try {
					friendlyPlayers.remove(id);
				}catch(Exception e){
					Gdx.app.log("SocketIO", "Error getting disconnected PlayerID");
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				try {
					for(int i = 0; i < objects.length(); i++){
						Starship coopPlayer = new Starship(friendlyShip);
						Vector2 position = new Vector2();
						position.x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
						position.y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
						coopPlayer.setPosition(position.x, position.y);

						friendlyPlayers.put(objects.getJSONObject(i).getString("id"), coopPlayer);
					}
				} catch(JSONException e){

				}
			}
		});
	}
}
