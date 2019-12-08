package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class Options extends GameState {

    public static boolean soundEnabled = true;

    private static Map<String,String> options = new HashMap<>();

    public Options() {
        FileHandle handle = Gdx.files.internal("options.txt");
        String mapFile = handle.readString();
        String[] lines = mapFile.split("\\r?\\n|\\r");
        for (int i = 0; i < lines.length; i++) {
            String[] tokens = lines[i].split("=");
            options.put(tokens[0], tokens[1]);
        }
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "sound":
                    soundEnabled = option.getValue().equals("true");
                    break;
            }
        }
    }

    public static void save() {
        FileHandle handle = Gdx.files.local("options.txt");
        handle.delete();
        options.forEach((option, value) -> {
            handle.writeString(option + "=" + value + "\n", true);
        });
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        float y = Arkanoid.HEIGHT - 100;
        int i = 0;
        for (Map.Entry<String, String> entry : options.entrySet()) {
            String option = entry.getKey();
            boolean value = entry.getValue().equals("true");

            Arkanoid.font.draw(sb, option, 10, y);
            Arkanoid.font.draw(sb, value ? "Enabled" : "Disabled", 130, y);
            y -= Arkanoid.font.getLineHeight();
        }
    }

    @Override
    public void dispose() {

    }
}
