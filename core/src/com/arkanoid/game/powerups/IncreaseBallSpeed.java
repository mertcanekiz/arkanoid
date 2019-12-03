package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class IncreaseBallSpeed extends InstantPowerUp {
    public IncreaseBallSpeed(Vector2 pos) {
        super(pos);
        img = new Texture("images/powerups/L.png");
    }

    @Override
    public void apply() {
        Game.getInstance().ball.vel.x *= 1.25f;
        Game.getInstance().ball.vel.y *= 1.25f;
    }
}
