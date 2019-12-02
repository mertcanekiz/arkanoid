package com.arkanoid.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

    public static final int MENU = 0;
    public static final int GAME = 1;

    private static GameState menu;
    private static GameState game;
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
                currentState = game;
                break;
        }
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
