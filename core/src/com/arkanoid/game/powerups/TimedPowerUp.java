package com.arkanoid.game.powerups;

import com.badlogic.gdx.math.Vector2;

public abstract class TimedPowerUp extends PowerUp {
    protected long startTime;
    protected long duration;
    public TimedPowerUp(long duration) {
        super(new Vector2(0, 0));
        this.duration = duration;
    }

    @Override
    public void apply() {
        this.startTime = System.currentTimeMillis();
    }
}
