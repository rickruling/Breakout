package com.vish.gdx.breakout.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vish.gdx.breakout.BreakoutGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 614;
		config.height = 1024;
		new LwjglApplication(new BreakoutGame(), config);
	}
}
