package com.vish.gdx.breakout;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.screens.GameScreen;
import com.vish.gdx.breakout.screens.MenuScreen;

public class BreakoutGame extends Game {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("Game", "App created");

		// System.out.println("OS name " + System.getProperty("os.name"));
		// System.out.println("OS version " + System.getProperty("os.version"));
		//// System.out.println("LWJGL version " + org.lwjgl.Sys.getVersion());
		// System.out.println("OpenGL version " +
		// Gdx.graphics.getGLVersion().getDebugVersionString());
		// System.out.println("OpenGL version2 " +
		// Gdx.graphics.getGLVersion().getMajorVersion());
		// System.out.println("OpenGL version3 " +
		// Gdx.graphics.getGLVersion().getMinorVersion());
		// System.out.println("OpenGL version4 " +
		// Gdx.graphics.getGLVersion().getRendererString());
		// System.out.println("OpenGL type " + Gdx.graphics.getType());
		//// System.out.println("fps " + Gdx.graphics.getFramesPerSecond());
		// GLVersion a = Gdx.graphics.getGLVersion();

		Assets.INSTANCE.init(new AssetManager());
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		Assets.INSTANCE.dispose();
	}
}
