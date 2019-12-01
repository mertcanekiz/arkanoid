package com.arkanoid.game.desktop;

import com.arkanoid.game.ArkanoidGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import static com.arkanoid.game.ArkanoidGame.SCALE;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ArkanoidGame.SCREEN_WIDTH * ArkanoidGame.SCALE;
		config.height = ArkanoidGame.SCREEN_HEIGHT * ArkanoidGame.SCALE;
//		config.resizable = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		new LwjglApplication(ArkanoidGame.getInstance(), config);
	}
}
