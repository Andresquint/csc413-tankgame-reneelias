package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tankgame.game.TankControls;

public class Bullet extends GameObject {
    Vector2 velocity;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void activate(int x, int y, float rotation)
    {
        active = true;
        this.x = x;
        this.y = y;
        velocity.x = (float)(velocity.x * Math.cos(Math.toRadians(rotation)));
        velocity.y = (float)(velocity.y * Math.cos(Math.toRadians(rotation)));
    }


    public Bullet(Color tint, Texture texture, int x, int y, int width, int height, int speed, float rotation)
    {
        super(tint, texture, x, y, width, height);
        velocity = new Vector2(speed, 0);
        velocity.x = (float)(velocity.x * Math.cos(Math.toRadians(rotation)));
        velocity.y = (float)(velocity.y * Math.cos(Math.toRadians(rotation)));
        active = false;

    }

    public Bullet(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, int speed, float rotation)
    {
        super(tint, textureRegion, x, y, width, height);
        velocity = new Vector2(speed, 0);
        velocity.x = (float)(velocity.x * Math.cos(Math.toRadians(rotation)));
        velocity.y = (float)(velocity.y * Math.cos(Math.toRadians(rotation)));
        active = false;
    }

    public void Update(int screenWidth, int screenHeight)
    {
        x += velocity.x;
        y += velocity.y;
        if(outOfBounds(screenWidth, screenHeight))
        {
            active = false;
        }
    }

    @Override
    public void Draw(SpriteBatch batch) {
        super.Draw(batch);
    }
}
