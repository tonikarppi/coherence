package com.pqbyte.coherence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coherence extends Game {
  public SpriteBatch batch;
  public BitmapFont font;
  private Screen previousScreen;
  private InputProcessor previousInputProcessor;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    setScreen(new MenuScreen(this));
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }

  @Override
  public void pause() {
    previousScreen = getScreen();
    previousInputProcessor = Gdx.input.getInputProcessor();
  }

  @Override
  public void resume() {
    if (previousScreen instanceof GameScreen) {
      setScreen(new PauseScreen(this));
    }
  }

  public Screen getPreviousScreen() {
    return previousScreen;
  }

  public InputProcessor getPreviousInputProcessor() {
    return previousInputProcessor;
  }
}

