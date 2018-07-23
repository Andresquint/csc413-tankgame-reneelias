package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Powerup extends GameObject {

    boolean active;
    public boolean getActive(){return active;}
    public void deactivate(){active = false;}
    float timeElapsed;
    float scaleIncrement;

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
}
