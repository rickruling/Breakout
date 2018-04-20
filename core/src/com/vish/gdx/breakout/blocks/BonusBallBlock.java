package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vish.gdx.breakout.actors.BlockGroup;

public class BonusBallBlock extends AbstractBlock {

	public BonusBallBlock(BlockGroup blockGroup, float x, float y, World world, Actor blockImage) {
		super(BlockType.BONUS_BLOCK, blockGroup, x, y, world, blockImage);
	}

	public BonusBallBlock() {
		super();
	}

	public void decreaseCount() {
		blockGroup.ballCount++;
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
