package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends ScreenAdapter {

  private Stage stage;
  private Coherence game;

  /**
   * The main menu screen.
   *
   * @param game The game instance.
   */
  public MenuScreen(Coherence game) {
    this.game = game;

    Table table = new Table();
    table.setFillParent(true);
    stage = new Stage();
    stage.addActor(table);

    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    Label titleLabel = createTitleLabel(skin);
    table.add(titleLabel).spaceBottom(20);
    table.row();

    int imageHeight = 300;
    int imageWidth = (int) (imageHeight * 1.34);
    Image image = new Image(new Texture(Gdx.files.internal("Spaceship.jpg")));
    table.add(image).width(imageWidth).height(imageHeight).spaceBottom(100);
    table.row();

    Button button = createButton(skin);
    table.add(button).width(300).height(60);

    table.setDebug(Constants.isDebug());
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
    Label label = new Label("COHERENCE", skin);

    // Double font size
    label.setSize(label.getWidth() * 2, label.getHeight());
    label.setFontScale(2);

    return label;
  }

  private Button createButton(Skin skin) {
    TextButton button = new TextButton("START GAME", skin);
    int buttonWidth = 300;
    int buttonHeight = 60;
    button.setWidth(buttonWidth);
    button.setHeight(buttonHeight);

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float xpos, float ypos) {
        game.setScreen(new GameScreen(game));
        dispose();
      }
    });

    return button;
  }
}
