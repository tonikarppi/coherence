package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends Actor {

  private Texture texture;
  private World world;

  /**
   * Represents a block on the map that blocks players and projectiles.
   *
   * @param texture The texture for the block.
   * @param xpos The x-position.
   * @param ypos The y-position.
   * @param width The width.
   * @param height The height.
   * @param angle The angle in degrees.
   * @param world The Box2D world object.
   */
  public Obstacle(
      Texture texture,
      float xpos,
      float ypos,
      float width,
      float height,
      float angle,
      World world) {
    this.texture = texture;
    this.world = world;
    setBounds(xpos, ypos, width, height);
    setRotation(angle);
    createObstacle();
  }

  private Body createObstacle() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(getX(), getY());
    PolygonShape shape = new PolygonShape();

    shape.setAsBox(
        getWidth() / 2,
        getHeight() / 2,
        new Vector2(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2),
        getRotation() * (float) Math.PI / 180f
    );

    FixtureDef properties = new FixtureDef();
    properties.shape = shape;
    properties.filter.categoryBits = Constants.WORLD_ENTITY;
    properties.filter.maskBits = Constants.PHYSICS_ENTITY;

    Body body = world.createBody(bodyDef);
    body.createFixture(properties);
    body.setUserData(this);
    shape.dispose();

    return body;
  }
}
