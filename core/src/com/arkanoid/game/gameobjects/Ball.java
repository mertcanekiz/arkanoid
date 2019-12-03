package com.arkanoid.game.gameobjects;

import com.arkanoid.game.gameobjects.Block.BlockType;
import com.arkanoid.game.ArkanoidGame;
import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Ball extends GameObject {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    public Ball(Vector2 pos) {
        this.pos = pos;
        this.size = new Vector2(WIDTH, HEIGHT);
        this.vel = new Vector2((int)(new Random().nextFloat() * 100), -60);
        this.img = new Texture("ball.png");
    }

    float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    Vector2 intercept(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, String d) {
        float denom = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (denom != 0) {
            float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / denom;
            if ((ua >= 0) && (ua <= 1)) {
                float ub = (((x2 - x1) * (y1 - y3)) - ((y2 - y1) * (x1 - x3))) / denom;
                if ((ub >= 0) && (ub <= 1)) {
                    float x = x1 + (ua * (x2 - x1));
                    float y = y1 + (ua * (y2 - y1));
                    return new Vector2(x, y);
                }
            }
        }
        return null;
    }

    Vector2 ballIntercept(Ball ball, Rectangle rect, float nx, float ny) {
        Vector2 pt = null;
        if (nx < 0) {
            pt = intercept(ball.pos.x, ball.pos.y, ball.pos.x + nx, ball.pos.y + ny,
                    rect.x + rect.width + ball.size.x,
                    rect.y + rect.height - ball.size.x,
                    rect.x + rect.width + ball.size.x,
                    rect.y + ball.size.x,
                    "right");
        }
        else if (nx > 0) {
            pt = intercept(ball.pos.x, ball.pos.y, ball.pos.x + nx, ball.pos.y + ny,
                    rect.x - ball.size.x,
                    rect.y + rect.height - ball.size.x,
                    rect.x - ball.size.x,
                    rect.y + ball.size.x,
                    "left");
        }
        if (pt == null) {
            if (ny < 0) {
                pt = intercept(ball.pos.x, ball.pos.y, ball.pos.x + nx, ball.pos.y + ny,
                        rect.x - ball.size.x,
                        rect.y + ball.size.x,
                        rect.x + rect.width + ball.size.x,
                        rect.y + ball.size.x,
                        "bottom");
            } else if (ny > 0) {
                pt = intercept(ball.pos.x, ball.pos.y, ball.pos.x + nx, ball.pos.y + ny,
                        rect.x - ball.size.x,
                        rect.y + rect.height - ball.size.x,
                        rect.x + rect.width + ball.size.x,
                        rect.y + rect.height - ball.size.x,
                        "top");
            }
        }
        return pt;
    }

    @Override
    public void update(float dt) {
        Block hitBlock = null;
        Vector2 topLeft = new Vector2(pos.x, pos.y + size.y);
        Vector2 topRight = new Vector2(pos.x + size.x, pos.y + size.y);
        Vector2 bottomLeft = new Vector2(pos.x, pos.y);
        Vector2 bottomRight = new Vector2(pos.x + size.x, pos.y);
        Vector2 center = new Vector2(pos.x + size.x / 2, pos.y + size.y / 2);
        Rectangle ballRect = new Rectangle(pos.x, pos.y, size.x, size.y);
        if (pos.x < 8) {
            vel.x *= -1;
            pos.x = 8;
        }
        if (pos.x + size.x > ArkanoidGame.WIDTH - 8) {
            vel.x *= -1;
            pos.x = ArkanoidGame.WIDTH - 8 - size.x;
        }
        if (pos.y + size.y > ArkanoidGame.HEIGHT - 8) {
            vel.y *= -1;
            pos.y = ArkanoidGame.HEIGHT - 8 - size.y;
        }
        for (Block go : Game.getInstance().level.blocks) {
            Rectangle blockRect = new Rectangle(go.pos.x, go.pos.y, go.size.x, go.size.y);
            Vector2 goCenter = new Vector2(go.pos.x + go.size.x / 2, go.pos.y + go.size.y / 2);
            if (blockRect.overlaps(ballRect)) {
                hitBlock = go;
                float dist = (float) Math.sqrt((center.x - goCenter.x)*(center.x - goCenter.x) + (center.y - goCenter.y) * (center.y - goCenter.y));
                if (center.x > blockRect.x && center.x < blockRect.x + blockRect.width) {
                    // Top or bottom
                    if (center.y < blockRect.y) {
                        // Bottom
                        vel.y *= -1;
                        pos.y = blockRect.y - size.y;
//                        break;
                    } else if (center.y > blockRect.y + blockRect.height) {
                        // Top
                        vel.y *= -1;
                        pos.y = blockRect.y + blockRect.height;
//                        break;
                    }
                } else if (center.y > blockRect.y && center.y < blockRect.y + blockRect.height) {
                    // Left or right
                    if (center.x < blockRect.x) {
                        // Left
                        vel.x *= -1;
                        pos.x = blockRect.x - size.x;
//                        break;
                    } else if (center.x > blockRect.x + blockRect.width) {
                        // Right
                        vel.x *= -1;
                        pos.x = blockRect.x + blockRect.width;
//                        break;
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
            Game.getInstance().level.hit(hitBlock);
        }

        Paddle paddle = Game.getInstance().paddle;
        Rectangle paddleRect = new Rectangle(paddle.pos.x, paddle.pos.y + paddle.size.y - 4, paddle.size.x, 4);
        float MAX_VERTICAL_VEL = Game.getInstance().level.MAX_VERTICAL_VEL;
        float MAX_HORIZONTAL_VEL = Game.getInstance().level.MAX_HORIZONTAL_VEL;
        if (paddleRect.overlaps(ballRect)) {
            float percentage = (pos.x - paddle.pos.x) / paddle.size.x;
            vel.x = map(percentage, 0, 1, -MAX_HORIZONTAL_VEL, MAX_HORIZONTAL_VEL);
            vel.y = -vel.y * 1.1f;
            if (vel.y > MAX_VERTICAL_VEL) {
                vel.y = MAX_VERTICAL_VEL;
            } else if (vel.y < -MAX_VERTICAL_VEL) {
                vel.y = -MAX_VERTICAL_VEL;
            }
            pos.y = paddle.pos.y + paddle.size.y + 1;
        }
        this.pos.x += this.vel.x * dt;
        this.pos.y += this.vel.y * dt;

    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
