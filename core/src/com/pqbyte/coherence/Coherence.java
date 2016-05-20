package com.pqbyte.coherence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coherence extends Game {
  public SpriteBatch batch;
  public BitmapFont font;
  private Screen previousScreen;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    setScreen(new MainMenuScreen(this));
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }

  @Override
  public void pause() {
    previousScreen = getScreen();
  }

  public void resume() {
    if (previousScreen instanceof GameScreen) {
      setScreen(new PauseMenuScreen(this));
    }
  }

  public Screen getPreviousScreen() {
    return previousScreen;
  }
}

