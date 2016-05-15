package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class MainMenuScreen implements Screen {
  final Coherence game;
  OrthographicCamera camera;

  /**
   * The screen that is shown when the game is started.
   *
   * @param game The game instance.
   */
  Texture spaceShipImage;
  public MainMenuScreen(final Coherence game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
    spaceShipImage = new Texture(Gdx.files.internal("Spaceship.jpg"));
  }
  /**
  public static Texture backgroundTexture;
  public static Sprite backgroundSprite;
  private SpriteBatch spriteBatch;

  private void loadTextures(){
    backgroundTexture = new Texture(Gdx.files.internal("Mainmenu.png"));
    backgroundSprite = new Sprite(backgroundTexture);
  }
  public void renderBackground(){
    backgroundSprite.draw(spriteBatch);
  }
   */

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
   camera.update();

    SpriteBatch batch = game.batch;
    BitmapFont font = game.font;
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    //renderBackground();
    //backgroundSprite.draw(spriteBatch);
    font.setColor(Color.GOLD);
    font.draw(batch, "Coherence", 330, 250);
    font.draw(batch, "Tap anywhere to begin!", 300, 150);
    batch.draw(spaceShipImage,500,300);
    batch.end();

    if (Gdx.input.isTouched()) {
      game.setScreen(new GameScreen());
      dispose();
    }
  }

  @Override
  public void show() {
    //skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),atlas);
    //ButtonGame = new TextButton("Play", skin/*textButtonSTyle*/);
  }

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
