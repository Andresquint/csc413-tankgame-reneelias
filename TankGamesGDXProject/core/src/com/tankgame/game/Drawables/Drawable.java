package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;


public class Drawable {

    Texture texture;
    public Texture getTexture(){ return texture; }

    TextureRegion textureRegion;
    public TextureRegion getTextureRegion(){ return textureRegion; }

    Color tint;
    public Color getTint() {
        return tint;
    }

    float x;
    public float getX() {
        return x;
    }

    float y;
    public float getY() {
        return y;
    }

    int width;
    public int getWidth() {
        return width;
    }

    int height;
    public int getHeight() {
        return height;
    }

    float rotation;
    public float getRotation() {
        return rotation;
    }

    float scaleX;
    public float getScaleX(){return scaleX;}
    public void setScaleX(float scaleX)
    {
        this.scaleX = scaleX;
    }

    float scaleY;
    public float getScaleY(){return scaleY;}
    public void setScaleY(float scaleY)
    {
        this.scaleY = scaleY;
    }

    public Drawable(Color tint, Texture texture, int x, int y, int width, int height)
    {
        this.tint = tint;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rotation = 0;
        scaleX = 1f;
        scaleY = 1f;
    }

    public Drawable(Color tint, TextureRegion textureRegion, int x, int y, int width, int height)
    {
        this.tint = tint;
        this.textureRegion = textureRegion;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rotation = 0;
        scaleX = 1f;
        scaleY = 1f;
    }

    public Drawable(Color tint, int x, int y, int width, int height)
    {
        this.tint = tint;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rotation = 0;
        scaleX = 1f;
        scaleY = 1f;
    }

    public void Draw(SpriteBatch batch)
    {
        batch.setColor(tint);
        if(texture != null)
        {
            batch.draw(new TextureRegion(texture), x, y, width/2, height/2, width, height, scaleX, scaleY, rotation);
        }
        else
        {
            batch.draw(textureRegion, x, y,  width/2, height/2, width, height, scaleX, scaleY, rotation);
        }
    }

}
