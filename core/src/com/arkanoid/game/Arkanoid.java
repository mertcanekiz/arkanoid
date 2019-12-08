package com.arkanoid.game;

import com.arkanoid.game.states.GameState;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Arkanoid extends Game {

	public static final int SCREEN_WIDTH = 256;
	public static final int SCREEN_HEIGHT = 240;
    public static final int WIDTH = 192;
    public static final int HEIGHT = 232;
    public static final float SCALE = 2.0f;

	public static BitmapFont font;
	public static float charWidth;
	public static float charHeight;

    private static Arkanoid instance = null;

    public static Arkanoid getInstance() {
        if (instance == null) {
        	instance = new Arkanoid();

		}
        return instance;
    }

    public SpriteBatch batch;
    public OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		GameState.setState(GameState.MENU);
		font = new BitmapFont(Gdx.files.internal("fonts/retro.fnt"), Gdx.files.internal("fonts/retro.png"), false);
		charWidth = font.getData().getGlyph('m').width * font.getScaleX();
		charHeight = font.getData().getGlyph('M').height * font.getScaleY();
	}

	public static float calculateStringWidth(String text) {
		float result = 0.0f;
		for (int i = 0; i < text.length(); i++) {
			result += font.getData().getGlyph(text.charAt(i)).width * font.getScaleX();
		}
		return result;
	}

	@Override
	public void render () {
		GameState.currentState.update(Gdx.graphics.getDeltaTime());
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		GameState.currentState.render(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
