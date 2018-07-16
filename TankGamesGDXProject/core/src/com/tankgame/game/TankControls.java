package com.tankgame.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class TankControls {

    HashMap<String, Integer> keysMap;
    HashMap<String, Boolean> pressedMap;

    public TankControls(boolean p1)
    {
        keysMap = new HashMap<String, Integer>();
        if(p1)
        {
            keysMap.put("Right", Input.Keys.RIGHT);
            keysMap.put("Left", Input.Keys.LEFT);
            keysMap.put("Down", Input.Keys.DOWN);
            keysMap.put("Up", Input.Keys.UP);
            keysMap.put("Shoot",Input.Keys.CONTROL_RIGHT);
        }
        else
        {
            keysMap.put("Right", Input.Keys.D);
            keysMap.put("Left", Input.Keys.A);
            keysMap.put("Down", Input.Keys.S);
            keysMap.put("Up", Input.Keys.W);
            keysMap.put("Shoot",Input.Keys.SPACE);
        }

        pressedMap = new HashMap<String, Boolean>();
        pressedMap.put("Right", false);
        pressedMap.put("Left", false);
        pressedMap.put("Down", false);
        pressedMap.put("Up", false);
        pressedMap.put("Shoot", false);
    }

    public HashMap<String, Boolean> UpdateInput(Input input) {
        pressedMap.put("Right", false);
        pressedMap.put("Left", false);
        pressedMap.put("Down", false);
        pressedMap.put("Up", false);
        pressedMap.put("Shoot", false);

        if(input.isKeyPressed(keysMap.get("Right")))
        {
            pressedMap.put("Right", true);
        }
        if(input.isKeyPressed(keysMap.get("Left")))
        {
            pressedMap.put("Left", true);
        }
        if(input.isKeyPressed(keysMap.get("Down")))
        {
            pressedMap.put("Down", true);
        }
        if(input.isKeyPressed(keysMap.get("Up")))
        {
            pressedMap.put("Up", true);
        }
        if(input.isKeyJustPressed(keysMap.get("Shoot")))
        {
            pressedMap.put("Shoot", true);
        }


        return pressedMap;
    }
}
