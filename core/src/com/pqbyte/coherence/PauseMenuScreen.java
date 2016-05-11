package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Carl on 5/10/2016.
 */
public class PauseMenuScreen implements Screen {

  final Coherence game;
  OrthographicCamera camera;

  public PauseMenuScreen(final Coherence game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    SpriteBatch batch = game.batch;
    BitmapFont font = game.font;

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    font.draw(batch, "The game has been paused", 100, 200);
    font.draw(batch, "Tap anywhere to begin!", 100, 50);
    batch.end();

    if (Gdx.input.isTouched()) {
      game.gameScreen.show();
     // game.setScreen(new GameScreen());
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
