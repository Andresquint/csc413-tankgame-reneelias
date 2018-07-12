package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Drawable {

    Rectangle hitbox;
    public Rectangle getHitbox()
    {
        return hitbox.set(x, y, width, height);
    }

    public GameObject(Color tint, Texture texture, int x, int y, int width, int height)
    {
        super(tint, texture, x, y, width, height);
        hitbox = new Rectangle(x, y, width, height);
    }

    public GameObject(Color tint, TextureRegion textureRegion, int x, int y, int width, int height)
    {
        super(tint, textureRegion, x, y, width, height);
        hitbox = new Rectangle(x, y, width, height);
    }

    public boolean outOfBounds(int screenWidth, int screenHeight)
    {
        return (x + width > screenWidth || x < 0 || y + height > screenHeight || y < 0);
    }
}
