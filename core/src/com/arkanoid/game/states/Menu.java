package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu extends GameState {

    private Button newGameBtn = new Button(0, new Vector2(100, 100));
    private Button buttons[] = new Button[] {
      new Button(0, new Vector2((Arkanoid.SCREEN_WIDTH - 100) / 2, Arkanoid.SCREEN_HEIGHT - 32)),
      new Button(1, new Vector2((Arkanoid.SCREEN_WIDTH - 100) / 2, Arkanoid.SCREEN_HEIGHT - 64))
    };

    @Override
    public void update(float dt) {
        float mouseX = Gdx.input.getX() / Arkanoid.SCALE;
        float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) / Arkanoid.SCALE;
        for (Button button : buttons) {
            Rectangle buttonRect = new Rectangle(button.pos.x, button.pos.y, 100, 24);
            if (buttonRect.contains(new Vector2(mouseX, mouseY))) {
                button.state = Button.BUTTON_HOVER;
                if (Gdx.input.isTouched()) {
                    button.state = Button.BUTTON_PRESS;
                    switch (button.index) {
                        case 0:
                            GameState.setState(GameState.GAME);
                            break;
                    }
                }
            } else {
                button.state = Button.BUTTON_NORMAL;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for (Button button: buttons) {
            button.render(sb);
        }
    }

    @Override
    public void dispose() {
        for (Button button: buttons) {
            button.dispose();
        }
    }
}
