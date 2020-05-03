package com.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CanyonBunny extends ApplicationAdapter {
	public static final String TAG = CanyonBunny.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean paused;
	
	@Override
	public void create () {
		// TODO: Remove on publish
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);

		paused = false;
	}

	@Override
	public void render () {
		// Update only if game is not paused
		if(!paused){
			worldController.update(Gdx.graphics.getDeltaTime());
		}

		Gdx.gl.glClearColor(0, 50, 50, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}

	@Override
	public void dispose () {
		worldRenderer.dispose();
	}
}
