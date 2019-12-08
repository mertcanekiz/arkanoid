package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class LoseLife extends InstantPowerUp {
    public LoseLife(Vector2 pos) {
        super(pos);
        img = new Sprite(new Texture("images/powerups/1down.png"));
    }

    @Override
    public void apply() {
        super.apply();
        Game.getInstance().paddle.lives--;
        if (Game.getInstance().paddle.lives <= 0) {
            Game.getInstance().die();
        }
    }
}
