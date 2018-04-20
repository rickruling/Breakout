package com.vish.gdx.breakout.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.vish.gdx.breakout.GameStage;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.utils.Constants;

public class GameScreen implements Screen, Constants {

	Game thisGame;
	GameStage stage;
	Stage pauseStage;
	public int step = 1;
	ImageActor backGroundActor;
//	Group screenShotHolder;

	public GameScreen(Game thisGame) {
		Gdx.input.setCatchBackKey(true);
		// check if game data is present
		stage = Assets.INSTANCE.getPreferences().loadGame();
		if (stage == null) {
			System.out.println("Creating new GameStage.");
			stage = new GameStage(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
		}

		// else {
		// System.out.println("Creating new bg");
		//
		// stage = new GameStage(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
		//
		// }
		this.thisGame = thisGame;
		stage.gameState = State.RUN;
		initialisePauseStage();
	}

	private void initialisePauseStage() {

		pauseStage = new Stage(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
		final Skin skinLibgdx = Assets.INSTANCE.getSkinLibgdx();

		// Play button
		ImageButton playButton = new ImageButton(skinLibgdx, "playButton");
		playButton.setColor(Color.WHITE);
		playButton.setPosition((GAME_WIDTH - playButton.getWidth()) / 2, (GAME_HEIGHT - playButton.getHeight()) / 2);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.resetInputProcessor();
				stage.gameState = State.RUN;
				step = 1;
			}
		});
		pauseStage.addActor(playButton);

		// Sound
		final ImageActor soundImage = new ImageActor(Assets.INSTANCE.getTexture("sound_on"));

		soundImage.setPosition((GAME_WIDTH - soundImage.getWidth() - 50), 50);
		if (Assets.INSTANCE.getPreferences().sound) {
			soundImage.setDrawable(skinLibgdx, "sound_on");
		} else {
			soundImage.setDrawable(skinLibgdx, "sound_off");
		}
		soundImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (Assets.INSTANCE.getPreferences().sound) {
					soundImage.setDrawable(skinLibgdx, "sound_off");
					Assets.INSTANCE.getPreferences().sound = false;
				} else {
					soundImage.setDrawable(skinLibgdx, "sound_on");
					Assets.INSTANCE.getPreferences().sound = true;
				}
				Assets.INSTANCE.getPreferences().save();
			}
		});
		pauseStage.addActor(soundImage);

		// Home
		final ImageActor homeImage = new ImageActor(Assets.INSTANCE.getTexture("home"));
		homeImage.setPosition(50, 50);
		homeImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.graphics.setContinuousRendering(true);
				Assets.INSTANCE.getPreferences().deleteGame();
				thisGame.setScreen(new MenuScreen(thisGame));
			}
		});
		// pauseStage.
		pauseStage.addActor(homeImage);

	}

	@Override
	public void show() {
		stage.addActorsToStage();
	}

	@Override
	public void render(float delta) {
		switch (stage.gameState) {
		case RUN:

			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
			break;
		case PAUSE:

			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();

			Gdx.input.setInputProcessor(pauseStage);
			pauseStage.act(Gdx.graphics.getDeltaTime());
			pauseStage.draw();

			Gdx.graphics.setContinuousRendering(false);
			break;
		default:
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
		stage.resetInputProcessor();
		stage.gameState = State.RUN;
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
		stage.gameState = State.PAUSE;
		stage.pauseCover.setVisible(true);
//		Assets.INSTANCE.getPreferences().saveGame(stage);

	}
}
