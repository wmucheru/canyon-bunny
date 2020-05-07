package com.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.canyonbunny.utils.Constants;

public class GamePreferences {
    public static final String TAG = GamePreferences.class.getName();

    public static final GamePreferences instance = new GamePreferences();

    public boolean sound, music;
    public float volSound, volMusic;
    public int charSkin;
    public boolean showFpsCounter;

    private Preferences prefs;

    private GamePreferences(){
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void load(){
        sound = prefs.getBoolean("sound", true);
        music = prefs.getBoolean("music", true);

        volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f),
                0.0f, 1.0f);
        volMusic = MathUtils.clamp(prefs.getInteger("charSkin", 0), 0, 2);
        charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0), 0, 2);
        showFpsCounter = prefs.getBoolean("showFpsCounter", false);
    }

    public void save(){
        prefs.putBoolean("sound", sound);
        prefs.putBoolean("music", music);
        prefs.putFloat("volSound", volSound);
        prefs.putFloat("volMusic", volMusic);
        prefs.putInteger("charSkin", charSkin);
        prefs.putBoolean("showFpsCounter", showFpsCounter);
        prefs.flush();
    }
}
