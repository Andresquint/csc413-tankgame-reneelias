package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DestructableWallPiece extends WallPiece {

    int fullHealth;

    int healthPoints;
    public int getHealth()
    {
        return healthPoints;
    }

    public void damageWall(int damagePoints)
    {
        healthPoints -= damagePoints;
        if(healthPoints <= fullHealth / 2 && textureRegion != brokenTexture) {
            textureRegion = brokenTexture;
        }
    }
    TextureRegion brokenTexture;

    public DestructableWallPiece(Color tint, Texture texture, int x, int y, int width, int height, int fullHealth) {
        super(tint, texture, x, y, width, height);
        destructable = true;
        this.fullHealth = fullHealth;
        healthPoints = fullHealth;
    }

    public DestructableWallPiece(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, TextureRegion brokenTexture, int fullHealth) {
        super(tint, textureRegion, x, y, width, height);
        this.brokenTexture = brokenTexture;
        destructable = true;
        this.fullHealth = fullHealth;
        healthPoints = fullHealth;
    }

    public DestructableWallPiece(Color tint, int x, int y, int width, int height, int fullHealth) {
        super(tint, x, y, width, height);
        destructable = true;
        this.fullHealth = fullHealth;
        healthPoints = fullHealth;
    }

    @Override
    public void Draw(SpriteBatch batch) {
        if(healthPoints > 0)
        {
            super.Draw(batch);
        }
    }
}
