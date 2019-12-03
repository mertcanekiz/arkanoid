package com.arkanoid.game.powerups;

import com.arkanoid.game.gameobjects.Paddle;
import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class PowerUp {
    protected Texture img;
    protected Vector2 pos;
    protected Vector2 size = new Vector2(22, 12);
    protected Vector2 vel = new Vector2(0, -30);

    public PowerUp(Vector2 pos) {
        this.pos = pos;
    }

    public boolean applied = false;

    public abstract void apply();

    public void update(float dt) {
        pos.x += vel.x * dt;
        pos.y += vel.y * dt;
        Paddle paddle = Game.getInstance().paddle;
        Rectangle rect = new Rectangle(pos.x, pos.y, size.x, size.y);
        Rectangle paddleRect = new Rectangle(paddle.pos.x, paddle.pos.y, paddle.size.x, paddle.size.y);
        if (rect.overlaps(paddleRect)) {
            apply();
            Game.getInstance().toBeRemoved = this;
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(img, pos.x, pos.y, size.x, size.y);
    }
}
