package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DecreaseBallSpeed extends InstantPowerUp {
    public DecreaseBallSpeed(Vector2 pos) {
        super(pos);
        img = new Texture("powerups/S.png");
    }

    @Override
    public void apply() {
        Game.getInstance().ball.vel.x *= 0.75f;
        Game.getInstance().ball.vel.y *= 0.75f;
    }
}
