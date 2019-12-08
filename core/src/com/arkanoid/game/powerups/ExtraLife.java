package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ExtraLife extends InstantPowerUp {
    public ExtraLife(Vector2 pos) {
        super(pos);
        img = new Sprite(new Texture("images/powerups/1up.png"));
    }

    @Override
    public void apply() {
        super.apply();
        Game.getInstance().paddle.lives++;
    }
}
