package com.arkanoid.game;

import com.arkanoid.game.gameobjects.Block;
import com.arkanoid.game.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {
    public ArrayList<Block> blocks = new ArrayList<>();
    private Texture bgImg;
    public float MAX_HORIZONTAL_VEL;
    public float MAX_VERTICAL_VEL;

    public Level(String filename) {
        Block.loadImages();
        blocks.add(new Block(Block.INVISIBLE, new Vector2(0, 0), new Vector2(8, ArkanoidGame.HEIGHT )));
        blocks.add(new Block(Block.INVISIBLE, new Vector2((ArkanoidGame.WIDTH - 8 ) , 0), new Vector2(8, ArkanoidGame.HEIGHT )));
        blocks.add(new Block(Block.INVISIBLE, new Vector2(0, ArkanoidGame.HEIGHT - 8 ), new Vector2(ArkanoidGame.WIDTH , 8)));
        FileHandle handle = Gdx.files.internal("levels/" + filename);
        String mapFile = handle.readString();
        String lines[] = mapFile.split("\\r?\\n|\\r");
        String bgFilename = lines[0];
        MAX_HORIZONTAL_VEL = Float.parseFloat(lines[1]);
        MAX_VERTICAL_VEL = Float.parseFloat(lines[2]);
        for (int i = 3; i < lines.length; i++) {
            String tokens[] = lines[i].split(" ");
            for (int j = 0; j < tokens.length; j++) {
                switch (tokens[j]) {
                    case "1":
                        blocks.add(new Block(Block.RED, new Vector2((Block.WIDTH / 2 + j * Block.WIDTH), ArkanoidGame.HEIGHT - i * Block.HEIGHT), new Vector2(Block.WIDTH, Block.HEIGHT)));
                        break;
                    case "2":
                        blocks.add(new Block(Block.BLUE, new Vector2((Block.WIDTH / 2 + j * Block.WIDTH) , (ArkanoidGame.HEIGHT - i * Block.HEIGHT)), new Vector2(Block.WIDTH , Block.HEIGHT )));
                        break;
                    case "3":
                        blocks.add(new Block(Block.UNBREAKABLE, new Vector2((Block.WIDTH / 2 + j * Block.WIDTH), (ArkanoidGame.HEIGHT - i * Block.HEIGHT)), new Vector2(Block.WIDTH, Block.HEIGHT)));
                        break;
                }
            }
            System.out.println();
        }
        bgImg = new Texture(bgFilename);

    }

    public void hit(Block block) {
        if (block.lives == 1) {
            blocks.remove(block);
        } else if (block.lives == 2) {
            block.lives = 1;
            block.reload();
        }
    }

    public void update(float dt) {
        for (GameObject gameObject : blocks) {
            gameObject.update(dt);
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(bgImg, 0, 0, ArkanoidGame.WIDTH * ArkanoidGame.SCALE, ArkanoidGame.HEIGHT * ArkanoidGame.SCALE);
        for (GameObject gameObject : blocks) {
            gameObject.render(sb);
        }
    }

    public void dispose() {
        bgImg.dispose();
        Block.disposeImages();
    }
}
