package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.vish.gdx.breakout.actors.BlockGroup;

public class DoubleClearBlock extends AbstractBlock {

	public boolean touched = false;
	private Actor horizontalEffectActor;
	private Actor verticalEffectActor;

	public DoubleClearBlock(BlockGroup blockGroup, float x, float y, World world, Actor blockImage) {
		super(BlockType.DOUBLE_CLEAR_BLOCK, blockGroup, x, y, world, blockImage);
	}

	public DoubleClearBlock() {
		super();
	}

	public void decreaseCount() {
		if (!touched) {
			verticalEffectActor = this.blockGroup.gameStage.animationLayer.getVerticalBeamfromPool();
			verticalEffectActor
					.setPosition(this.getX() + (this.blockImage.getWidth() - verticalEffectActor.getWidth()) / 2, 0);
			horizontalEffectActor = this.blockGroup.gameStage.animationLayer.getHorizontalBeamfromPool();
			horizontalEffectActor.setPosition(0,
					this.getY() + (this.blockImage.getHeight() - horizontalEffectActor.getHeight()) / 2);
			touched = true;
		}

		SequenceAction sequenceAction = new SequenceAction();
		Action dimAction = Actions.alpha(0, 0.02f);
		Action brightAction = Actions.alpha(1, 0.02f);

		sequenceAction.addAction(brightAction);
		sequenceAction.addAction(dimAction);
		SequenceAction sequenceAction2 = new SequenceAction();
		Action dimAction2 = Actions.alpha(0, 0.02f);
		Action brightAction2 = Actions.alpha(1, 0.02f);

		sequenceAction2.addAction(brightAction2);
		sequenceAction2.addAction(dimAction2);

		if (this.verticalEffectActor.getActions().size == 0) {
			verticalEffectActor.addAction(sequenceAction);
		}
		if (this.horizontalEffectActor.getActions().size == 0) {
			horizontalEffectActor.addAction(sequenceAction2);
		}
		for (Actor act : this.blockGroup.getChildren()) {
			if (act instanceof PhysicalBlock) {
				if ((act.getX() < this.getX()) && (act.getX() + BLOCK_SIZE) > this.getX()) {
					((PhysicalBlock) act).decreaseCount();
				} else if ((act.getY() < this.getY()) && (act.getY() + BLOCK_SIZE) > this.getY()) {
					((PhysicalBlock) act).decreaseCount();
				}
			}
		}

	}

	@Override
	public void step() {
		super.step();
		if (touched) {
			blockGroup.deleteBlock.add(this);
			this.blockGroup.gameStage.animationLayer.returnHorizontalBeamtoPool(horizontalEffectActor);
			this.blockGroup.gameStage.animationLayer.returnVerticalBeam(verticalEffectActor);

		}
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
