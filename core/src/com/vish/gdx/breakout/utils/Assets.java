package com.vish.gdx.breakout.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener, Constants {

	public static final Assets instance = new Assets();

	private AssetFonts fonts;

	public void init(AssetManager assetManager) {
		fonts = new AssetFonts();
	}

	public BitmapFont getfont(fontSize size) {
		switch (size) {
		case small:
			return fonts.defaultSmall;
		case medium:
			return fonts.defaultMiddle;
		case large:
			return fonts.defaultBig;
		default:
			return null;
		}
	}

	public class AssetFonts {
		public BitmapFont defaultSmall;
		public BitmapFont defaultMiddle;
		public BitmapFont defaultBig;

		public AssetFonts() {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
					Gdx.files.internal("font/jellee-typeface/Jellee-Roman.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 18; // font size
			defaultSmall = generator.generateFont(parameter);
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			parameter.size = 20;
			defaultMiddle = generator.generateFont(parameter);
			defaultMiddle.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			parameter.size = 55;
			defaultBig = generator.generateFont(parameter);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		}
	}

	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {

	}

	@Override
	public void dispose() {

	}

}
