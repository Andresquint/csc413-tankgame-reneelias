package com.tankgame.game.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Rocket extends Bullet {

    public Rocket(Color tint, Texture texture, int x, int y, int width, int height, int speed, float rotation) {
        super(tint, texture, x, y, width, height, speed, rotation);
        damagePoints = 3;
    }

    public Rocket(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, int speed, float rotation) {
        super(tint, textureRegion, x, y, width, height, speed, rotation);
        damagePoints = 3;

    }
}
