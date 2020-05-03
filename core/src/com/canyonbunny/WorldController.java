package com.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.canyonbunny.game.objects.Level;
import com.canyonbunny.utils.CameraHelper;
import com.canyonbunny.utils.Constants;

public class WorldController {
    public CameraHelper cameraHelper;

    public Level level;
    public int lives;
    public int score;

    public WorldController() {
        init();
    }

    private void init(){
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;

        initLevel();
    }

    private void initLevel () {
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    public void update(float deltaTime){
        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime){
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        if (Gdx.input.isKeyJustPressed(Keys.UP)){
            cameraHelper.setZoom(2);
        }

        if (Gdx.input.isKeyJustPressed(Keys.DOWN)){
            cameraHelper.setZoom(1);
        }
    }
}
