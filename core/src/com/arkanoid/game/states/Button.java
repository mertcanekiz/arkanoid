package com.arkanoid.game.states;

import com.arkanoid.game.ArkanoidGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Button {

    public static final int BUTTON_NORMAL = 0;
    public static final int BUTTON_HOVER = 1;
    public static final int BUTTON_PRESS = 2;

    public static final int WIDTH = 100;
    public static final int HEIGHT = 24;

    private Texture images[] = new Texture[3];
    public int state = BUTTON_NORMAL;
    public Vector2 pos;
    public int index;

    public Button(int index, Vector2 pos) {
        this.index = index;
        for (int i = 0; i < 3; i++) {
            images[i] = new Texture("menu/button_" + index + "_" + i + ".png");
        }
        this.pos = pos;
    }

    public void render(SpriteBatch sb) {
        sb.draw(images[state], pos.x * ArkanoidGame.SCALE, pos.y * ArkanoidGame.SCALE, WIDTH * ArkanoidGame.SCALE, HEIGHT * ArkanoidGame.SCALE);
    }

    public void dispose() {
        for (Texture img : images) {
            img.dispose();
        }
    }
}
