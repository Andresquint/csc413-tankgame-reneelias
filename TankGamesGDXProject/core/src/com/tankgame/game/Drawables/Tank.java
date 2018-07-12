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
    int speed;
    float rotationSpeed;
    Bullet[] bullets;

    public Tank(Color tint, Texture texture, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed)
    {
        super(tint, texture, x, y, width, height);
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        bullets = new Bullet[50];
//        for (int i = 0; i < bullets.length; i++)
//        {
//            bullets[i]
//        }
    }

    public Tank(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed)
    {
        super(tint, textureRegion, x, y, width, height);
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public void Update(Input input, int screenWidth, int screenHeight)
    {
        UpdateTankPosition(tankControls.UpdateInput(input), screenWidth, screenHeight);

    }

    public void UpdateTankPosition(HashMap<String, Boolean> currentInput, int screenWidth, int screenHeight)
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
            if(outOfBounds(screenWidth, screenHeight))
            {
                x -= currentXSpeed;
                y -= currentYSpeed;
            }
        }
        if(currentInput.get("Down"))
        {
            x -= currentXSpeed;
            y -= currentYSpeed;
            if(outOfBounds(screenWidth, screenHeight))
            {
                x += currentXSpeed;
                y += currentYSpeed;
            }
        }
    }




    @Override
    public void Draw(SpriteBatch batch) {
        super.Draw(batch);
    }
}
