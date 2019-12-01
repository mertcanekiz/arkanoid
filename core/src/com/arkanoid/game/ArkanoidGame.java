package com.arkanoid.game;

import com.arkanoid.game.gameobjects.Ball;
import com.arkanoid.game.gameobjects.Paddle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ArkanoidGame extends Game {

	public static final int SCREEN_WIDTH = 256;
	public static final int SCREEN_HEIGHT = 240;
    public static final int WIDTH = 192;
    public static final int HEIGHT = 232;
    public static final int SCALE = 1;

    private static ArkanoidGame instance = null;

    public static ArkanoidGame getInstance() {
        if (instance == null) instance = new ArkanoidGame();
        return instance;
    }

    public SpriteBatch batch;
    public Texture img;
    public Paddle paddle;
	public Level level;
	public Ball ball;

	@Override
	public void create () {
		batch = new SpriteBatch();
		paddle = new Paddle();
		level = new Level("level-2.txt");
		ball = new Ball(new Vector2(100, 50));
	}

	@Override
	public void render () {
		paddle.update(Gdx.graphics.getDeltaTime());
		ball.update(Gdx.graphics.getDeltaTime());
		level.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		level.render(batch);
		paddle.render(batch);
		ball.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		paddle.dispose();
		level.dispose();
		ball.dispose();
	}
}
