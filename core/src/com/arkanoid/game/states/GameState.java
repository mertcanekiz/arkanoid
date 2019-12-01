package com.arkanoid.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameState {
    void update(float dt);
    void render(SpriteBatch sb);
    void dispose();
}
