package com.vish.gdx.breakout.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.vish.gdx.breakout.GameStage;
import com.vish.gdx.breakout.blocks.AbstractBlock;
import com.vish.gdx.breakout.blocks.AbstractBlock.BlockType;
import com.vish.gdx.breakout.blocks.BlockCreatorFactory;
import com.vish.gdx.breakout.blocks.PhysicalBlock;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.utils.Constants;

public class BlockGroup extends Group implements Constants, Serializable {

	private static final long serialVersionUID = 1L;
	Random random = new Random();
	public boolean gameOver = false;
	public transient World world;
	public Set<AbstractBlock> deleteBlock;
	public int maxScore;
	Array<Actor> currentChildren;
	public int step = 1;
	public int ballCount = 1;
	public GameStage gameStage;
	BlockCreatorFactory blockCreatorFactory;

	public BlockGroup() {
		super();
		blockCreatorFactory = new BlockCreatorFactory();
	}

	public BlockGroup(World world, int maxScore, GameStage gameStage) {
		super();
		super.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.world = world;
		this.maxScore = maxScore;
		deleteBlock = new HashSet<AbstractBlock>();
		this.gameStage = gameStage;
		blockCreatorFactory = new BlockCreatorFactory();
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public void nextStep() {
		for (int i = 0; i < this.getChildren().size; i++) {
			AbstractBlock child = (AbstractBlock) this.getChildren().get(i);
			if ((this.getY() + child.getY()) < (LOWER_MARGIN + 2 * BLOCK_SIZE)) {
				if (child instanceof PhysicalBlock) {
					System.out.println("Game Over,clearing off.");
					gameOver = true;
					break;
				} else {
					((AbstractBlock) child).decreaseCount();
				}
			}
		}
		if (gameOver) {
			clearAllChildren();
			if (maxScore < step - 1) {
				Assets.INSTANCE.getPreferences().maxScore = step - 1;
				Assets.INSTANCE.getPreferences().save();
			}
			Assets.INSTANCE.getPreferences().deleteGame();
			return;
		}

		ArrayList<BlockType> blocks = new ArrayList<BlockType>();
		blocks.add(BlockType.BONUS_BLOCK);
		blocks.add(BlockType.PHYSICAL_BLOCK);

		if (random.nextInt(3) > 0) {
			switch (random.nextInt(3)) {
			case 0:
				blocks.add(BlockType.HORIZONTAL_CLEARER);
				break;
			case 1:
				blocks.add(BlockType.VERTICAL_CLEARER);
				break;
			case 2:
				blocks.add(BlockType.DOUBLE_CLEAR_BLOCK);
				break;
			}
		}
		int blockValue = step;
		if (random.nextInt(9) >= 7)
			blockValue = step * 2;
		int initialBlockSize = blocks.size();
		for (int i = 0; i < NUMBER_OF_BLOCKS - initialBlockSize; i++) {
			if (random.nextInt(10) > 6) {
				blocks.add(BlockType.EMPTY_BLOCK);
			} else {
				blocks.add(BlockType.PHYSICAL_BLOCK);
			}
		}

		Collections.shuffle(blocks);
		int i = 0;
		for (BlockType blockType : blocks) {
			this.addActor(blockCreatorFactory.createBlock(blockType, world, blockValue, this, i++));
		}
		Assets.INSTANCE.getPreferences().saveGame(this.gameStage);
		updateBlocks();
	}

	public void updateBlocks() {
		for (Actor child : this.getChildren())
			((AbstractBlock) child).step();

	}

	private void clearAllChildren() {
		while (this.getChildren().size > 0) {
			Actor act = this.getChildren().first();
			world.destroyBody(((AbstractBlock) act).body);
			act.remove();
		}
	}

	public void clearPhysics() {
		for (final AbstractBlock child : deleteBlock) {
			world.destroyBody(child.body);
			child.remove();
		}
		deleteBlock.clear();
	}

	@Override
	public void write(Json json) {
		json.writeValue("gameOver", gameOver);
		json.writeValue("maxScore", maxScore);
		json.writeValue("children", this.getChildren());
		json.writeValue("step", step);
		json.writeValue("ballCount", ballCount);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		System.out.println("Reading blockGroup");
		gameOver = json.readValue("gameOver", boolean.class, jsonData);
		maxScore = json.readValue("maxScore", int.class, jsonData);
		currentChildren = json.readValue("children", Array.class, jsonData);
		step = json.readValue("step", int.class, jsonData);
		ballCount = json.readValue("ballCount", int.class, jsonData);
	}

	public void load() {
		System.out.println("creating children");
		deleteBlock = new HashSet<AbstractBlock>();
		for (Actor actor : currentChildren) {
			try {
				((AbstractBlock) actor).blockGroup = this;
				((AbstractBlock) actor).load(world,
						blockCreatorFactory.createBlockImage(((AbstractBlock) actor).blockType));
				this.addActor(actor);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Invalid type coversion ");
			}
		}
	}

	@Override
	public void addActor(Actor actor) {
		if (actor == null)
			return;
		super.addActor(actor);
	}

	@Override
	public String toString() {
		return "BlockGroup [gameOver=" + gameOver + ", deleteBlock=" + deleteBlock + ", maxScore=" + maxScore
				+ " children : " + this.getChildren() + "]";
	}

}
