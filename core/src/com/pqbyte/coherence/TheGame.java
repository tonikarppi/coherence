package com.pqbyte.coherence;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TheGame extends ApplicationAdapter {

  private static final float WORLD_WIDTH = 160;
  private static final float WORLD_HEIGHT = 90;
  private static final float VIEWPORT_WIDTH = 30;
  private static final float PLAYER_SIZE = 2;

  private SpriteBatch batch;
  private Sprite wallpaper;
  private Sprite player;
  private OrthographicCamera camera;

  @Override
  public void create() {
    wallpaper = new Sprite(new Texture(Gdx.files.internal("wallpaper.jpg")));
    wallpaper.setPosition(0, 0);
    wallpaper.setSize(WORLD_WIDTH, WORLD_HEIGHT);

    player = new Sprite(new Texture(Gdx.files.internal("cube128.png")));
    player.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    player.setColor(Color.BLUE);
    player.setSize(PLAYER_SIZE, PLAYER_SIZE);
    player.setPosition(0, 0);

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    camera = new OrthographicCamera(
        VIEWPORT_WIDTH,
        VIEWPORT_WIDTH * (screenHeight / screenWidth)
    );

    batch = new SpriteBatch();
  }

  @Override
  public void dispose() {
    wallpaper.getTexture().dispose();
    player.getTexture().dispose();
    batch.dispose();
  }

  @Override
  public void render() {
    handleInput(1);
    camera.position.x = player.getX() + player.getWidth() / 2f;
    camera.position.y = player.getY() + player.getHeight() / 2f;
    camera.update();

    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    wallpaper.draw(batch);
    player.draw(batch);
    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    Gdx.app.log("INFO", "Resize called: width = " + width + ", height = " + height);
    camera.viewportWidth = VIEWPORT_WIDTH;
    camera.viewportHeight = VIEWPORT_WIDTH * ((float) height / (float) width);
    camera.update();
  }

  private void handleInput(float speed) {

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
        && player.getX() > 0) {
      player.translateX(-speed);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        && player.getX() + player.getWidth() < WORLD_WIDTH) {
      player.translateX(speed);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.UP)
        && player.getY() + player.getHeight() < WORLD_HEIGHT) {
      player.translateY(speed);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)
        && player.getY() > 0) {
      player.translateY(-speed);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.X)) {
      Gdx.app.log("BUTTON", "Player position: (" + player.getX() + ", " + player.getY() + ")");
    }
  }
}
