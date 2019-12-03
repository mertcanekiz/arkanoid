package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class IncreasePaddleLength extends InstantPowerUp {
    public IncreasePaddleLength(Vector2 pos) {
        super(pos);
        img = new Texture("powerups/R.png");
    }
    @Override
    public void apply() {
        Game.getInstance().paddle.enlarge();
    }
}
