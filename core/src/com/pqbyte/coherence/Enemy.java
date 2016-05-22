package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Person {

  /**
   * The evil enemy that must be destroyed.
   *
   * @param texture The player's texture.
   * @param startX  The starting x-position.
   * @param startY  The starting y-position.
   * @param world   The Box2D world.
   */
  public Enemy(Texture texture, float startX, float startY, World world) {
    super(texture, startX, startY, world);
  }
}
