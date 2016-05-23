package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Person {

  private static final int NANOSECONDS_IN_SECOND = 1000000000;

  private double lastShotTime = 0;
  private Player target;

  /**
   * The evil enemy that must be destroyed.
   *
   * @param texture The player's texture.
   * @param startX  The starting x-position.
   * @param startY  The starting y-position.
   * @param world   The Box2D world.
   */
  public Enemy(Texture texture, float startX, float startY, World world, Player target, Color color) {
    super(texture, startX, startY, world, color);
    this.target = target;
  }

  @Override
  public void act(float delta) {
    update(delta);

    double currentTime = System.nanoTime();
    if (currentTime - lastShotTime > NANOSECONDS_IN_SECOND * 0.2) {
      shoot(target.getX() - getX(), target.getY() - getY());
      lastShotTime = currentTime;
    }

    super.act(delta);
  }
}
