package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

/**
 * Created by Carl on 5/9/2016.
 */
public class Coherence2 extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

