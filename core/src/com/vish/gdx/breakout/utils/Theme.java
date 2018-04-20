package com.vish.gdx.breakout.utils;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Theme {
	
    private ImageButton play;
    
	public void init(Map<String, TextureRegion> textures) {
		ImageButtonStyle buttonStyles = new ImageButton.ImageButtonStyle();
		buttonStyles.up = new TextureRegionDrawable(textures.get("play"));
		play = new ImageButton(buttonStyles);
		
	}

	public ImageButton getPlay() {
		return play;
	}

	public void setPlay(ImageButton play) {
		this.play = play;
	}


}
