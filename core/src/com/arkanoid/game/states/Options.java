package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class Options extends GameState {

    public static boolean soundEnabled = false;
    public static String difficulty = "Novice";

    public static String[] options = new String[]{
            "Difficulty",
            "Sound",
    };

    public static String[] difficulties = new String[]{
            "Novice",
            "Intermediate",
            "Advanced"
    };

    private int selectIndex = 0;
    private int difficultyIndex = 0;

    public Options() {
        FileHandle handle = Gdx.files.internal("options.txt");
        String mapFile = handle.readString();
        String[] lines = mapFile.split("\\r?\\n|\\r");
        difficulty = lines[0].split("=")[1];
        switch (difficulty) {
            case "Novice":
                difficultyIndex = 0;
                break;
            case "Intermediate":
                difficultyIndex = 1;
                break;
            case "Advanced":
                difficultyIndex = 2;
                break;
        }
        soundEnabled = lines[1].split("=")[1].equals("true");
    }

    public static void save() {
        FileHandle handle = Gdx.files.local("options.txt");
        handle.delete();
        handle.writeString("Difficulty=" + difficulty + "\n", true);
        handle.writeString("Sound=" + String.valueOf(soundEnabled) + "\n", true);
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameState.setState(GameState.MENU);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectIndex++;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectIndex--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectIndex) {
                case 0:
                    difficultyIndex++;
                    if (difficultyIndex > 2) difficultyIndex = 0;
                    difficulty = difficulties[difficultyIndex];
                    break;
                case 1:
                    soundEnabled = !soundEnabled;
                    break;
            }
            System.out.println(soundEnabled);
            save();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        float y = Arkanoid.HEIGHT - 100;

        for (int i = 0; i < options.length; i++) {
            Arkanoid.font.setColor(Color.WHITE);
            Arkanoid.font.draw(sb, options[i], 10, y);
            if (selectIndex == i) {
                Arkanoid.font.setColor(new Color(0xb53121ff));
            } else {
                Arkanoid.font.setColor(Color.WHITE);
            }
            String value = "";
            if (i == 0) value = difficulty;
            if (i == 1) value = String.valueOf(soundEnabled);
            Arkanoid.font.draw(sb, value, 130, y);
            y -= Arkanoid.font.getLineHeight();
        }
    }

    @Override
    public void dispose() {

    }
}
