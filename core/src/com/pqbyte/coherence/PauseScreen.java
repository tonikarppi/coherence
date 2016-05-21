package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen extends ScreenAdapter {

  private Stage stage;
  private Coherence game;

  /**
   * The screen that comes up when game is paused.
   *
   * @param game The game instance.
   */
  public PauseScreen(Coherence game) {
    this.game = game;
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    stage = new Stage();
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    Label titleLabel = createTitleLabel(skin);
    table.add(titleLabel).spaceBottom(160);
    table.row();

    int buttonWidth = 300;
    int buttonHeight = 60;

    Button resumeButton = createResumeButton(skin);
    table.add(resumeButton).width(buttonWidth).height(buttonHeight);
    table.row();

    Button mainButton = createMainButton(skin);
    table.add(mainButton).width(buttonWidth).height(buttonHeight);
    table.row();

    Button exitButton = createExitButton(skin);
    table.add(exitButton).width(buttonWidth).height(buttonHeight);

    table.setDebug(Constants.isDebug());

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act(delta);
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

    return label;
  }

  private Button createResumeButton(Skin skin) {
    TextButton button = new TextButton("RESUME GAME", skin, "default");

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float xPos, float yPos) {
        game.setScreen(game.getPreviousScreen());
        Gdx.input.setInputProcessor(game.getPreviousInputProcessor());
        dispose();
      }
    });

    return button;
  }

  private Button createMainButton(Skin skin) {
    TextButton button = new TextButton("RETURN TO MAIN MENU", skin, "default");

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float xPos, float yPos) {
        game.setScreen(new MenuScreen(game));
        //Gdx.input.setInputProcessor(game.getPreviousInputProcessor());
        dispose();
      }
    });

    return button;
  }

  private Button createExitButton(Skin skin) {
    TextButton button = new TextButton("EXIT THE GAME", skin, "default");

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float xPos, float yPos) {
        Gdx.app.exit();
        dispose();
      }
    });

    return button;
  }
}
