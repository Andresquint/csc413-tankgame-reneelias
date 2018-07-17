package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class WallPiece extends GameObject {

    public WallPiece(Color tint, Texture texture, int x, int y, int width, int height)
    {
        super(tint, texture, x, y, width, height);
        hitbox = new Rectangle(x, y, width, height);
    }

    public WallPiece(Color tint, TextureRegion textureRegion, int x, int y, int width, int height)
    {
        super(tint, textureRegion, x, y, width, height);
        hitbox = new Rectangle(x, y, width, height);
    }

    public WallPiece(Color tint, int x, int y, int width, int height)
    {
        super(tint, x, y, width, height);
        hitbox = new Rectangle(x, y, width, height);
    }


}
