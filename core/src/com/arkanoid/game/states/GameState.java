package com.arkanoid.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

    public static final int MENU = 0;
    public static final int GAME = 1;
    public static final int HIGHSCORES = 2;
    public static final int OPTIONS = 4;

    private static GameState menu;
    private static GameState game;
    private static GameState highScores;
    private static GameState options;
    public static GameState currentState;

    public static void setState(int state) {
        switch (state) {
            case MENU:
                if (menu == null) {
                    menu = new Menu();
                }
                currentState = menu;
                break;
            case GAME:
                game = Game.getInstance();
                Game.getInstance().restart();
                currentState = game;
                break;
            case HIGHSCORES:
                if (highScores == null) {
                    highScores = new HighScores();
                }
                currentState = highScores;
                break;
            case OPTIONS:
                if (options == null) {
                    options = new Options();
                }
                currentState = options;
                break;
        }
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
