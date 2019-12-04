package com.arkanoid.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Block extends GameObject {

    public enum BlockType {
        INVISIBLE("block-0.png", Integer.MAX_VALUE),
        RED("images/blocks/block-R.png", 1),
        BLUE("images/blocks/block-B.png", 1),
        YELLOW("images/blocks/block-Y.png", 1),
        GREEN("images/blocks/block-G.png", 1),
        MAGENTA("images/blocks/block-M.png", 1),
        PINK("images/blocks/block-P.png", 1),
        CYAN("images/blocks/block-C.png", 1),
        WHITE("images/blocks/block-W.png", 1),
        SINGLE("images/blocks/block-S.png", 1),
        DOUBLE("images/blocks/block-D.png", 2),
        UNBREAKABLE("images/blocks/block-U.png", Integer.MAX_VALUE);

        private Texture img;
        public int lives;

        BlockType(String filename, int lives) {
            if (filename == "block-0.png") img = null;
            else this.img = new Texture(filename);
            this.lives = lives;
        }
    }

    public BlockType type;
    public int lives;

    public static final float WIDTH = 16f;
    public static final float HEIGHT = 8f;

    public static void disposeImages() {
        BlockType.RED.img.dispose();
        BlockType.BLUE.img.dispose();
        BlockType.YELLOW.img.dispose();
        BlockType.GREEN.img.dispose();
        BlockType.MAGENTA.img.dispose();
        BlockType.PINK.img.dispose();
        BlockType.CYAN.img.dispose();
        BlockType.WHITE.img.dispose();
        BlockType.SINGLE.img.dispose();
        BlockType.DOUBLE.img.dispose();
        BlockType.UNBREAKABLE.img.dispose();
    }

    public Block(BlockType type, Vector2 pos) {
        this(type, pos, new Vector2(WIDTH, HEIGHT));
    }

    public Block(BlockType type, Vector2 pos, Vector2 size) {
        this.type = type;
        this.lives = type.lives;
        this.pos = pos;
        this.size = size;
    }

    public void reload() {
        type = BlockType.SINGLE;
    }

    @Override
    public void update(float dt) {
        //
    }

    @Override
    public void render(SpriteBatch sb) {
        if (type.img != null) {
            sb.draw(type.img, pos.x, pos.y, WIDTH, HEIGHT);
        }
    }
}
