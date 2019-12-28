package com.tankgame.game.Drawables;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tankgame.game.TankControls;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Tank extends GameObject {

    TankControls tankControls;
    int speed, currentBulletIndex, fullHealth, lives, currentRocketIndex, currentlyAddedRockets;

    public int getLives() {
        return lives;
    }

    float rotationSpeed;
    Bullet[] bullets;
    Bullet[] standardBullets, rockets;
    int healthPoints;

    public int getHealth() {
        return healthPoints;
    }

    Animation<TextureRegion> explosionAnimation;
    float explosionTimer, bulletTimer;
    boolean p1;
    TextureRegion healthBarEmptyTexture, healthBarFillerTexture;
    boolean currentlyPoweredUp;
    BitmapFont font;
    Sound explosionSound, bulletSound;

    public Tank(Color tint, HashMap<String, TextureRegion> textureMap, Animation<TextureRegion> explosionAnimation, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed, int fullHealth, int lives, Sound explosionSound, Sound bulletSound) {
        super(tint, x, y, width, height);
        this.p1 = p1;
        if (p1) {
            textureRegion = textureMap.get("Tank1");
            rotation = 270;
        } else {
            textureRegion = textureMap.get("Tank2");
            rotation = 90;
        }
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        standardBullets = new Bullet[50];
        for (int i = 0; i < standardBullets.length; i++) {
            standardBullets[i] = new Bullet(Color.WHITE, textureMap.get("Bullet"), 0, 0, 20, 7, 15, 0);
        }
        bullets = standardBullets;
        rockets = new Rocket[20];
        currentRocketIndex = 0;
        currentlyAddedRockets = 0;
        for (int i = 0; i < rockets.length; i++) {
            rockets[i] = new Rocket(Color.WHITE, textureMap.get("Rocket"), 0, 0, 30, 11, 15, 0);
        }
        currentBulletIndex = 0;
        healthPoints = fullHealth;
        this.explosionAnimation = explosionAnimation;
        bulletTimer = 0;
        healthBarEmptyTexture = textureMap.get("HealthBarEmpty");
        healthBarFillerTexture = textureMap.get("HealthBarFiller");
        this.fullHealth = fullHealth;
        this.lives = lives;
        currentlyPoweredUp = false;
        font = new BitmapFont();
        this.explosionSound = explosionSound;
        this.bulletSound = bulletSound;
    }

    public Tank(Color tint, Texture texture, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed) {
        super(tint, texture, x, y, width, height);
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        currentBulletIndex = 0;
    }

    public Tank(Color tint, TextureRegion textureRegion, int x, int y, int width, int height, boolean p1, int speed, float rotationSpeed) {
        super(tint, textureRegion, x, y, width, height);
        tankControls = new TankControls(p1);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public void Update(Input input, int screenWidth, int screenHeight, ArrayList<WallPiece> walls, Tank enemy, float deltaTime, Powerup powerup) {
        HashMap<String, Boolean> pressedMap = tankControls.UpdateInput(input);
        if (healthPoints > 0 && lives > 0) {
            UpdateTankPosition(pressedMap, screenWidth, screenHeight, walls, enemy);
        } else {
            explosionTimer += deltaTime;
            textureRegion = explosionAnimation.getKeyFrame(explosionTimer);
        }

        bulletTimer += deltaTime;
        ManageBullets(pressedMap, screenWidth, screenHeight, walls, enemy);

        if (powerup.getActive() && powerup.getHitbox().overlaps(getHitbox())) {
            if (powerup.getType().equals("Rocket")) {
                currentlyAddedRockets = 0;
                currentlyPoweredUp = true;
            } else {
                healthPoints += 5;
                if (healthPoints > fullHealth) {
                    healthPoints = fullHealth;
                }
            }
            powerup.deactivate();
        }
    }

    private void UpdateTankPosition(HashMap<String, Boolean> currentInput, int screenWidth, int screenHeight, ArrayList<WallPiece> walls, Tank enemy) {
        if (currentInput.get("Left")) {
            rotation += rotationSpeed;
        }
        if (currentInput.get("Right")) {
            rotation -= rotationSpeed;
        }
        double currentXSpeed = Math.cos(Math.toRadians(rotation)) * speed;
        double currentYSpeed = Math.sin(Math.toRadians(rotation)) * speed;
        if (currentInput.get("Up")) {
            x += currentXSpeed;
            y += currentYSpeed;
            if (outOfBounds(screenWidth, screenHeight) || CheckWallCollision(walls) || (getHitbox().overlaps(enemy.getHitbox()) && enemy.getLives() > 0)) {
                x -= currentXSpeed;
                y -= currentYSpeed;
            }
        }
        if (currentInput.get("Down")) {
            x -= currentXSpeed;
            y -= currentYSpeed;
            if (outOfBounds(screenWidth, screenHeight) || CheckWallCollision(walls) || (getHitbox().overlaps(enemy.getHitbox()) && enemy.getLives() > 0)) {
                x += currentXSpeed;
                y += currentYSpeed;
            }
        }
    }

    private void ManageBullets(HashMap<String, Boolean> pressedMap, int screenWidth, int screenHeight, ArrayList<WallPiece> walls, Tank enemy) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                bullets[i].Update(screenWidth, screenHeight, walls, enemy);
            }
        }
        for (int i = 0; i < rockets.length; i++) {
            if (rockets[i].isActive()) {
                rockets[i].Update(screenWidth, screenHeight, walls, enemy);
            }
        }
        if (pressedMap.get("Shoot") && lives > 0 && bulletTimer >= .2f) {
            if (currentlyPoweredUp) {
                while (rockets[currentRocketIndex].isActive()) {
                    currentRocketIndex++;
                    if (currentRocketIndex >= rockets.length) {
                        currentRocketIndex = 0;
                    }
                }
                currentlyAddedRockets++;
                rockets[currentRocketIndex].activate((int) x + width / 2, (int) y + height / 2, rotation);
                bulletSound.play(.25f);
                bulletTimer = 0;
                if (currentlyAddedRockets == 5) {
                    currentlyPoweredUp = false;
                }
            } else {
                while (bullets[currentBulletIndex].isActive()) {
                    currentBulletIndex++;
                    if (currentBulletIndex == bullets.length) {
                        currentBulletIndex = 0;
                    }
                }
                bullets[currentBulletIndex].activate((int) x + width / 2, (int) y + height / 2, rotation);
                bulletSound.play(.25f);
                bulletTimer = 0;
            }

        }
    }

    private boolean CheckWallCollision(ArrayList<WallPiece> walls) {
        for (WallPiece w : walls) {
            if (getHitbox().overlaps(w.getHitbox())) {
                if (!w.isDestructable() || (w.isDestructable() && ((DestructableWallPiece) w).getHealth() > 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void LoseHealth(int damagePoints) {
        healthPoints -= damagePoints;

        if (healthPoints <= 0) {
            lives--;
            currentlyPoweredUp = false;
            if (lives > 0) {
                healthPoints = fullHealth;
            } else {
                healthPoints = 0;
                explosionSound.play();
            }
        }
    }

    @Override
    public void Draw(SpriteBatch batch) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                bullets[i].Draw(batch);
            }
        }
        for (int i = 0; i < rockets.length; i++) {
            if (rockets[i].isActive()) {
                rockets[i].Draw(batch);
            }
        }
        if (lives > 0 || !explosionAnimation.isAnimationFinished(explosionTimer)) {
            super.Draw(batch);
        }
    }

    public void MiniDraw(SpriteBatch batch) {
        if (p1) {
            tint = Color.RED;
        } else {
            tint = Color.BLUE;
        }
        float multiplier = 1.75f;
        width *= multiplier;
        height *= multiplier;
        Draw(batch);
        tint = Color.WHITE;
        width /= multiplier;
        height /= multiplier;
    }

    public void DrawHUD(SpriteBatch batch, int screenWidth, int screenHeight) {
        float currentHealthPercentage = (float) healthPoints / fullHealth;
        int healthBarWidth = 100, healthBarHeight = 15;
        if (currentHealthPercentage > .5f) {
            batch.setColor(Color.GREEN);
        } else if (currentHealthPercentage > .25f) {
            batch.setColor(Color.YELLOW);
        } else {
            batch.setColor(Color.RED);
        }
        if (p1) {
            batch.draw(healthBarFillerTexture, 10, screenHeight - healthBarHeight - 16, healthBarWidth * currentHealthPercentage, healthBarHeight + 1);
            batch.setColor(Color.WHITE);
            batch.draw(healthBarEmptyTexture, 10, screenHeight - healthBarHeight - 15, healthBarWidth, healthBarHeight);
            int currX = 10;
            for (int i = 0; i < lives; i++) {
                batch.draw(textureRegion, currX, screenHeight - healthBarHeight - height / 4 - 25, 0, 0, width, height, .25f, .25f, 0f);
                currX += 5 + width / 4f;
            }
            if (currentlyPoweredUp) {
                batch.draw(rockets[0].getTextureRegion(), 20 + healthBarWidth, screenHeight - healthBarHeight - 15, rockets[0].getWidth() * rockets[0].getHeight() / healthBarHeight, healthBarHeight);
                font.draw(batch, String.format("x %d", 5 - currentlyAddedRockets), 25 + healthBarWidth + rockets[0].getWidth() * rockets[0].getHeight() / healthBarHeight, screenHeight - healthBarHeight - 1);
            }
        } else {
            batch.draw(healthBarFillerTexture, screenWidth - healthBarWidth - 10, screenHeight - healthBarHeight - 16, healthBarWidth * currentHealthPercentage, healthBarHeight + 1);
            batch.setColor(Color.WHITE);
            batch.draw(healthBarEmptyTexture, screenWidth - healthBarWidth - 10, screenHeight - healthBarHeight - 15, healthBarWidth, healthBarHeight);
            int currX = screenWidth - 5 * lives - (width / 4) * lives - 10;
            for (int i = 0; i < lives; i++) {
                batch.draw(textureRegion, currX, screenHeight - healthBarHeight - height / 4 - 25, 0, 0, width, height, .25f, .25f, 0f);
                currX += 5 + width / 4f;
            }
            if (currentlyPoweredUp) {
                batch.draw(rockets[0].getTextureRegion(), screenWidth - healthBarWidth - 20 - (rockets[0].getWidth() * rockets[0].getHeight() / healthBarHeight) * 2, screenHeight - healthBarHeight - 15, rockets[0].getWidth() * rockets[0].getHeight() / healthBarHeight, healthBarHeight);
                font.draw(batch, String.format("x %d", 5 - currentlyAddedRockets), screenWidth - healthBarWidth - 15 - (rockets[0].getWidth() * rockets[0].getHeight() / healthBarHeight) * 1, screenHeight - healthBarHeight - 1);
            }
        }
    }
}
