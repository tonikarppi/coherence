package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import javafx.scene.control.Tab;

public class Hud {

  private static final float VIEWPORT_WIDTH = 1100;

  private Stage stage;
  private Touchpad movementController;
  private Touchpad shooterController;

  /**
   * The overlay displayed over the game.
   *
   * @param batch The drawing batch.
   */
  public Hud(Batch batch) {
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    Viewport viewport = new FitViewport(
        VIEWPORT_WIDTH,
        VIEWPORT_WIDTH * (screenHeight / screenWidth)
    );

    stage = new Stage(viewport, batch);
    movementController = makeController();
    shooterController = makeController();

    Table bottomTable = new Table();
    bottomTable.setFillParent(true);
    bottomTable.bottom().padBottom(10).padLeft(30).padRight(30);
    bottomTable.add(movementController);
    bottomTable.add().expandX();
    bottomTable.add(shooterController);
    bottomTable.setDebug(Constants.isDebug());
    stage.addActor(bottomTable);
  }

  private Touchpad makeController() {
    Skin skin = new Skin();
    skin.add("background", new Texture("touchBackground.png"));
    skin.add("knob", new Texture("touchKnob.png"));
    Drawable background = skin.getDrawable("background");
    Drawable knob = skin.getDrawable("knob");
    Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
    style.background = background;
    style.knob = knob;

    return new Touchpad(10, style);
  }

  public Stage getStage() {
    return stage;
  }

  public Touchpad getMovementController() {
    return movementController;
  }

  public Touchpad getShooterController() {
    return shooterController;
  }
}
