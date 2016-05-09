package com.pqbyte.coherence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The player being controlled.
 */
public class Player extends Actor {
  private static final int PLAYER_SIZE = 2;
  private static final float STEP_SIZE = 20;

  private boolean goingLeft = false;
  private boolean goingRight = false;
  private boolean goingUp = false;
  private boolean goingDown = false;

  private Sprite sprite;
  private Body body;

  public Player(Texture texture, float startX, float startY, World world) {
    sprite = new Sprite(texture);
    setBounds(startX, startY, PLAYER_SIZE, PLAYER_SIZE);
    body = createPlayerBody(world);
  }

  @Override
  public void act(float delta) {
    float horizontal = 0;
    float vertical = 0;

    if (goingLeft) {
      horizontal -= STEP_SIZE;
    }
    if (goingRight) {
      horizontal += STEP_SIZE;
    }
    if (goingUp) {
      vertical += STEP_SIZE;
    }
    if (goingDown) {
      vertical -= STEP_SIZE;
    }

    body.setLinearVelocity(horizontal, vertical);
    Vector2 bodyPos = body.getPosition();
    setPosition(
        bodyPos.x - getWidth() / 2,
        bodyPos.y - getHeight() / 2
    );

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

  /**
   * Creates the Box2D physical body of the player.
   *
   * @param world The world object.
   * @return The player's body.
   */
  private Body createPlayerBody(World world) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(
        getX() + getWidth() / 2,
        getY() + getHeight() / 2
    );
    Body body = world.createBody(bodyDef);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(
        getWidth() / 2,
        getHeight() / 2
    );

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1;
    fixtureDef.filter.categoryBits = Constants.PHYSICS_ENTITY;
    fixtureDef.filter.maskBits = Constants.WORLD_ENTITY;

    body.createFixture(fixtureDef);
    shape.dispose();

    return body;
  }
}
