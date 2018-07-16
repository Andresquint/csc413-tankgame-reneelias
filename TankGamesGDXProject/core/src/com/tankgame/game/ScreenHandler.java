package com.tankgame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tankgame.game.Drawables.Drawable;
import com.tankgame.game.Drawables.Tank;

import java.util.HashMap;

public class ScreenHandler extends ApplicationAdapter {
	SpriteBatch batch;
	Tank tank1, tank2;
	Drawable background;
	int screenWidth, screenHeight;
	HashMap<String, TextureRegion> textureMap;
	
	@Override
	public void create () {
		screenWidth = 1280;
		screenHeight = 720;
		Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
		Gdx.graphics.setResizable(false);
		batch = new SpriteBatch();
		textureMap = new HashMap<String, TextureRegion>();
		textureMap.put("Tank1", new TextureRegion(new Texture("Tank1.gif"), 7, 9, 53, 45));
		textureMap.put("Tank2", new TextureRegion(new Texture("Tank2.gif"), 7, 9, 53, 45));
		textureMap.put("Bullet", new TextureRegion(new Texture("Shell.gif"), 6, 8, 12, 8));
		tank1 = new Tank(Color.WHITE, textureMap, 0, 0, 50, 50, true, 10, 5);
		tank2 = new Tank(Color.WHITE, textureMap, 800, 0, 50, 50, false, 10, 5);
		background = new Drawable(Color.WHITE, new Texture("Background.bmp"), 0, 0, 1280, 720);
	}

	@Override
	public void render () {
		tank1.Update(Gdx.input, screenWidth, screenHeight);
		tank2.Update(Gdx.input, screenWidth, screenHeight);

		draw();
	}

	public void draw()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.Draw(batch);
		tank1.Draw(batch);
		tank2.Draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
