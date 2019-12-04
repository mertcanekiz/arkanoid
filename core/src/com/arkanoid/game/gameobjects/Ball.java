package com.arkanoid.game.gameobjects;

import com.arkanoid.game.Util;
import com.arkanoid.game.gameobjects.Block.BlockType;
import com.arkanoid.game.Arkanoid;
import com.arkanoid.game.states.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Ball extends GameObject {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    private static Sound paddleSound = Gdx.audio.newSound(Gdx.files.internal("sfx/paddle.wav"));
    private static Sound blockSound = Gdx.audio.newSound(Gdx.files.internal("sfx/block.wav"));
    private static Sound unbreakableBlockSound = Gdx.audio.newSound(Gdx.files.internal("sfx/unbreakable.wav"));

    private boolean playing = false;

    private static Texture ballImg = new Texture("images/ball.png");

    private boolean isAttached = true;
    private float offset;

    public Ball(Paddle paddle) {
        this.img = ballImg;
        this.size = new Vector2(WIDTH, HEIGHT);
        attach(paddle);
    }

    public void reset() {
        attach(Game.getInstance().paddle);
    }

    public void attach(Paddle paddle) {
        offset = (Util.random(-0.4f, 0.4f)) * paddle.size.x;
        System.out.println(offset);
        isAttached = true;
        this.vel = new Vector2(0, 0);
    }

    public void detach(Paddle paddle) {
        float MAX_VERTICAL_VEL = Game.getInstance().levels[Game.getInstance().currentLevel].MAX_VERTICAL_VEL;
        float MAX_HORIZONTAL_VEL = Game.getInstance().levels[Game.getInstance().currentLevel].MAX_HORIZONTAL_VEL;
        float percentage = Math.abs(pos.x - paddle.pos.x) / paddle.size.x;
        vel.x = Util.map(percentage, 0f, 1f, -MAX_HORIZONTAL_VEL, MAX_HORIZONTAL_VEL);
        vel.y = 60;
        isAttached = false;
    }

    @Override
    public void update(float dt) {

        if (isAttached) {
            Paddle paddle = Game.getInstance().paddle;
            pos.x = paddle.pos.x + paddle.size.x / 2f - size.x / 2f + offset;
            pos.y = paddle.pos.y + paddle.size.y;
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                detach(paddle);
            }
        } else {
            Block hitBlock = null;
            Vector2 center = new Vector2(pos.x + size.x / 2, pos.y + size.y / 2);
            Rectangle ballRect = new Rectangle(pos.x, pos.y, size.x, size.y);
            if (pos.x < 8) {
                vel.x *= -1;
                pos.x = 8;
            }
            if (pos.x + size.x > Arkanoid.WIDTH - 8) {
                vel.x *= -1;
                pos.x = Arkanoid.WIDTH - 8 - size.x;
            }
            if (pos.y + size.y > Arkanoid.HEIGHT - 8) {
                vel.y *= -1;
                pos.y = Arkanoid.HEIGHT - 8 - size.y;
            }
            if (pos.y < 0) {
                Game.getInstance().die();
            }
            for (Block go : Game.getInstance().levels[Game.getInstance().currentLevel].blocks) {
                Rectangle blockRect = new Rectangle(go.pos.x, go.pos.y, go.size.x, go.size.y);
                Vector2 goCenter = new Vector2(go.pos.x + go.size.x / 2, go.pos.y + go.size.y / 2);
                if (blockRect.overlaps(ballRect)) {
                    hitBlock = go;
                    if (go.type == BlockType.UNBREAKABLE || go.type == BlockType.DOUBLE) {
                        blockSound.stop();
                        unbreakableBlockSound.stop();
                        unbreakableBlockSound.play();
                    } else {
                        unbreakableBlockSound.stop();
                        blockSound.stop();
                        blockSound.play();
                    }
                    float dist = (float) Math.sqrt((center.x - goCenter.x) * (center.x - goCenter.x) + (center.y - goCenter.y) * (center.y - goCenter.y));
                    if (center.x > blockRect.x && center.x < blockRect.x + blockRect.width) {
                        // Top or bottom
                        if (center.y < blockRect.y) {
                            // Bottom
                            vel.y *= -1;
                            pos.y = blockRect.y - size.y;
                        } else if (center.y > blockRect.y + blockRect.height) {
                            // Top
                            vel.y *= -1;
                            pos.y = blockRect.y + blockRect.height;
                        }
                    } else if (center.y > blockRect.y && center.y < blockRect.y + blockRect.height) {
                        // Left or right
                        if (center.x < blockRect.x) {
                            // Left
                            vel.x *= -1;
                            pos.x = blockRect.x - size.x;
                        } else if (center.x > blockRect.x + blockRect.width) {
                            // Right
                            vel.x *= -1;
                            pos.x = blockRect.x + blockRect.width;
                        }
                    } else {
                        // Corners
                        if (center.y < blockRect.y) {
                            // Bottom
                            vel.y *= -1;
                            pos.y = blockRect.y - size.y - 1;
                            break;
                        } else if (center.y > blockRect.y + blockRect.height) {
                            // Top
                            vel.y *= -1;
                            pos.y = blockRect.y + blockRect.height + 1;
                            break;
                        }
                    }
                }
            }
            if (hitBlock != null && hitBlock.type != BlockType.INVISIBLE && hitBlock.type != BlockType.UNBREAKABLE) {
                Game.getInstance().levels[Game.getInstance().currentLevel].hit(hitBlock);
            }

            Paddle paddle = Game.getInstance().paddle;
            Rectangle paddleRect = new Rectangle(paddle.pos.x, paddle.pos.y + paddle.size.y - 4, paddle.size.x, 4);
            float MAX_VERTICAL_VEL = Game.getInstance().levels[Game.getInstance().currentLevel].MAX_VERTICAL_VEL;
            float MAX_HORIZONTAL_VEL = Game.getInstance().levels[Game.getInstance().currentLevel].MAX_HORIZONTAL_VEL;
            if (paddleRect.overlaps(ballRect)) {
                float percentage = Math.abs(pos.x - paddle.pos.x) / paddle.size.x;
                vel.x = Util.map(percentage, 0f, 1f, -MAX_HORIZONTAL_VEL, MAX_HORIZONTAL_VEL);
                if (Math.abs(vel.x) < 30) {
                    vel.x = (vel.x < 0 ? -1 : 1) * 30f;
                }
                vel.y = -vel.y * 1.1f;
                if (vel.y > MAX_VERTICAL_VEL) {
                    vel.y = MAX_VERTICAL_VEL;
                } else if (vel.y < -MAX_VERTICAL_VEL) {
                    vel.y = -MAX_VERTICAL_VEL;
                }
                pos.y = paddle.pos.y + paddle.size.y + 1;
                paddleSound.play();
            }
            this.pos.x += this.vel.x * dt;
            this.pos.y += this.vel.y * dt;
        }
    }

    @Override
    public void dispose() {
        ballImg.dispose();
        unbreakableBlockSound.dispose();
        blockSound.dispose();
        paddleSound.dispose();
    }
}
