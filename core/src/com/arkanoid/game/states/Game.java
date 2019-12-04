package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.arkanoid.game.Level;
import com.arkanoid.game.gameobjects.Ball;
import com.arkanoid.game.gameobjects.Block;
import com.arkanoid.game.gameobjects.Paddle;
import com.arkanoid.game.powerups.DecreaseBallSpeed;
import com.arkanoid.game.powerups.IncreaseBallSpeed;
import com.arkanoid.game.powerups.IncreasePaddleLength;
import com.arkanoid.game.powerups.PowerUp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Game extends GameState {
    private static Game instance = null;


    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    public Level[] levels = {
            new Level("levels/level-1.txt"),
            new Level("levels/level-2.txt"),
            new Level("levels/level-3.txt"),
    };
    public int currentLevel = 0;
    public Paddle paddle;
    public Ball ball;
    public ArrayList<PowerUp> powerups = new ArrayList<>();
    public PowerUp toBeRemoved;

    public Game() {
        paddle = new Paddle();
        ball = new Ball(new Vector2(50, 50));
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
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        if (toBeRemoved != null) {
            toBeRemoved.dispose();
            powerups.remove(toBeRemoved);
        }
        paddle.update(dt);
        levels[currentLevel].update(dt);
        ball.update(dt);
        for (PowerUp powerup : powerups) {
            powerup.update(dt);
        }

        int blocksLeft = (int) levels[currentLevel].blocks.stream().filter(block -> block.type != Block.BlockType.UNBREAKABLE).count();
        if (blocksLeft == 0) {
            currentLevel++;
            reset();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        levels[currentLevel].render(sb);
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
