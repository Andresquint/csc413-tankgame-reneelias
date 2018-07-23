package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tankgame.game.TankControls;

public class Bullet extends GameObject {
    Vector2 velocity;
    int speed;
    private boolean active;
    int damagePoints;

    public boolean isActive() {
        return active;
    }

    public void activate(int x, int y, float rotation)
    {
        active = true;
        this.x = x;
        this.y = y;
        velocity.x = (float)(speed * Math.cos(Math.toRadians(rotation)));
        velocity.y = (float)(speed * Math.sin(Math.toRadians(rotation)));
        this.rotation = rotation;
    }

    public Bullet(Color tint, Texture texture, int x, int y, int width, int height, int speed, float rotation)
    {
        super(tint, texture, x, y, width, height);
        this.speed = speed;
        velocity = new Vector2(speed, 0);
        this.rotation = rotation;
        active = false;
        damagePoints = 1;
    }

    public Bullet(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, int speed, float rotation)
    {
        super(tint, textureRegion, x, y, width, height);
        this.speed = speed;
        velocity = new Vector2(speed, 0);
        this.rotation = rotation;
        active = false;
        damagePoints = 1;
    }

    public void Update(int screenWidth, int screenHeight, WallPiece[][] walls, Tank enemy)
    {
        x += velocity.x;
        y += velocity.y;
        boolean tankHit = getHitbox().overlaps(enemy.getHitbox()) && enemy.getLives() > 0;
        if(outOfBounds(screenWidth, screenHeight) || CheckWallCollision(walls) || tankHit)
        {
            active = false;
            if(tankHit)
            {
                enemy.LoseHealth(damagePoints);
            }
        }
    }

    private boolean CheckWallCollision(WallPiece[][] walls)
    {
        for(WallPiece[] wall : walls)
        {
            for(WallPiece w : wall)
            {
                if(getHitbox().overlaps(w.getHitbox()))
                {
                    if((w.isDestructable() && ((DestructableWallPiece)w).getHealth() > 0))
                    {
                        ((DestructableWallPiece)w).damageWall(damagePoints);
                        return true;
                    }
                    else if(!w.isDestructable())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void Draw(SpriteBatch batch) {
        super.Draw(batch);
    }
}
