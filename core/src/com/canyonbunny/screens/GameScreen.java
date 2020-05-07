package com.canyonbunny.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.canyonbunny.WorldController;
import com.canyonbunny.WorldRenderer;
import com.canyonbunny.game.GamePreferences;

class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {

        // Do not update world when paused
        if(!paused){
            worldController.update(deltaTime);
        }

        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed /
                255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        GamePreferences.instance.load();
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    @Override
    public void pause() {
         paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android
        paused = false;
    }
}
