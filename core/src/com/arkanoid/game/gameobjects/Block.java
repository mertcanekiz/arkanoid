package com.arkanoid.game.gameobjects;

import com.arkanoid.game.ArkanoidGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Block extends GameObject {
    public BlockType type;
    public int lives;

    public static final float WIDTH = 16f;
    public static final float HEIGHT = 8f;
    private static Texture RED_BLOCK_IMG;
    private static Texture BLUE_BLOCK_IMG;
    private static Texture UNBREAKABLE_BLOCK_IMG;

    public static BlockType INVISIBLE;
    public static BlockType RED;
    public static BlockType BLUE;
    public static BlockType UNBREAKABLE;


    public static void loadImages() {
        RED_BLOCK_IMG = new Texture("block-1.png");
        BLUE_BLOCK_IMG = new Texture("block-2.png");
        UNBREAKABLE_BLOCK_IMG = new Texture("block-3.png");
        INVISIBLE = new BlockType(null);
        RED = new BlockType(RED_BLOCK_IMG);
        BLUE = new BlockType(BLUE_BLOCK_IMG);
        UNBREAKABLE = new BlockType(UNBREAKABLE_BLOCK_IMG);
    }

    public static void disposeImages() {
        RED_BLOCK_IMG.dispose();
        BLUE_BLOCK_IMG.dispose();
        UNBREAKABLE_BLOCK_IMG.dispose();
    }

    public Block(BlockType type, Vector2 pos) {
        this(type, pos, new Vector2(WIDTH, HEIGHT));
    }

    public Block(BlockType type, Vector2 pos, Vector2 size) {
        this.type = type;
        if (type == RED) lives = 1;
        if (type == BLUE) lives = 2;
        if (type == UNBREAKABLE) lives = Integer.MAX_VALUE;
        this.pos = pos;
        this.size = size;
    }

    public void reload() {
        type = RED;
    }

    @Override
    public void update(float dt) {
        //
    }

    @Override
    public void render(SpriteBatch sb) {
        if (type.img != null) {
            sb.draw(type.img, pos.x * ArkanoidGame.SCALE, pos.y * ArkanoidGame.SCALE, WIDTH * ArkanoidGame.SCALE, HEIGHT * ArkanoidGame.SCALE);
        }
    }

    @Override
    public void dispose() {
    }
}
