package com.arkanoid.game.states;

import com.arkanoid.game.Arkanoid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class HighScores extends GameState implements Input.TextInputListener {

    private static Map<String, Integer> highScores = new LinkedHashMap<>();

    public HighScores() {
        load();
        sort();
    }

    private void load() {
        FileHandle handle = Gdx.files.internal("highscores.txt");
        String mapFile = handle.readString();
        String[] lines = mapFile.split("\\r?\\n|\\r");
        for (int i = 0; i < lines.length; i++) {
            String[] tokens = lines[i].split(",");
            highScores.put(tokens[0], Integer.parseInt(tokens[1]));
        }
    }

    private void sort() {
        List<Entry<String, Integer>> list = new ArrayList<>(highScores.entrySet());
        list.sort(Entry.comparingByValue());
        highScores = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list) {
            highScores.put(entry.getKey(), entry.getValue());
        }
    }

    public static void save() {
        FileHandle handle = Gdx.files.local("highscores.txt");
        handle.delete();
        highScores.forEach((name, score) -> {
            handle.writeString(name + "," + String.valueOf(score) + "\n", true);
        });
    }

    @Override
    public void input(String text) {
        highScores.put(text, Game.getInstance().paddle.score);
        sort();
        save();
    }

    @Override
    public void canceled() {
        System.out.println("User canceled the operation");
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        float y = Arkanoid.HEIGHT - 30;

        Arkanoid.font.setColor(new Color(0xb53121ff));
        Arkanoid.font.draw(sb, "High Scores", 10, y);
        Arkanoid.font.setColor(Color.WHITE);
        y -= Arkanoid.font.getLineHeight();
        int i = 0;
        for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
            if (i++ > 9) break;
            String name = entry.getKey();
            String score = String.valueOf(entry.getValue());

            // Trim strings if they are too long
            name = name.length() > 10 ? name.substring(0, 10) : name;
            score = score.length() > 10 ? score.substring(0, 10) : score;

            Arkanoid.font.draw(sb, name, 10, y);
            Arkanoid.font.draw(sb, score, 130, y);
            y -= Arkanoid.font.getLineHeight();
        }
    }

    @Override
    public void dispose() {

    }
}
