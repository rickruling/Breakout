package com.vish.gdx.breakout.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.vish.gdx.breakout.GameStage;
import com.vish.gdx.breakout.actors.BlockGroup;
import com.vish.gdx.breakout.utils.Constants;

public class GamePreferences {
	public static final GamePreferences instance = new GamePreferences();
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public int charSkin;
	public boolean showFpsCounter;
	private Preferences prefs;
	public Integer maxScore;

	public void load() {
		System.out.println("Loading preferences.");
		prefs = Gdx.app.getPreferences(Constants.MAX_SCORE_PREFERENCE);
		sound = prefs.getBoolean("sound", true);
		maxScore = prefs.getInteger(Constants.MAX_SCORE_VALUE, 0);
		System.out.println("sound : " + sound + " maxScore : " + maxScore);
	}

	public void save() {
		prefs.putBoolean("sound", sound);
		prefs.putInteger(Constants.MAX_SCORE_VALUE, maxScore);
		prefs.flush();
		System.out.println("flushed...");
		System.out.println("sound : " + sound + " maxScore : " + maxScore);

	}

	public void saveGame(GameStage blockGroup) {
		System.out.println("Saving game");
		try {
			new Json(OutputType.json).toJson(blockGroup, GameStage.class, Gdx.files.local(Constants.DATA_FILE));
			System.out.println("serialized succesfully");
		} catch (Exception e) {
			System.out.println("Problem serializing: " + e);
			e.printStackTrace();
		}
	}

	public GameStage loadGame() {
		GameStage bg = null;
		try {

			final FileHandle handle = Gdx.files.local(Constants.DATA_FILE);
			if (handle.exists()) {
				bg = new Json(OutputType.json).fromJson(GameStage.class, handle);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Problem reading: " + e);
			e.printStackTrace();
		}
		return bg;

	}

	public void deleteGame() {
		try {
			final FileHandle handle = Gdx.files.local(Constants.DATA_FILE);
			if (handle.exists())
				handle.delete();
		} catch (Exception e) {
			System.out.println("Problem deleting: " + e);
			e.printStackTrace();
		}

	}

	public void clearSavedGame() {

	}

	public static GamePreferences getInstance() {
		return instance;
	}

	public boolean isSound() {
		return sound;
	}

	public boolean isMusic() {
		return music;
	}

	public float getVolSound() {
		return volSound;
	}

	public float getVolMusic() {
		return volMusic;
	}

	public int getCharSkin() {
		return charSkin;
	}

	public boolean isShowFpsCounter() {
		return showFpsCounter;
	}

	public Preferences getPrefs() {
		return prefs;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	static boolean hasSavedData() {
		return Gdx.files.local(Constants.DATA_FILE).exists();
	}

	private void deleteSave() {
		final FileHandle handle = Gdx.files.local(Constants.DATA_FILE);
		if (handle.exists())
			handle.delete();
	}
}
