package com.tankgame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tankgame.game.Drawables.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ScreenHandler extends ApplicationAdapter {
    SpriteBatch cameraBatch, regularBatch, miniMapBatch;
    Tank tank1, tank2;
    ArrayList<Vector2> backgroundLocations;
    Drawable centerDivider;
    int screenWidth, screenHeight, mapWidth, mapHeight, miniMapWidth, miniMapHeight;
    HashMap<String, TextureRegion> textureMap;
    ArrayList<WallPiece> walls;
    //	WallPiece[][] walls;
    BitmapFont font;
    Animation<TextureRegion> explosion;
    OrthographicCamera cam1, cam2, miniCam;
    Powerup powerup;
    Music song;
    Sound explosionSound;
    FileHandle level;

    @Override
    public void create() {
        screenWidth = 960;
        screenHeight = 720;
//		mapWidth = 1900;
//		mapHeight = 1450;
        mapWidth = 50 * 50;
        mapHeight = 41 * 50;
        miniMapWidth = mapWidth / 14;
        miniMapHeight = mapHeight / 14;
        Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
        Gdx.graphics.setResizable(false);
        cameraBatch = new SpriteBatch();
        regularBatch = new SpriteBatch();
        miniMapBatch = new SpriteBatch();
        textureMap = new HashMap<String, TextureRegion>();
        textureMap.put("Tank1", new TextureRegion(new Texture("Tank1_trans.gif"), 7, 9, 53, 45));
        textureMap.put("Tank2", new TextureRegion(new Texture("Tank2_trans.gif"), 7, 9, 53, 45));
        textureMap.put("Bullet", new TextureRegion(new Texture("Shell.gif"), 6, 8, 12, 8));
        textureMap.put("Wall1", new TextureRegion(new Texture("Wall1.gif"), 0, 0, 32, 32));
        textureMap.put("Wall2", new TextureRegion(new Texture("Wall2.gif"), 0, 0, 32, 32));
        textureMap.put("WhitePixel", new TextureRegion(new Texture("WhitePixel.png"), 0, 0, 1, 1));
        textureMap.put("HealthBarEmpty", new TextureRegion(new Texture("HealthBarEmpty.png"), 0, 0, 301, 50));
        textureMap.put("HealthBarFiller", new TextureRegion(new Texture("HealthBarFiller.png"), 0, 0, 301, 50));
        textureMap.put("RocketPickup", new TextureRegion(new Texture("RocketPickup.png"), 0, 0, 32, 32));
        textureMap.put("Rocket", new TextureRegion(new Texture("Rocket.png"), 0, 0, 21, 12));
        textureMap.put("LifePowerup", new TextureRegion(new Texture("LifePowerupWhite.png"), 0, 0, 151, 151));
        textureMap.put("LunarBackground", new TextureRegion(new Texture("LunarSurface400.png"), 0, 0, 400, 400));
        textureMap.put("WhiteBlock", new TextureRegion(new Texture("ProjectUtumno_full.png"), 1952, 544, 32, 32));
        textureMap.put("BlackBlock", new TextureRegion(new Texture("ProjectUtumno_full.png"), 1824, 544, 32, 32));
        textureMap.put("BrokenBlackBlock", new TextureRegion(new Texture("ProjectUtumno_full.png"), 1856, 544, 32, 32));
        textureMap.put("UnbreakableBlack", new TextureRegion(new Texture("ProjectUtumno_full.png"), 704, 576, 32, 32));

        backgroundLocations = new ArrayList<Vector2>();
        int backgroundX = -400, backgroundY = -800;
        while (true) {
            backgroundLocations.add(new Vector2(backgroundX, backgroundY));
            //background.add(new Drawable(Color.WHITE, new Texture("Background.bmp"), backgroundX, backgroundY, 320, 240));
            backgroundX += 400;
            if (backgroundX >= mapWidth + 400 && backgroundY >= mapHeight + 400) {
                break;
            }
            if (backgroundX >= mapWidth + 400) {
                backgroundX = -400;
                backgroundY += 400;
            }
        }
        explosion = GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL, Gdx.files.internal("Explosion_large_trans.gif").read());
        explosion.setFrameDuration(.045f);

        tank1 = new Tank(Color.WHITE, textureMap, explosion, mapWidth / 4 - 25, mapHeight - 100, 50, 50, true, 10, 5, 10, 3, Gdx.audio.newSound(Gdx.files.local("Explosion_large.wav")), Gdx.audio.newSound(Gdx.files.local("TankFiring.mp3")));
        tank2 = new Tank(Color.WHITE, textureMap, explosion, mapWidth * 3 / 4 - 25, 50, 50, 50, false, 10, 5, 10, 3, Gdx.audio.newSound(Gdx.files.local("Explosion_large.wav")), Gdx.audio.newSound(Gdx.files.local("TankFiring.mp3")));
        powerup = new Powerup(Color.WHITE, textureMap, (int) (mapWidth / 2 - (tank1.getWidth() * .85f) / 2), 200, (int) (tank1.getWidth() * .85f), (int) (tank1.getWidth() * .85f));

        centerDivider = new Drawable(Color.BLACK, textureMap.get("WhitePixel"), screenWidth / 2 - 2, 0, 4, screenHeight);

        cam1 = new OrthographicCamera(screenWidth / 2, screenHeight);
        cam1.zoom = 1.4f;
        cam2 = new OrthographicCamera(screenWidth / 2, screenHeight);
        cam2.zoom = 1.4f;
        miniCam = new OrthographicCamera(mapWidth, mapHeight);
        miniCam.position.set(mapWidth / 2, mapHeight / 2, 0);

        song = Gdx.audio.newMusic(Gdx.files.local("Music.mp3"));
        song.setLooping(true);
		song.play();

        initialiazeWalls();
        font = new BitmapFont();

    }

    @Override
    public void render() {
        powerup.Update(Gdx.graphics.getDeltaTime(), mapWidth, mapHeight);
        tank1.Update(Gdx.input, mapWidth, mapHeight, walls, tank2, Gdx.graphics.getDeltaTime(), powerup);
        tank2.Update(Gdx.input, mapWidth, mapHeight, walls, tank1, Gdx.graphics.getDeltaTime(), powerup);

        draw();
    }

    public void draw() {
        /**Left Screen Draw*/
        Gdx.gl.glViewport(0, 0, screenWidth / 2, screenHeight);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam1.position.set(tank1.getX() + tank1.getWidth() / 2, tank1.getY() + tank1.getHeight() / 2, 0);
        cam1.update();
        cameraBatch.setProjectionMatrix(cam1.combined);
        cameraBatch.begin();
        for (Vector2 back : backgroundLocations) {
            cameraBatch.draw(textureMap.get("LunarBackground"), back.x, back.y);
        }
        powerup.Draw(cameraBatch);
        tank1.Draw(cameraBatch);
        tank2.Draw(cameraBatch);
//		for(WallPiece[] wall: walls)
//		{
//			for(WallPiece w : wall)
//			{
//				w.Draw(cameraBatch);
//			}
//		}

        for (WallPiece w : walls) {
            w.Draw(cameraBatch);
        }

        cameraBatch.end();

        /**Right Screen Draw*/
        Gdx.gl.glViewport(screenWidth / 2, 0, screenWidth / 2, screenHeight);
        Gdx.gl.glClearColor(1, 0, 0, 1);

        cam2.position.set(tank2.getX() + tank1.getWidth() / 2, tank2.getY() + tank1.getHeight() / 2, 0);
        cam2.update();
        cameraBatch.setProjectionMatrix(cam2.combined);
        cameraBatch.begin();
        for (Vector2 back : backgroundLocations) {
            cameraBatch.draw(textureMap.get("LunarBackground"), back.x, back.y);
        }
        powerup.Draw(cameraBatch);
        tank1.Draw(cameraBatch);
        tank2.Draw(cameraBatch);
//		for(WallPiece[] wall: walls)
//		{
//			for(WallPiece w : wall)
//			{
//				w.Draw(cameraBatch);
//			}
//		}

        for (WallPiece w : walls) {
            w.Draw(cameraBatch);
        }

        cameraBatch.end();

        /**HUD Draw*/
        Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        regularBatch.begin();

//		font.getData().setScale(1.75f);
//		font.draw(regularBatch, Integer.toString(tank1.getHealth()), 0, 20);
//		font.draw(regularBatch, Integer.toString(tank2.getHealth()), screenWidth - 30, 20);
        tank1.DrawHUD(regularBatch, screenWidth, screenHeight);
        tank2.DrawHUD(regularBatch, screenWidth, screenHeight);
        centerDivider.Draw(regularBatch);
        regularBatch.end();

        /**Mini-Map Draw*/
        Gdx.gl.glViewport(screenWidth / 2 - miniMapWidth / 2, 0, miniMapWidth, miniMapHeight);
        Gdx.gl.glClearColor(1, 0, 0, 1);

        miniCam.update();
        miniMapBatch.setProjectionMatrix(miniCam.combined);
        miniMapBatch.begin();
        for (Vector2 back : backgroundLocations) {
            miniMapBatch.draw(textureMap.get("LunarBackground"), back.x, back.y);
        }
        powerup.MiniDraw(miniMapBatch);
        tank1.MiniDraw(miniMapBatch);
        tank2.MiniDraw(miniMapBatch);
//		for(WallPiece[] wall: walls)
//		{
//			for(WallPiece w : wall)
//			{
//				w.Draw(cameraBatch);
//			}
//		}

        for (WallPiece w : walls) {
            w.Draw(miniMapBatch);
        }

        miniMapBatch.end();
    }

    public void initialiazeWalls() {
        level = Gdx.files.local("Level1Large.txt");
        String levelString = level.readString().replace("\n", "").replace("\r", "");
//		walls = new WallPiece[7][];
        walls = new ArrayList<WallPiece>();

        for (int i = 0, k = 0; i < mapHeight / 50; i++) {
            for (int j = 0; j < mapWidth / 50; j++, k++) {
                if (k < levelString.length()) {
                    if (levelString.toCharArray()[k] == 'u') {
                        walls.add(new WallPiece(Color.WHITE, textureMap.get("UnbreakableBlack"), 50 * j, i * 50, 50, 50));
                    } else if (levelString.toCharArray()[k] == 'b') {
                        walls.add(new DestructableWallPiece(Color.WHITE, textureMap.get("BlackBlock"), 50 * j, i * 50, 50, 50, textureMap.get("BrokenBlackBlock"), 2));
                    }
                }
            }
        }

//		/**Center Divider*/
//		int startingY = mapHeight / 2 - 50 * 6;
//		int startingX = mapWidth / 2 - 25;
//		walls[0] = new WallPiece[12];
//		for(int i = 0; i < walls[0].length; i++)
//		{
//			walls[0][i] = new DestructableWallPiece(Color.WHITE, textureMap.get("BlackBlock"), startingX, startingY + 50 * i, 50, 50, textureMap.get("BrokenBlackBlock"), 2);
//		}
//
//		/**Quarter Divider Left*/
//		startingY = mapHeight / 2 - 25;
//		startingX = mapWidth / 4 - 100;
//		walls[1] = new DestructableWallPiece[4];
//		for(int i = 0; i < walls[1].length; i++)
//		{
//			walls[1][i] = new DestructableWallPiece(Color.WHITE, textureMap.get("BlackBlock"), startingX + 50 * i, startingY, 50, 50, textureMap.get("BrokenBlackBlock"), 2);
//		}
//
//		/**Quarter Divider Right*/
//		startingX = (mapWidth / 4) * 3 - 100;
//		walls[2] = new DestructableWallPiece[4];
//		for(int i = 0; i < walls[2].length; i++)
//		{
//			walls[2][i] = new DestructableWallPiece(Color.WHITE, textureMap.get("BlackBlock"), startingX + 50 * i, startingY, 50, 50, textureMap.get("BrokenBlackBlock"), 2);
//		}
//
//		/**Top Border*/
//		startingX = 0;
//		startingY = mapHeight;
//		walls[3] = new WallPiece[mapWidth / 50];
//		for(int i = 0; i < walls[3].length; i++)
//		{
//			walls[3][i] = new WallPiece(Color.WHITE, textureMap.get("UnbreakableBlack"), startingX + 50 * i, startingY, 50, 50);
//		}
//
//		/**Left Border*/
//		startingX = -50;
//		startingY = -50;
//		walls[4] = new WallPiece[mapHeight / 50 + 2];
//		for(int i = 0; i < walls[4].length; i++)
//		{
//			walls[4][i] = new WallPiece(Color.WHITE, textureMap.get("UnbreakableBlack"), startingX , startingY+ 50 * i, 50, 50);
//		}
//
//
//		/**Bottom Border*/
//		startingX = 0;
//		startingY = -50;
//		walls[5] = new WallPiece[mapWidth / 50];
//		for(int i = 0; i < walls[3].length; i++)
//		{
//			walls[5][i] = new WallPiece(Color.WHITE, textureMap.get("UnbreakableBlack"), startingX + 50 * i, startingY, 50, 50);
//		}
//
//		/**Right Border*/
//		startingX = mapWidth;
//		startingY = -50;
//		walls[6] = new WallPiece[mapHeight / 50 + 2];
//		for(int i = 0; i < walls[4].length; i++)
//		{
//			walls[6][i] = new WallPiece(Color.WHITE, textureMap.get("UnbreakableBlack"), startingX , startingY+ 50 * i, 50, 50);
//		}

    }

    @Override
    public void dispose() {
        cameraBatch.dispose();
        regularBatch.dispose();
    }
}
