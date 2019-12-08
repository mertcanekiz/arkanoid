package com.arkanoid.game;

import com.arkanoid.game.gameobjects.Block;
import com.arkanoid.game.gameobjects.Block.BlockType;
import com.arkanoid.game.gameobjects.GameObject;
import com.arkanoid.game.powerups.Fireball;
import com.arkanoid.game.powerups.PowerUp;
import com.arkanoid.game.states.Game;
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
    private String filename;

    public Level(String filename) {
        System.out.println("new level");
        this.filename = filename;
        reset();
    }

    public void reset() {
        System.out.println("level reset");
        FileHandle handle = Gdx.files.internal(filename);
        String mapFile = handle.readString();
        String[] lines = mapFile.split("\\r?\\n|\\r");
        String bgFilename = lines[0];
        MAX_HORIZONTAL_VEL = Float.parseFloat(lines[1]);
        MAX_VERTICAL_VEL = Float.parseFloat(lines[2]);
        for (int i = 3; i < lines.length; i++) {
            String[] tokens = lines[i].split(" ");
            for (int j = 0; j < tokens.length; j++) {
                BlockType type;

                switch (tokens[j]) {
                    case "R":
                        type = BlockType.RED;
                        break;
                    case "B":
                        type = BlockType.BLUE;
                        break;
                    case "Y":
                        type = BlockType.YELLOW;
                        break;
                    case "G":
                        type = BlockType.GREEN;
                        break;
                    case "M":
                        type = BlockType.MAGENTA;
                        break;
                    case "P":
                        type = BlockType.PINK;
                        break;
                    case "C":
                        type = BlockType.CYAN;
                        break;
                    case "W":
                        type = BlockType.WHITE;
                        break;
                    case "D":
                        type = BlockType.DOUBLE;
                        break;
                    case "U":
                        type = BlockType.UNBREAKABLE;
                        break;
                    default:
                        continue;
                }

                blocks.add(new Block(type, new Vector2((Block.WIDTH / 2 + j * Block.WIDTH) , (Arkanoid.HEIGHT - i * Block.HEIGHT))));
            }
        }
        bgImg = new Texture("images/" + bgFilename);
    }

    public void hit(Block block) {
        System.out.println("hit");
        if (Game.getInstance().ball.isSoftball()) {
            // Do not remove blocks if it's a soft ball.
            return;
        }
        if (Game.getInstance().ball.isFireball()) {
            if (block.type != BlockType.UNBREAKABLE) {
                blocks.remove(block);
                Game.getInstance().paddle.score += 60;
                float random = Util.random(0, 1);
                if (random < 0.3f) {
                    Game.getInstance().powerups.add(PowerUp.randomPowerUp(block.pos));
                }
                System.out.println("Fireball");
            }
        } else {
            if (block.lives == 1) {
                blocks.remove(block);
                Game.getInstance().paddle.score += 60;
                float random = Util.random(0, 1);
                if (random < 0.3f) {
                    Game.getInstance().powerups.add(PowerUp.randomPowerUp(block.pos));
                }
                System.out.println("Single brick no fire");
            } else if (block.lives == 2) {
                block.lives = 1;
                block.type = BlockType.SINGLE;
                block.reload();
                Game.getInstance().paddle.score += 40;
                System.out.println("Double brick");
            } else {
                System.out.println("how");
            }
        }
    }

    public void update(float dt) {
        for (GameObject gameObject : blocks) {
            gameObject.update(dt);
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(bgImg, 0, 0, Arkanoid.WIDTH, Arkanoid.HEIGHT);
        for (GameObject gameObject : blocks) {
            gameObject.render(sb);
        }
    }

    public void dispose() {
        bgImg.dispose();
        Block.disposeImages();
    }
}
