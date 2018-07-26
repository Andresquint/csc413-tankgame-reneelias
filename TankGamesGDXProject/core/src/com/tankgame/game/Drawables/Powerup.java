package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Random;

public class Powerup extends GameObject {

    boolean active;
    public boolean getActive(){return active;}
    public void deactivate(){active = false;}
    float timeElapsed;
    float scaleIncrement;
    Random random;
    TextureRegion lifePowerupTexture;
    TextureRegion rocketPowerupTexture;
    float elapsedMiniTime;
    boolean toDrawMini;
    String type;
    public String getType()
    {
        return type;
    }

    public Powerup(Color tint, Texture texture, int x, int y, int width, int height) {
        super(tint, texture, x, y, width, height);
        active = false;
        timeElapsed = 0;
        scaleIncrement = -.0025f;
    }

    public Powerup(Color tint, TextureRegion textureRegion, int x, int y, int width, int height) {
        super(tint, textureRegion, x, y, width, height);
        active = false;
        timeElapsed = 0;
        scaleIncrement = -.0025f;
    }

    public Powerup(Color tint, HashMap<String, TextureRegion> textureMap, int x, int y, int width, int height) {
        super(tint, x, y, width, height);
        active = false;
        timeElapsed = 0;
        scaleIncrement = -.0025f;
        rocketPowerupTexture = textureMap.get("RocketPickup");
        lifePowerupTexture = textureMap.get("LifePowerup");
        random = new Random();
        elapsedMiniTime = 0;
        toDrawMini = true;
        type = "Rocket";
    }

    public Powerup(Color tint, int x, int y, int width, int height) {
        super(tint, x, y, width, height);
        active = false;
        timeElapsed = 0;
        scaleIncrement = -.0025f;
    }

    public void Update(float deltaTime, boolean tankUsingPowerup) {
        if(!active && timeElapsed > 5)
        {
            timeElapsed = 0;
            active = true;
            scaleX = 1;
            scaleY = 1;
            scaleIncrement = -Math.abs(scaleIncrement);
            if(random.nextInt() % 2 == 0)
            {
                textureRegion = rocketPowerupTexture;
                type = "Rocket";
            }
            else
            {
                textureRegion = lifePowerupTexture;
                type = "Life";
            }
            if(random.nextInt() % 2 == 0)
            {
                y = 200;
            }
            else
            {
                y = 1200;
            }
            elapsedMiniTime += deltaTime;
        }
        else
        {
            timeElapsed += deltaTime;
        }
    }

    @Override
    public void Draw(SpriteBatch batch) {
        if(active)
        {
            scaleX += scaleIncrement;
            scaleY += scaleIncrement;
            if(scaleX < .65f || scaleX >= 1f)
            {
                scaleIncrement *= -1;
            }
            super.Draw(batch);
        }
    }

    public void MiniDraw(SpriteBatch batch)
    {
        if(active)
        {
            float oldScaleX = scaleX;
            float oldScaleY = scaleY;
            scaleX = 2f;
            scaleY = 2f;
            super.Draw(batch);
            scaleX = oldScaleX;
            scaleY = oldScaleY;
        }
    }
}
