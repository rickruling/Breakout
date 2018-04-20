package com.vish.gdx.breakout.utils;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.core.assets.AssetFonts.fontSize;

public class SkinLoader {
	public static Skin loadSkin(Map<String, TextureRegion> textures) {

		// Base skin

		Skin skin = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

		skin.add("ttf-font", Assets.INSTANCE.getFonts().getfont(fontSize.large), BitmapFont.class);
		skin.add("ttf-font-mid", Assets.INSTANCE.getFonts().getfont(fontSize.medium), BitmapFont.class);
		skin.add("ttf-font-micro", Assets.INSTANCE.getFonts().getfont(fontSize.micro), BitmapFont.class);

		int border = 28;

		// Nine patches
		for (String textureName : textures.keySet()) {
			skin.add(textureName, new NinePatch(textures.get(textureName), border, border, border, border));
		}
		return skin;
	}

}
