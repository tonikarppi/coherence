package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor {

  private Person shooter;
  private Body body;
  private World world;
  private ParticleEffect effect;

  private float dx;
  private float dy;
  private float length;
  private float speed;

  /**
   * Represents a projectile being shot.
   * @param shooter The person who shot the projectile.
   * @param fromX Originating x-position.
   * @param fromY Originating y-position.
   * @param toX Target x-position.
   * @param toY Target y-position.
   * @param world The Box2D world.
   */
  public Projectile(Person shooter, float fromX, float fromY, float toX, float toY, World world) {
    this.shooter = shooter;
    this.world = world;
    dx = toX - fromX;
    dy = toY - fromY;
    length = (float) Math.sqrt(dx * dx + dy * dy);
    speed = 80;
    effect = new ParticleEffect();
    if (shooter instanceof Player) {
      effect.load(Gdx.files.internal("blue-particle"), Gdx.files.internal(""));
    } else if (shooter instanceof Enemy) {
      effect.load(Gdx.files.internal("red-particle"), Gdx.files.internal(""));
    } else {
      throw new IllegalArgumentException("Unknown shooter type");
    }
    effect.scaleEffect(0.05f);
    effect.start();

    int offsetFromPlayer = 3;
    setPosition(
        fromX + offsetFromPlayer * dx / length,
        fromY + offsetFromPlayer * dy / length);
    setSize(0.5f, 0.5f);
    body = createProjectileBody();
  }

  @Override
  public void act(float delta) {
    Vector2 position = body.getPosition();
    effect.update(delta);
    effect.setPosition(position.x, position.y);
    body.setLinearVelocity(
        speed * dx / length,
        speed * dy / length);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    if (Constants.isDebug()) {
      return;
    }

    effect.draw(batch);

    if (effect.isComplete()) {
      effect.reset();
    }
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
    fixtureDef.isSensor = true;
    fixtureDef.filter.categoryBits = Constants.BULLET_ENTITY;
    fixtureDef.filter.maskBits = Constants.WORLD_ENTITY;

    body.createFixture(fixtureDef);
    body.setUserData(this);
    shape.dispose();

    return body;
  }

  public Person getShooter() {
    return shooter;
  }
}
