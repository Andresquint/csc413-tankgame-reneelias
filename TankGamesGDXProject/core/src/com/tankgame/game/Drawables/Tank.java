package com.tankgame.game.Drawables;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tankgame.game.TankControls;

import java.util.ArrayList;
import java.util.HashMap;

public class Tank extends GameObject {

    TankControls tankControls;
    int speed, currentBulletIndex;
    float rotationSpeed;
    Bullet[] bullets;
    int healthPoints;
    public int GetHealth()
    {
        return healthPoints;
    }

    public Tank(Color tint, HashMap<String, TextureRegion> textureMap, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed)
    {
        super(tint, x, y, width, height);
        if(p1)
        {
            textureRegion = textureMap.get("Tank1");
        }
        else
        {
            textureRegion = textureMap.get("Tank2");
        }
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        bullets = new Bullet[50];
        for (int i = 0; i < bullets.length; i++)
        {
            bullets[i] = new Bullet(Color.WHITE, textureMap.get("Bullet"), 0, 0, 15, 5, 15, 0);
        }
        currentBulletIndex = 0;
        healthPoints = 20;
    }

    public Tank(Color tint, Texture texture, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed)
    {
        super(tint, texture, x, y, width, height);
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        currentBulletIndex = 0;
    }

    public Tank(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed)
    {
        super(tint, textureRegion, x, y, width, height);
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public void Update(Input input, int screenWidth, int screenHeight, WallPiece[][] walls, Tank enemy)
    {
        HashMap<String, Boolean> pressedMap = tankControls.UpdateInput(input);
        UpdateTankPosition(pressedMap, screenWidth, screenHeight, walls, enemy);
        ManageBullets(pressedMap, screenWidth, screenHeight, walls, enemy);
    }

    private void UpdateTankPosition(HashMap<String, Boolean> currentInput, int screenWidth, int screenHeight, WallPiece[][] walls, Tank enemy)
    {
        if(currentInput.get("Left"))
        {
            rotation += rotationSpeed;
        }
        if(currentInput.get("Right"))
        {
            rotation -= rotationSpeed;
        }
        double currentXSpeed = Math.cos(Math.toRadians(rotation)) * speed;
        double currentYSpeed = Math.sin(Math.toRadians(rotation)) * speed;
        if(currentInput.get("Up"))
        {
            x += currentXSpeed;
            y += currentYSpeed;
            if(outOfBounds(screenWidth, screenHeight) || CheckWallCollision(walls) || getHitbox().overlaps(enemy.getHitbox()))
            {
                x -= currentXSpeed;
                y -= currentYSpeed;
            }
        }
        if(currentInput.get("Down"))
        {
            x -= currentXSpeed;
            y -= currentYSpeed;
            if(outOfBounds(screenWidth, screenHeight) || CheckWallCollision(walls) || getHitbox().overlaps(enemy.getHitbox()))
            {
                x += currentXSpeed;
                y += currentYSpeed;
            }
        }
    }

    private void ManageBullets(HashMap<String, Boolean> pressedMap, int screenWidth, int screenHeight, WallPiece[][] walls, Tank enemy)
    {
        for(int i = 0; i < bullets.length; i++)
        {
            if(bullets[i].isActive())
            {
                bullets[i].Update(screenWidth, screenHeight, walls, enemy);
            }
        }
        if(pressedMap.get("Shoot"))
        {
            while(bullets[currentBulletIndex].isActive())
            {
                currentBulletIndex++;
                if(currentBulletIndex == bullets.length)
                {
                    currentBulletIndex = 0;
                }
            }
            bullets[currentBulletIndex].activate((int)x + width / 2, (int)y + height / 2, rotation);
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
                    return true;
                }
            }
        }
        return false;
    }

    public void LoseHealth()
    {
        healthPoints--;
    }

    @Override
    public void Draw(SpriteBatch batch) {
        for(int i = 0; i < bullets.length; i++)
        {
            if(bullets[i].isActive())
            {
                bullets[i].Draw(batch);
            }
        }
        super.Draw(batch);
    }
}
