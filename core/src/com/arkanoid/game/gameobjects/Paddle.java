
package com.arkanoid.game.gameobjects;

import com.arkanoid.game.ArkanoidGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Paddle extends GameObject {

    public static final float WIDTH = 32f;
    public static final float HEIGHT = 8f;

    public Paddle() {
        img = new Texture("paddle.png");
        this.size = new Vector2(WIDTH, HEIGHT);
        this.pos.x = (ArkanoidGame.WIDTH - size.x) / 2.0f;
        this.pos.y = 10;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= 200 * dt;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += 200 * dt;
        }
        if (pos.x < 8) pos.x = 8;
        if (pos.x > ArkanoidGame.WIDTH - 8 - size.x) pos.x = ArkanoidGame.WIDTH - 8 - size.x;
    }
}
