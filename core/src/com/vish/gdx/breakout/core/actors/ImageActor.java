package com.vish.gdx.breakout.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageActor extends Image {

	Rectangle bounds;

	public Rectangle getBounds() {
		return bounds;
	}

	public ImageActor(TextureRegion texture) {
		super(texture);
		setBounds(getX(), getY(), getWidth(), getHeight());
		bounds = new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	}

	public ImageActor(Texture texture) {
		super(texture);
		setBounds(getX(), getY(), getWidth(), getHeight());
		bounds = new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	}

	public void updateBounds() {
		bounds.setX(getX());
		bounds.setY(getY());
	}
}
