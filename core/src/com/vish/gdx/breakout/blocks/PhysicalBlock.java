package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.vish.gdx.breakout.actors.BlockGroup;
import com.vish.gdx.breakout.actors.ThreeeDBlock;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.assets.Assets;

public class PhysicalBlock extends AbstractBlock {

	private static final long serialVersionUID = 1L;
	private transient Label hitCountActor;
	private int hitCount;
	GlyphLayout glyphLayout;

	public PhysicalBlock(World world, float x, float y, int blockValue, BlockGroup blockGroup, Actor blockImage) {
		super(BlockType.PHYSICAL_BLOCK, blockGroup, x, y, world, blockImage);
		hitCount = blockValue;
		initialise();
	}

	public PhysicalBlock() {
		super();
		blockType = BlockType.PHYSICAL_BLOCK;
	}

	private void initialise() {
		hitCountActor = new Label(String.valueOf(hitCount), Assets.INSTANCE.getSkinLibgdx(), "ttf-font-micro",
				Color.WHITE);
		hitCountActor.setPosition(
				((ThreeeDBlock) blockImage).frontBlock.getX()
						+ (((ThreeeDBlock) blockImage).frontBlock.getWidth() - hitCountActor.getWidth()) / 2,
				((ThreeeDBlock) blockImage).frontBlock.getY()
						+ (((ThreeeDBlock) blockImage).frontBlock.getHeight() - hitCountActor.getHeight()) / 2);
		setBlockColor();
		super.addActor(hitCountActor);
		glyphLayout = new GlyphLayout();
	}

	@Override
	public void load(World world, Actor blockImage) {
		super.load(world, blockImage);
		initialise();
	}

	private void setBlockColor() {
		// Action a = Actions.color(Color.WHITE, Color.alpha(0));
		// this.addAction(a);
		if (hitCount > 1000) {
			blockImage.setColor(Color.BROWN);
		} else if (hitCount > 500) {
			blockImage.setColor(Color.CORAL);
		} else if (hitCount > 300) {
			blockImage.setColor(Color.FOREST);
		} else if (hitCount > 200) {
			blockImage.setColor(Color.RED);
		} else if (hitCount > 100) {
			blockImage.setColor(Color.PURPLE);
		} else if (hitCount > 50) {
			blockImage.setColor(Color.CHARTREUSE);
		} else if (hitCount > 25) {
			blockImage.setColor(Color.SKY);
		} else if (hitCount > 15) {
			blockImage.setColor(Color.MAROON);
		} else if (hitCount > 5) {
			blockImage.setColor(Color.MAGENTA);
		} else {
			blockImage.setColor(Color.GREEN);
		}
	}

	@Override
	public void decreaseCount() {
		setBlockColor();
		hitCountActor.setText(String.valueOf(--hitCount));
		glyphLayout.setText(Assets.INSTANCE.getSkinLibgdx().getFont("ttf-font-micro"), hitCountActor.getText());
		hitCountActor.setX(((ThreeeDBlock) blockImage).frontBlock.getX()
				+ (((ThreeeDBlock) blockImage).frontBlock.getWidth() - glyphLayout.width) / 2);
		if (hitCount < 1) {
			blockGroup.deleteBlock.add(this);
		}
		Assets.INSTANCE.getSounds().play(this.blockType);

	}

	@Override
	public void destroyEffect() {
		this.removeActor(blockImage);
		this.removeActor(hitCountActor);
//		this.addActor(new ImageActor(new Texture(
//				Assets.INSTANCE.getDynamicAssets().createIsosecelesTrianglePixmap(BLOCK_SIZE / 2, Color.BLUE))));
	}

	@Override
	public void addPhysics(float x, float y) {
		x += BLOCK_MARGIN + BLOCK_SIZE / 2;
		y += BLOCK_MARGIN + BLOCK_SIZE / 2;
		super.addPhysics(x, y);
	}

	@Override
	public void addFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(BLOCK_SIZE / (2 * PIXELS_TO_METERS), BLOCK_SIZE / (2 * PIXELS_TO_METERS));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = WORLD_ENTITY;
		fixtureDef.filter.maskBits = PHYSICS_ENTITY;
		body.createFixture(fixtureDef);
		shape.dispose();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void write(Json json) {
		json.writeValue("hitCount", hitCount);
		super.write(json);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		hitCount = json.readValue("hitCount", int.class, jsonData);
		super.read(json, jsonData);
	}

	@Override
	protected void positionChanged() {
		((ThreeeDBlock) blockImage).updateY(this.getX(), this.getY());
		super.positionChanged();
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	@Override
	public String toString() {
		return "PhysicalBlock [hitCountActor=" + hitCountActor + ", hitCount=" + hitCount + ", blockImage=" + blockImage
				+ ", blockType=" + blockType + "]";
	}

}
