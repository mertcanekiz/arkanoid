package com.arkanoid.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 pos;
    public Vector2 vel;
    public Vector2 size;
    public Texture img;

    public GameObject() {
        this.pos = new Vector2(0, 0);
        this.vel = new Vector2(0, 0);
        this.size = new Vector2(0, 0);
    }

    public abstract void update(float dt);
    public void render(SpriteBatch sb) {
        sb.draw(img, pos.x, pos.y, size.x, size.y);
    }
    public void dispose() {
        img.dispose();
    }
}
