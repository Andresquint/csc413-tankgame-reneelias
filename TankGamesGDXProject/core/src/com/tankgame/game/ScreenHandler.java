package com.tankgame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.tankgame.game.Drawables.Drawable;
import com.tankgame.game.Drawables.Tank;
import com.tankgame.game.Drawables.WallPiece;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ScreenHandler extends ApplicationAdapter {
	SpriteBatch cameraBatch, regularBatch, miniMapBatch;
	Tank tank1, tank2;
	ArrayList<Drawable> background;
	int screenWidth, screenHeight, mapWidth, mapHeight, miniMapWidth, miniMapHeight;
	HashMap<String, TextureRegion> textureMap;
	WallPiece[][] walls;
	BitmapFont font;
	Animation<TextureRegion> explosion;
	OrthographicCamera cam1, cam2, miniCam;
	
	@Override
	public void create () {
		screenWidth = 960;
		screenHeight = 720;
		mapWidth = 1900;
		mapHeight = 1450;
		miniMapWidth = mapWidth / 11;
		miniMapHeight = mapHeight / 11;
		Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
		Gdx.graphics.setResizable(false);
		cameraBatch =  new SpriteBatch();
		regularBatch =  new SpriteBatch();
		miniMapBatch = new SpriteBatch();
		textureMap = new HashMap<String, TextureRegion>();
		textureMap.put("Tank1", new TextureRegion(new Texture("Tank1.gif"), 7, 9, 53, 45));
		textureMap.put("Tank2", new TextureRegion(new Texture("Tank2.gif"), 7, 9, 53, 45));
		textureMap.put("Bullet", new TextureRegion(new Texture("Shell.gif"), 6, 8, 12, 8));
		textureMap.put("Wall1", new TextureRegion(new Texture("Wall1.gif"), 0, 0, 32, 32));
		textureMap.put("Wall2", new TextureRegion(new Texture("Wall2.gif"), 0, 0, 32, 32));
		background = new ArrayList<Drawable>();
		int backgroundX = -320,  backgroundY = -480;
		while(true)
		{
			background.add(new Drawable(Color.WHITE, new Texture("Background.bmp"), backgroundX, backgroundY, 320, 240));
			backgroundX += 320;
			if(backgroundX >= mapWidth + 320 && backgroundY >= mapHeight + 240)
			{
				break;
			}
			if(backgroundX >= mapWidth + 320)
			{
				backgroundX = -320;
				backgroundY += 240;
			}
		}
		explosion = GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL, Gdx.files.internal("Explosion_large_trans.gif").read());
		explosion.setFrameDuration(.045f);
		tank1 = new Tank(Color.WHITE, textureMap, explosion, mapWidth / 4 - 25, mapHeight - 50, 50, 50, true, 10, 5);
		tank2 = new Tank(Color.WHITE, textureMap, explosion, mapWidth * 3 / 4 - 25, 0, 50, 50, false, 10, 5);

		cam1 = new OrthographicCamera(screenWidth / 2, screenHeight);
		cam1.zoom = 1.25f;
		cam2 = new OrthographicCamera(screenWidth / 2, screenHeight);
		cam2.zoom = 1.25f;
		miniCam = new OrthographicCamera(mapWidth + 100, mapHeight + 100);
		miniCam.position.set(mapWidth / 2, mapHeight/ 2, 0);

		initialiazeWalls();

		font = new BitmapFont();
	}

	@Override
	public void render () {
		tank1.Update(Gdx.input, mapWidth, mapHeight, walls, tank2, Gdx.graphics.getDeltaTime());
		tank2.Update(Gdx.input, mapWidth, mapHeight, walls, tank1, Gdx.graphics.getDeltaTime());

		cam1.position.set(tank1.getX() + tank1.getWidth() / 2, tank1.getY() + tank1.getHeight() / 2, 0);
		cam1.update();
		cam2.position.set(tank2.getX() + tank1.getWidth() / 2, tank2.getY() + tank1.getHeight() / 2, 0);
		cam2.update();
		miniCam.update();

		draw();
	}

	public void draw()
	{
		/**Left Screen Draw*/
		Gdx.gl.glViewport(0, 0, screenWidth / 2, screenHeight);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cameraBatch.setProjectionMatrix(cam1.combined);
		cameraBatch.begin();
		for(Drawable back : background)
		{
			back.Draw(cameraBatch);
		}
		tank1.Draw(cameraBatch);
		tank2.Draw(cameraBatch);
		for(WallPiece[] wall: walls)
		{
			for(WallPiece w : wall)
			{
				w.Draw(cameraBatch);
			}
		}

		cameraBatch.end();

		/**Right Screen Draw*/
		Gdx.gl.glViewport(screenWidth / 2, 0, screenWidth / 2, screenHeight);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		cameraBatch.setProjectionMatrix(cam2.combined);
		cameraBatch.begin();
		for(Drawable back : background)
		{
			back.Draw(cameraBatch);
		}
		tank1.Draw(cameraBatch);
		tank2.Draw(cameraBatch);
		for(WallPiece[] wall: walls)
		{
			for(WallPiece w : wall)
			{
				w.Draw(cameraBatch);
			}
		}

		cameraBatch.end();

		/**Mini-Map Draw*/
		Gdx.gl.glViewport(screenWidth / 2 - miniMapWidth / 2, 0, miniMapWidth, miniMapHeight);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		miniMapBatch.setProjectionMatrix(miniCam.combined);
		miniMapBatch.begin();
		for(Drawable back : background)
		{
			back.Draw(miniMapBatch);
		}
		tank1.MiniDraw(miniMapBatch);
		tank2.MiniDraw(miniMapBatch);
		for(WallPiece[] wall: walls)
		{
			for(WallPiece w : wall)
			{
				w.Draw(miniMapBatch);
			}
		}

		miniMapBatch.end();

		Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		regularBatch.begin();

		font.draw(regularBatch, Integer.toString(tank1.getHealth()), 0, 20);
		font.draw(regularBatch, Integer.toString(tank2.getHealth()), screenWidth - 20, 20);
		regularBatch.end();
	}

	public void initialiazeWalls()
	{

		walls = new WallPiece[7][];

		/**Center Divider*/
		int startingY = mapHeight / 2 - 50 * 5;
		int startingX = mapWidth / 2 - 25;
		walls[0] = new WallPiece[10];
		for(int i = 0; i < walls[0].length; i++)
		{
			walls[0][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX, startingY + 50 * i, 50, 50);
		}

		/**Quarter Divider Left*/
		startingY = mapHeight / 2 - 25;
		startingX = mapWidth / 4 - 100;
		walls[1] = new WallPiece[4];
		for(int i = 0; i < walls[1].length; i++)
		{
			walls[1][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX + 50 * i, startingY, 50, 50);
		}

		/**Quarter Divider Right*/
		startingX = (mapWidth / 4) * 3 - 100;
		walls[2] = new WallPiece[4];
		for(int i = 0; i < walls[2].length; i++)
		{
			walls[2][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX + 50 * i, startingY, 50, 50);
		}

		/**Top Border*/
		startingX = 0;
		startingY = mapHeight;
		walls[3] = new WallPiece[mapWidth / 50];
		for(int i = 0; i < walls[3].length; i++)
		{
			walls[3][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX + 50 * i, startingY, 50, 50);
		}

		/**Left Border*/
		startingX = -50;
		startingY = -50;
		walls[4] = new WallPiece[mapHeight / 50 + 2];
		for(int i = 0; i < walls[4].length; i++)
		{
			walls[4][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX , startingY+ 50 * i, 50, 50);
		}


		/**Bottom Border*/
		startingX = 0;
		startingY = -50;
		walls[5] = new WallPiece[mapWidth / 50];
		for(int i = 0; i < walls[3].length; i++)
		{
			walls[5][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX + 50 * i, startingY, 50, 50);
		}

		/**Right Border*/
		startingX = mapWidth;
		startingY = -50;
		walls[6] = new WallPiece[mapHeight / 50 + 2];
		for(int i = 0; i < walls[4].length; i++)
		{
			walls[6][i] = new WallPiece(Color.WHITE, textureMap.get("Wall1"), startingX , startingY+ 50 * i, 50, 50);
		}
	}

	@Override
	public void dispose () {
		cameraBatch.dispose();
		regularBatch.dispose();
	}
}
