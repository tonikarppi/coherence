package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Player extends Person {
  private static final int NANOSECONDS_IN_SECOND = 1000000000;

  private Touchpad movementController;
  private Touchpad shooterController;
  private double lastShotTime = 0;

  /**
   * The player that's being moved.
   * @param texture The player texture.
   * @param startX The starting x-position.
   * @param startY The starting y-position.
   * @param world The Box2D world.
   * @param hud Overlay showing buttons.
   */

  public Player(Texture texture, float startX, float startY, World world, Hud hud, Color color) {
    super(texture, startX, startY, world, color);
    movementController = hud.getMovementController();
    shooterController = hud.getShooterController();
  }

  @Override
  public void act(float delta) {
    if (movementController != null) {
      body.setLinearVelocity(
          movementController.getKnobPercentX() * 20,
          movementController.getKnobPercentY() * 20
      );
    }

    double currentTime = System.nanoTime();
    if (shooterController != null && (currentTime - lastShotTime > NANOSECONDS_IN_SECOND * 0.2)) {
      if (shooterController.getKnobPercentX() * shooterController.getKnobPercentY() != 0.0f) {
        shoot(
            shooterController.getKnobPercentX(),
            shooterController.getKnobPercentY()
        );
        lastShotTime = currentTime;
      }
    }

    super.act(delta);
  }
}