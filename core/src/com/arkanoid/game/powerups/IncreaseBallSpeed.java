package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class IncreaseBallSpeed extends InstantPowerUp {
    public IncreaseBallSpeed(Vector2 pos) {
        super(pos);
        img = new Sprite(new Texture("images/powerups/fastball.png"));
    }

    @Override
    public void apply() {
        super.apply();
        Game.getInstance().ball.vel.x *= 1.25f;
        Game.getInstance().ball.vel.y *= 1.25f;
    }
}
