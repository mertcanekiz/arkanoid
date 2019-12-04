package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class IncreasePaddleLength extends InstantPowerUp {
    private Sound sound;
    public IncreasePaddleLength(Vector2 pos) {
        super(pos);
        img = new Texture("images/powerups/R.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sfx/enlarge.wav"));
    }
    @Override
    public void apply() {
        Game.getInstance().paddle.enlarge();
        sound.play();
    }
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
