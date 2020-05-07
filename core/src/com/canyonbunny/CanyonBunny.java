package com.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.canyonbunny.game.Assets;
import com.canyonbunny.screens.MenuScreen;

public class CanyonBunny extends Game {
	public static final String TAG = CanyonBunny.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean paused;
	
	@Override
	public void create () {
		// TODO: Remove on publish
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Load assets
		Assets.instance.init(new AssetManager());

		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}
