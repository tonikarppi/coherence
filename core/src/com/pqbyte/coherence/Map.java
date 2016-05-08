package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The map where the game is played.
 */
public class Map extends Actor {

  private Texture texture;

  public Map(Texture texture, float width, float height) {
    this.texture = texture;
    setBounds(getX(), getY(), width, height);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());
  }
}
