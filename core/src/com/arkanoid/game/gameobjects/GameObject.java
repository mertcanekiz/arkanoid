package com.arkanoid.game.gameobjects;

import com.arkanoid.game.ArkanoidGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    protected Vector2 pos;
    protected Vector2 vel;
    protected Vector2 size;
    protected Texture img;

    public GameObject() {
        this.pos = Vector2.Zero;
        this.vel = Vector2.Zero;
        this.size = Vector2.Zero;
    }

    public abstract void update(float dt);
    public void render(SpriteBatch sb) {
        sb.draw(img, pos.x * ArkanoidGame.SCALE, pos.y * ArkanoidGame.SCALE, size.x * ArkanoidGame.SCALE, size.y * ArkanoidGame.SCALE);
    }
    public void dispose() {
        img.dispose();
    }
}
