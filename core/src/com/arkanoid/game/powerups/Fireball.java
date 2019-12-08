package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Fireball extends TimedPowerUp {

    public Fireball(Vector2 pos) {
        super(pos, 10000);
        img = new Sprite(new Texture("images/powerups/fireball.png"));
    }

    @Override
    public void start() {
        Game.getInstance().ball.setFireball(true);
    }

    @Override
    public void finish() {
        Game.getInstance().ball.setFireball(false);
    }
}
