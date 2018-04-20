package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.vish.gdx.breakout.actors.BlockGroup;

public class BombBlock extends AbstractBlock  {

	public BombBlock(BlockGroup blockGroup, Vector2 position, World world) {
		super(BlockType.BOMB_BLOCK, blockGroup, position, world);
	}
	
	public BombBlock() {
		super();
	}


	public void decreaseCount() {
		blockGroup.deleteBlock.add(this);
	}

	@Override
	public void addPhysics(float x, float y) {
		x += blockImage.getWidth() / 2;
		y += blockImage.getHeight() / 2;
		super.addPhysics(x, y);
	}

	@Override
	public void addFixture() {
		CircleShape circle = new CircleShape();
		circle.setRadius(blockImage.getHeight() / (2 * PIXELS_TO_METERS));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.filter.categoryBits = WORLD_ENTITY;
		fixtureDef.filter.maskBits = PHYSICS_ENTITY;
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		circle.dispose();
	}

	@Override
	public void destroyEffect() {
		this.remove();
	}

}
