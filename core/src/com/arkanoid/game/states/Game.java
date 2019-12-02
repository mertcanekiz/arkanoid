package com.arkanoid.game.states;

import com.arkanoid.game.ArkanoidGame;
import com.arkanoid.game.Level;
import com.arkanoid.game.gameobjects.Ball;
import com.arkanoid.game.gameobjects.Paddle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Game extends GameState {
    private static Game instance = null;

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    public Level level;
    public Paddle paddle;
    public Ball ball;

    public Game() {
        level = new Level("level-1.txt");
        paddle = new Paddle();
        ball = new Ball(new Vector2(50, 50));
    }

//    @Override
//    public void init()
//    {
//
//    }

    @Override
    public void update(float dt) {
        paddle.update(dt);
        level.update(dt);
        ball.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        level.render(sb);
        paddle.render(sb);
        ball.render(sb);
    }

    @Override
    public void dispose() {
        paddle.dispose();
        ball.dispose();
        level.dispose();
    }
}
