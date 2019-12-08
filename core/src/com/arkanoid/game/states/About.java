package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class About extends GameState {

    private String developedBy = "Developed By";
    private String name = "Omer Afsin Pirim";
    private String number = "20150702048";
    private String email = "test@test.com";

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        float x, y;

        x = (Arkanoid.SCREEN_WIDTH - Arkanoid.calculateStringWidth(developedBy))/2.0f;
        y = Arkanoid.SCREEN_HEIGHT - 100;
        Arkanoid.font.setColor(new Color(0xb53121ff));
        Arkanoid.font.draw(sb, developedBy, x, y);

        x = (Arkanoid.SCREEN_WIDTH - Arkanoid.calculateStringWidth(name))/2.0f;
        y -= Arkanoid.font.getLineHeight();
        Arkanoid.font.setColor(Color.WHITE);
        Arkanoid.font.draw(sb, name, x, y);

        x = (Arkanoid.SCREEN_WIDTH - Arkanoid.calculateStringWidth(number))/2.0f;
        y -= Arkanoid.font.getLineHeight();
        Arkanoid.font.draw(sb, number, x, y);

        x = (Arkanoid.SCREEN_WIDTH - Arkanoid.calculateStringWidth(email))/2.0f;
        y -= Arkanoid.font.getLineHeight();
        Arkanoid.font.draw(sb, email, x, y);
    }

    @Override
    public void dispose() {

    }
}
