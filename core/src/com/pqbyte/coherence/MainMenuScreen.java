package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {
  final Coherence game;
  OrthographicCamera camera;

  /**
   * The screen that is shown when the game is started.
   *
   * @param game The game instance.
   */
  public MainMenuScreen(final Coherence game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();

    SpriteBatch batch = game.batch;
    BitmapFont font = game.font;
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    font.draw(batch, "Coherence", 100, 200);
    font.draw(batch, "Tap anywhere to begin!", 100, 50);
    batch.end();

    if (Gdx.input.isTouched()) {
      game.setScreen(new GameScreen());
      dispose();
    }
  }

  @Override
  public void show() { /* Do nothing */ }

  @Override
  public void resize(int width, int height) { /* Do nothing */ }

  @Override
  public void pause() { /* Do nothing */}

  @Override
  public void resume() { /* Do nothing */}

  @Override
  public void hide() { /* Do nothing */ }

  @Override
  public void dispose() { /* Do nothing */ }
}
