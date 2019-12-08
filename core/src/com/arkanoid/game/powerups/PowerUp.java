package com.arkanoid.game.powerups;

import com.arkanoid.game.Util;
import com.arkanoid.game.gameobjects.Paddle;
import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class PowerUp {

    private static String[] powerupTypes = {
      "IncreasePaddleLength",
      "DecreaseBallSpeed",
      "IncreaseBallSpeed",
      "Fireball",
      "Softball",
    };

    protected Sprite img;
    public Vector2 pos;
    protected Vector2 size = new Vector2(32, 32);
    protected Vector2 vel = new Vector2(0, -30);
    protected boolean applied = false;

    private static long fireballTimer = 0;
    private static long softballTimer = 0;
    // private static long shieldTimer = 0;

    public PowerUp(Vector2 pos) {
        this.pos = pos;
    }

    public void apply() {
        applied = true;
        System.out.println("apply");
    }

    public void dispose() {
        img.getTexture().dispose();
    }

    public void update(float dt) {
        if (applied) return;
        if (!applied) {
            pos.x += vel.x * dt;
            pos.y += vel.y * dt;
            Paddle paddle = Game.getInstance().paddle;
            Rectangle rect = new Rectangle(pos.x, pos.y, size.x, size.y);
            Rectangle paddleRect = new Rectangle(paddle.pos.x, paddle.pos.y, paddle.size.x, paddle.size.y);
            if (rect.overlaps(paddleRect)) {
                apply();
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (!applied) {
            img.setSize(32, 32);
            img.setScale(0.5f);
            img.setPosition(pos.x, pos.y);
            img.draw(sb);
        }
    }

    public static PowerUp randomPowerUp(Vector2 pos) {
        int index = (int) Util.random(0, powerupTypes.length);
        String type = powerupTypes[index];
        switch (type) {
            case "IncreaseBallSpeed":
                return new IncreaseBallSpeed(pos);
            case "DecreaseBallSpeed":
                return new DecreaseBallSpeed(pos);
            case "IncreasePaddleLength":
                return new IncreasePaddleLength(pos);
            case "Fireball":
                return new Fireball(pos);
            case "Softball":
                return new Softball(pos);
        }
        return null;
    }
}
