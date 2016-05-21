package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen extends ScreenAdapter {

  private Stage stage;
  private Coherence game;

  public PauseScreen(Coherence game) {
    this.game = game;
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    Button button = createButton(skin);
    Label titleLabel = createTitleLabel(skin);

    stage = new Stage();
    stage.addActor(button);
    stage.addActor(titleLabel);

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.draw();
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  private Label createTitleLabel(Skin skin) {
    Label label = new Label("Game paused", skin);

    // Double font size
    label.setSize(label.getWidth() * 2, label.getHeight());
    label.setFontScale(2);

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    label.setPosition(
        (width - label.getWidth()) / 2,
        (height - label.getHeight()) / 2 + height / 4
    );

    return label;
  }

  private Button createButton(Skin skin) {
    TextButton button = new TextButton("RESUME GAME", skin, "default");
    int buttonWidth = 300;
    int buttonHeight = 60;
    button.setWidth(buttonWidth);
    button.setHeight(buttonHeight);

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    button.setPosition(
        (width - buttonWidth) / 2,
        (height - buttonHeight) / 2 - height / 4
    );

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float xPos, float yPos) {
        game.setScreen(game.getPreviousScreen());
        dispose();
      }
    });

    return button;
  }
}