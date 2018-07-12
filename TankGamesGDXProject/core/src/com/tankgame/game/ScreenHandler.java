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

public class ScreenHandler extends ApplicationAdapter {
	SpriteBatch batch;
	Tank tank;
	Drawable background;
	int screenWidth, screenHeight;
	
	@Override
	public void create () {
		screenWidth = 1280;
		screenHeight = 720;
		Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
		Gdx.graphics.setResizable(false);
		batch = new SpriteBatch();
		tank = new Tank(Color.WHITE, new TextureRegion(new Texture("Tank1.gif"), 7, 9, 53, 45), 0, 0, 100, 100, true, 10, 5);
		background = new Drawable(Color.WHITE, new Texture("Background.bmp"), 0, 0, 1280, 720);
	}

	@Override
	public void render () {

		tank.Update(Gdx.input, screenWidth, screenHeight);

		draw();
	}

	public void draw()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.Draw(batch);
		tank.Draw(batch);
		batch.end();
	}

	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
