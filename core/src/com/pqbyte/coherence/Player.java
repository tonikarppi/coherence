package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The player being controlled.
 */
public class Player extends Actor {
  private static final int PLAYER_SIZE = 2;
  private static final float STEP_SIZE = 0.5f;

  private boolean goingLeft = false;
  private boolean goingRight = false;
  private boolean goingUp = false;
  private boolean goingDown = false;

  private Sprite sprite;

  public Player(Texture texture, float startX, float startY) {
    sprite = new Sprite(texture);
    setBounds(startX, startY, PLAYER_SIZE, PLAYER_SIZE);
  }

  @Override
  public void act(float delta) {
    float horizontal = 0;
    float vertical = 0;

    if (goingLeft && getX() > 0) {
      horizontal -= STEP_SIZE;
    }
    if (goingRight && getX() + getWidth() < Constants.WORLD_WIDTH) {
      horizontal += STEP_SIZE;
    }
    if (goingUp && getY() + getHeight() < Constants.WORLD_HEIGHT) {
      vertical += STEP_SIZE;
    }
    if (goingDown && getY() > 0) {
      vertical -= STEP_SIZE;
    }

    setPosition(getX() + horizontal, getY() + vertical);

    getStage().getCamera().position.set(
        getX() + getWidth() / 2f,
        getY() + getHeight() / 2f,
        0
    );
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
  }

  public void setGoingLeft(boolean going) {
    goingLeft = going;
  }

  public void setGoingRight(boolean going) {
    goingRight = going;
  }

  public void setGoingUp(boolean going) {
    goingUp = going;
  }

  public void setGoingDown(boolean going) {
    goingDown = going;
  }
}
