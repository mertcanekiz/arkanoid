package com.arkanoid.game.gameobjects;

import com.arkanoid.game.gameobjects.Block.BlockType;
import com.arkanoid.game.ArkanoidGame;
import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends GameObject {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;


    public Ball(Vector2 pos) {
        this.pos = pos;
        this.size = new Vector2(WIDTH, HEIGHT);
        this.vel = new Vector2(50, -60);
        this.img = new Texture("ball.png");
    }

    float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    @Override
    public void update(float dt) {
        this.pos.x += this.vel.x * dt;
        this.pos.y += this.vel.y * dt;

        Block hitBlock = null;
        Vector2 topLeft = new Vector2(pos.x, pos.y + size.y);
        Vector2 topRight = new Vector2(pos.x + size.x, pos.y + size.y);
        Vector2 bottomLeft = new Vector2(pos.x, pos.y);
        Vector2 bottomRight = new Vector2(pos.x + size.x, pos.y);
        Vector2 center = new Vector2(pos.x + size.x / 2, pos.y + size.y / 2);
        Rectangle ballRect = new Rectangle(pos.x, pos.y, size.x, size.y);
        for (Block go : Game.getInstance().level.blocks) {
            Rectangle blockRect = new Rectangle(go.pos.x, go.pos.y, go.size.x, go.size.y);
            if (blockRect.overlaps(ballRect)) {
                hitBlock = go;
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
                    } else if (center.y > blockRect.y + blockRect.height) {
                        // Top
                        vel.y *= -1;
                        pos.y = blockRect.y + blockRect.height + 1;
                    }
                }
                if (go.type == BlockType.UNBREAKABLE || go.type == BlockType.DOUBLE) {
                    break;
                }
            }
        }
        if (hitBlock != null && hitBlock.type != BlockType.INVISIBLE && hitBlock.type != BlockType.UNBREAKABLE) {
            Game.getInstance().level.hit(hitBlock);
        }

        Paddle paddle = Game.getInstance().paddle;
        Rectangle paddleRect = new Rectangle(paddle.pos.x, paddle.pos.y + paddle.size.y - 4, paddle.size.x, 4);
        float MAX_VERTICAL_VEL = Game.getInstance().level.MAX_VERTICAL_VEL;
        float MAX_HORIZONTAL_VEL = Game.getInstance().level.MAX_HORIZONTAL_VEL;
        if (paddleRect.overlaps(ballRect)) {
            float percentage = (pos.x - paddle.pos.x) / paddle.size.x;
            float yDir = vel.y < 0 ? 1 : -1;
            yDir *= -1;
            vel.x += map(percentage, 0, 1, -100, 100);
            if (vel.x > MAX_HORIZONTAL_VEL) {
                vel.x = MAX_HORIZONTAL_VEL;
            } else if (vel.x < -MAX_HORIZONTAL_VEL) {
                vel.x = -MAX_HORIZONTAL_VEL;
            }
            vel.y = yDir * vel.y * 1.05f;
            if (vel.y > MAX_VERTICAL_VEL) {
                vel.y = MAX_VERTICAL_VEL;
            } else if (vel.y < -MAX_VERTICAL_VEL) {
                vel.y = -MAX_VERTICAL_VEL;
            }
            pos.y = paddle.pos.y + paddle.size.y + 1;
        }


    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
