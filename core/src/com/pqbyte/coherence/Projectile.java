package com.pqbyte.coherence;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor {

  private Player shooter;
  private Body body;
  private World world;
  private float toX;
  private float toY;

  private float dx;
  private float dy;
  private float length;
  private float speed;

  /**
   * Represents a projectile being shot.
   *
   * @param shooter The player who fired the projectile.
   *                @param fromX The origin
   * @param world   The Box2D world.
   */
  public Projectile(Player shooter, float fromX, float fromY, float toX, float toY, World world) {
    this.shooter = shooter;
    this.world = world;
    dx = toX - fromX;
    dy = toY - fromY;
    length = (float) Math.sqrt(dx * dx + dy * dy);
    speed = 80;

    int offsetFromPlayer = 3;
    setPosition(
        fromX + offsetFromPlayer * dx / length,
        fromY + offsetFromPlayer * dy / length);
    setSize(0.5f, 0.5f);
    body = createProjectileBody();
  }

  @Override
  public void act(float delta) {
    body.setLinearVelocity(
        speed * dx / length,
        speed * dy / length);
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
