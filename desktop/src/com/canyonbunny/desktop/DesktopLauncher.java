package com.canyonbunny.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.canyonbunny.CanyonBunny;

public class DesktopLauncher {

	public static void main (String[] arg) {
		boolean rebuildAtlas = true;

		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.debug = false;

			// Game assets
			TexturePacker.process(settings,
					"desktop/assets-raw/images",
					"android/assets/images",
			"canyon_bunny.atlas");

			// UI assets
			TexturePacker.process(settings,
					"desktop/assets-raw/images-ui",
					"android/assets/images",
					"canyon_bunny_ui.atlas");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Canyon Bunny";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new CanyonBunny(), config);
	}
}
