package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class IncreasePaddleLength extends InstantPowerUp {
    private static Sound sound = Gdx.audio.newSound(Gdx.files.internal("sfx/enlarge.wav"));
    public IncreasePaddleLength(Vector2 pos) {
        super(pos);
        img = new Sprite(new Texture("images/powerups/enlarge.png"));
    }
    @Override
    public void apply() {
        super.apply();
        Game.getInstance().paddle.enlarge();
        sound.play();
    }
    public void dispose() {
        super.dispose();
    }
}
