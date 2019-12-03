package com.arkanoid.game.desktop;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (Arkanoid.SCREEN_WIDTH * Arkanoid.SCALE);
		config.height = (int) (Arkanoid.SCREEN_HEIGHT * Arkanoid.SCALE);
		config.resizable = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		new LwjglApplication(Arkanoid.getInstance(), config);
	}
}
