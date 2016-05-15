package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    SpriteBatch batch = game.batch;
    BitmapFont font = game.font;

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    font.draw(batch, "The game has been paused", 300, 350);
    font.draw(batch, "Tap anywhere to resume!", 300, 250);
    font.draw(batch, "Press Enter to return to main menu", 250, 200);
    font.draw(batch, "Press Delete to return to exit", 250, 150);
    batch.end();

    if (Gdx.input.isTouched()) {
     //ame.GameScreen.show();
     game.setScreen(new GameScreen(game));
      dispose();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
      game.setScreen(new MainMenuScreen(game));
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DEL)) {
      Gdx.app.exit();
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
