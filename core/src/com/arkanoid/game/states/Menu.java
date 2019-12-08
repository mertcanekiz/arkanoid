package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu extends GameState {

    private int selectIndex = 0;
    private String[] buttons = new String[]{
            "New Game",
            "Options",
            "High Scores",
            "Help",
            "About",
            "Exit"
    };

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectIndex++;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectIndex--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectIndex) {
                case 0:
                    GameState.setState(GameState.GAME);
                    break;
                case 1:
                    GameState.setState(GameState.OPTIONS);
                    break;
                case 2:
                    GameState.setState(GameState.HIGHSCORES);
                    break;
                case 4:
                    GameState.setState(GameState.ABOUT);
                    break;
                case 5:
                    Gdx.app.exit();
                    break;
            }
        }
        if (selectIndex < 0) selectIndex = buttons.length -1;
        if (selectIndex >= buttons.length) selectIndex = 0;
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == selectIndex) {
                Arkanoid.font.setColor(new Color(0xb53121ff));
            } else {
                Arkanoid.font.setColor(Color.WHITE);
            }
            String buttonText = buttons[i];
            Arkanoid.font.draw(sb, buttonText, (Arkanoid.SCREEN_WIDTH - Arkanoid.calculateStringWidth(buttonText)) / 2f, Arkanoid.SCREEN_HEIGHT / 2f - i * Arkanoid.charHeight * 1.4f);
        }
    }

    @Override
    public void dispose() {
    }
}
