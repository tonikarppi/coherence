package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor {

  private Body body;
  private World world;
  private float toX;
  private float toY;

  public Projectile(float fromX, float fromY, float toX, float toY, World world) {
    this.world = world;
    this.toX = toX;
    this.toY = toY;
    setPosition(fromX, fromY);
    setSize(0.5f, 0.5f);
    body = createProjectileBody();
  }

  @Override
  public void act(float delta) {
    float dx = toX - getX();
    float dy = toY - getY();

    body.applyLinearImpulse(dx, dy, getX(), getY(), false);
  }

  @Override
  public boolean remove() {
    // Check to see if bullet was already removed.
    if (body.getUserData() != null) {
      world.destroyBody(body);
    }

    return super.remove();
  }

  private Body createProjectileBody() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(
        getX() + getWidth() / 2,
        getY() + getHeight() / 2
    );
    Body body = world.createBody(bodyDef);
    body.setBullet(true);
    CircleShape shape = new CircleShape();
    shape.setRadius(getWidth() / 2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 10;
    fixtureDef.filter.categoryBits = Constants.PHYSICS_ENTITY;
    fixtureDef.filter.maskBits = Constants.WORLD_ENTITY;

    body.createFixture(fixtureDef);
    body.setUserData(this);
    shape.dispose();

    return body;
  }
}
