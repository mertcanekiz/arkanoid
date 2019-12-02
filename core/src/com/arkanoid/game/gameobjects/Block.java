package com.arkanoid.game.gameobjects;

import com.arkanoid.game.ArkanoidGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Block extends GameObject {

    public enum BlockType {
        INVISIBLE("block-0.png", Integer.MAX_VALUE),
        RED("block-R.png", 1),
        BLUE("block-B.png", 1),
        YELLOW("block-Y.png", 1),
        GREEN("block-G.png", 1),
        MAGENTA("block-M.png", 1),
        PINK("block-P.png", 1),
        CYAN("block-C.png", 1),
        WHITE("block-W.png", 1),
        SINGLE("block-D.png", 1),
        DOUBLE("block-D.png", 2),
        UNBREAKABLE("block-U.png", Integer.MAX_VALUE);

        private Texture img;
        public int lives;

        BlockType(String filename, int lives) {
            if (filename == "block-0.png") img = null;
            else this.img = new Texture(filename);
            this.lives = lives;
        }
    }

    public BlockType type;

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
            sb.draw(type.img, pos.x * ArkanoidGame.SCALE, pos.y * ArkanoidGame.SCALE, WIDTH * ArkanoidGame.SCALE, HEIGHT * ArkanoidGame.SCALE);
        }
    }

    @Override
    public void dispose() {
    }
}
