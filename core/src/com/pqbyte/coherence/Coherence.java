package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class Coherence extends Game {
  public SpriteBatch batch;
  public BitmapFont font;
  GameScreen gameScreen = new GameScreen();
  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    this.setScreen(new MainMenuScreen(this));

  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }
  @Override
  public void pause() {

    this.setScreen(new PauseMenuScreen(this));

  }
  public void resume() {
  }
}

