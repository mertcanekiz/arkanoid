
package com.arkanoid.game.gameobjects;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Paddle extends GameObject {

    public static final float WIDTH = 32f;
    public static final float HEIGHT = 8f;

    public static Texture img_normal = new Texture("images/paddle.png");
    public static Texture img_large = new Texture("images/paddle_lg.png");
    public static Texture img_small = new Texture("images/paddle_sm.png");

    public int lives = 3;
    public int score = 0;
    public int currentSize = 0;

    public Paddle() {
        img = img_normal;
        this.size = new Vector2(WIDTH, HEIGHT);
        this.pos.x = (Arkanoid.WIDTH - size.x) / 2.0f;
        this.pos.y = 10;
    }

    public void reset() {
        this.pos.x = (Arkanoid.WIDTH - size.x) / 2.0f;
        this.vel.x = 0.0f;
        this.vel.y = 0.0f;
        setSize(0);
    }

    private void setSize(int size) {
        if (size < 0) {
            img = img_small;
            this.size = new Vector2(24f, HEIGHT);
            size = -1;
        } else if (size == 0) {
            img = img_normal;
            this.size = new Vector2(32f, HEIGHT);
        } else {
            img = img_large;
            this.size = new Vector2(48f, HEIGHT);
            size = 1;
        }
    }

    public void shrink() {
        setSize(currentSize - 1);
    }

    public void enlarge() {
        setSize(currentSize + 1);
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= 200 * dt;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += 200 * dt;
        }
        if (pos.x < 8) pos.x = 8;
        if (pos.x > Arkanoid.WIDTH - 8 - size.x) pos.x = Arkanoid.WIDTH - 8 - size.x;
    }

    @Override
    public void dispose() {
        img_normal.dispose();
        img_large.dispose();
        img_small.dispose();
    }
}
