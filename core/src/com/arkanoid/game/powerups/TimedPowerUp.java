package com.arkanoid.game.powerups;

import com.arkanoid.game.states.Game;
import com.badlogic.gdx.math.Vector2;

public abstract class TimedPowerUp extends PowerUp {

    protected long duration;
    protected static long timer = 0;

    public TimedPowerUp(Vector2 pos, long duration) {
        super(pos);
        this.duration = duration;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (System.currentTimeMillis() - timer > duration) {
            finish();
            timer = 0;
        }
        if (applied) {
            System.out.println(this.toString() + " " + String.valueOf(System.currentTimeMillis() - timer));
        }
    }

    @Override
    public void apply() {
        super.apply();
        start();
        timer = System.currentTimeMillis();
    }

    public abstract void start();
    public abstract void finish();
}
