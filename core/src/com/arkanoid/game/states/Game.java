package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.arkanoid.game.Level;
import com.arkanoid.game.gameobjects.Ball;
import com.arkanoid.game.gameobjects.Block;
import com.arkanoid.game.gameobjects.Paddle;
import com.arkanoid.game.powerups.PowerUp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Game extends GameState {
    private static Game instance = null;

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    public ArrayList<Level> levels = new ArrayList<>();{

    };
    public int currentLevel = 1;
    public Paddle paddle;
    public Ball ball;
    public ArrayList<PowerUp> powerups = new ArrayList<>();
    public PowerUp toBeRemoved;

    private boolean paused = false;

    public Game() {
        paddle = new Paddle();
        ball = new Ball(paddle);

    }

    public void restart() {
        reset();
        paddle.lives = 3;
        levels = new ArrayList<>();
        levels.add(new Level("levels/level-1.txt"));
        levels.add(new Level("levels/level-2.txt"));
        levels.add(new Level("levels/level-3.txt"));
        powerups = new ArrayList<>();
        currentLevel = 1;
        paused = false;
    }

    public void reset() {
        paddle.reset();
        ball.reset();
    }

    public void die() {
        if (paddle.lives > 1) {
            paddle.lives--;
            reset();
        } else {
            paused = true;
            GameState.setState(GameState.HIGHSCORES);
            Gdx.input.getTextInput(new HighScores(), "New High Score", "", "");
        }
    }

    @Override
    public void update(float dt) {
        if (!paused) {
            if (toBeRemoved != null) {
                toBeRemoved.dispose();
                powerups.remove(toBeRemoved);
            }
            paddle.update(dt);
            levels.get(currentLevel).update(dt);
            ball.update(dt);
            for (int i = powerups.size() - 1; i >= 0; i--) {
                powerups.get(i).update(dt);
                if (powerups.get(i).pos.y < 0) {
                    powerups.remove(i);
                }
            }

            int blocksLeft = (int) levels.get(currentLevel).blocks.stream().filter(block -> block.type != Block.BlockType.UNBREAKABLE).count();
            if (blocksLeft == 0) {
                currentLevel++;
                reset();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                paused = true;
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                paused = false;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        levels.get(currentLevel).render(sb);
        paddle.render(sb);
        ball.render(sb);
        for (PowerUp powerup : powerups) {
            powerup.render(sb);
        }
        Arkanoid.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        Arkanoid.font.setColor(new Color(0xb53121ff));
        Arkanoid.font.draw(sb, "HIGH", Arkanoid.WIDTH + 5, Arkanoid.HEIGHT - 10);
        Arkanoid.font.draw(sb, "SCORE", Arkanoid.SCREEN_WIDTH - Arkanoid.charWidth * 6 + 2, Arkanoid.HEIGHT - 10 - Arkanoid.charHeight);
        Arkanoid.font.setColor(Color.WHITE);
        Arkanoid.font.draw(sb, "50000", Arkanoid.SCREEN_WIDTH - Arkanoid.charWidth * 6 + 2, Arkanoid.HEIGHT - 10 - Arkanoid.charHeight * 2);

        Arkanoid.font.setColor(new Color(0xb53121ff));
        Arkanoid.font.draw(sb, "1UP", Arkanoid.WIDTH + Arkanoid.charWidth / 2, Arkanoid.HEIGHT - 50);
        Arkanoid.font.setColor(Color.WHITE);
        String score = String.valueOf(paddle.score);
        Arkanoid.font.draw(sb, score, Arkanoid.SCREEN_WIDTH - Arkanoid.charWidth * (score.length() + 1), Arkanoid.HEIGHT - 50 - Arkanoid.charHeight);

        for (int i = 0; i < paddle.lives; i++) {
            float x = (i % 3) * (Paddle.WIDTH / 2f + 2);
            float y = (i / 3) * Paddle.HEIGHT / 2f;
            sb.draw(Paddle.img_normal, Arkanoid.WIDTH + 5 + x, Arkanoid.HEIGHT - 120 + y, Paddle.WIDTH / 2f, Paddle.HEIGHT / 2f);
        }

        Arkanoid.font.setColor(new Color(0xb53121ff));
        Arkanoid.font.draw(sb, "ROUND", Arkanoid.SCREEN_WIDTH - Arkanoid.charWidth * 6, Arkanoid.HEIGHT - 180);
        Arkanoid.font.setColor(Color.WHITE);
        Arkanoid.font.draw(sb, String.valueOf(currentLevel + 1), Arkanoid.SCREEN_WIDTH - Arkanoid.charWidth  - 7, Arkanoid.HEIGHT - 180 - Arkanoid.charHeight);

        if (paused) {
            Arkanoid.font.draw(sb, "PAUSED", (Arkanoid.WIDTH - Arkanoid.charWidth * 6) / 2.0f, 100);
        }
    }

    @Override
    public void dispose() {
        paddle.dispose();
        ball.dispose();
        for (Level level : levels) {
            level.dispose();
        }
    }
}
