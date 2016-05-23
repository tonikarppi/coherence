package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * A moving person (player or enemy).
 */
public class Person extends Actor implements Steerable<Vector2> {

  private static final int PLAYER_SIZE = 2;
  private static final int FULL_HEALTH = 100;

  protected Body body;
  private Sprite sprite;
  private World world;
  private Array<Projectile> projectiles;
  private int currentHealth = FULL_HEALTH;
  private boolean alive = true;
  private Sound laserSound;
  private Sound crashSound;
  private Color color;

  // AI stuff
  SteeringBehavior<Vector2> behavior;
  SteeringAcceleration<Vector2> steeringOutput;
  boolean tagged;
  float boundingRadius;
  float maxLinearSpeed;
  float maxLinearAcceleration;
  float maxAngularSpeed;
  float maxAngularAcceleration;

  /**
   * The player entity that is controlled.
   *
   * @param texture The player's texture.
   * @param startX  The starting x-position.
   * @param startY  The starting y-position.
   * @param world   The Box2D world.
   */
  public Person(Texture texture, float startX, float startY, World world, Color color) {
    this.world = world;
    this.color = color;
    sprite = new Sprite(texture);
    setBounds(startX, startY, PLAYER_SIZE, PLAYER_SIZE);
    body = createPlayerBody(world);
    projectiles = new Array<Projectile>();
    laserSound = Gdx.audio.newSound(Gdx.files.internal("Lasersound.ogg"));
    crashSound = Gdx.audio.newSound(Gdx.files.internal("Crashsound.wav"));

    boundingRadius = PLAYER_SIZE / 2;
    maxLinearSpeed = 50;
    maxLinearAcceleration = 100;
    maxAngularSpeed = 20;
    maxAngularAcceleration = 100;
    tagged = false;
    steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
  }

  @Override
  public void act(float delta) {
    Vector2 bodyPos = body.getPosition();
    setPosition(
        bodyPos.x - getWidth() / 2,
        bodyPos.y - getHeight() / 2
    );

    Stage stage = getStage();
    Iterator<Projectile> iterator = projectiles.iterator();
    while (iterator.hasNext()) {
      stage.addActor(iterator.next());
      iterator.remove();
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    if (!Constants.isDebug()) {
      batch.setColor(color);
      batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
    }
  }

  @Override
  public boolean remove() {
    // Check to see if bullet was already removed.
    if (body.getUserData() != null) {
      world.destroyBody(body);
    }

    crashSound.dispose();
    laserSound.dispose();

    return super.remove();
  }

  /**
   * Shoots a projectile towards to coordinates.
   *
   * @param targetX The target x-coordinate.
   * @param targetY The target y-coordinate.
   */
  public void shoot(float targetX, float targetY) {
    projectiles.add(new Projectile(
        this,
        getX() + getWidth() / 2,
        getY() + getHeight() / 2,
        getX() + getWidth() / 2 + targetX,
        getY() + getHeight() / 2 + targetY,
        world
    ));
    laserSound.play();
  }

  /**
   * Make the player take some damage.
   *
   * @param damage The amount of damage to player takes.
   */
  public void takeDamage(int damage) {
    currentHealth -= damage;
    crashSound.play();
    if (currentHealth <= 0) {
      Gdx.app.log(getClass().getSimpleName(), "Player is dead");
      alive = false;
    }
  }

  public boolean isAlive() {
    return alive;
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
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(
        getWidth() / 2,
        getHeight() / 2
    );

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 100;
    fixtureDef.filter.categoryBits = Constants.PLAYER_ENTITY;
    fixtureDef.filter.maskBits = Constants.WORLD_ENTITY;

    Body body = world.createBody(bodyDef);
    body.createFixture(fixtureDef);
    body.setUserData(this);

    shape.dispose();

    return body;
  }

  @Override
  public Vector2 getLinearVelocity() {
    return body.getLinearVelocity();
  }

  @Override
  public float getAngularVelocity() {
    return body.getAngularVelocity();
  }

  @Override
  public float getBoundingRadius() {
    return boundingRadius;
  }

  @Override
  public boolean isTagged() {
    return tagged;
  }

  @Override
  public void setTagged(boolean tagged) {
    this.tagged = tagged;
  }

  @Override
  public float getZeroLinearSpeedThreshold() {
    return 0.001f;
  }

  @Override
  public void setZeroLinearSpeedThreshold(float value) {

  }

  @Override
  public float getMaxLinearSpeed() {
    return maxLinearSpeed;
  }

  @Override
  public void setMaxLinearSpeed(float maxLinearSpeed) {
    this.maxLinearSpeed = maxLinearSpeed;
  }

  @Override
  public float getMaxLinearAcceleration() {
    return maxLinearSpeed;
  }

  @Override
  public void setMaxLinearAcceleration(float maxLinearAcceleration) {
    this.maxLinearAcceleration = maxLinearAcceleration;
  }

  @Override
  public float getMaxAngularSpeed() {
    return maxAngularSpeed;
  }

  @Override
  public void setMaxAngularSpeed(float maxAngularSpeed) {
    this.maxAngularSpeed = maxAngularSpeed;
  }

  @Override
  public float getMaxAngularAcceleration() {
    return maxAngularAcceleration;
  }

  @Override
  public void setMaxAngularAcceleration(float maxAngularAcceleration) {
    this.maxAngularAcceleration = maxAngularAcceleration;
  }

  @Override
  public Vector2 getPosition() {
    return body.getPosition();
  }

  @Override
  public float getOrientation() {
    return body.getAngle();
  }

  @Override
  public void setOrientation(float orientation) {
    body.setTransform(getPosition(), orientation);
  }

  @Override
  public float vectorToAngle(Vector2 vector) {
    //noinspection SuspiciousNameCombination
    return (float) Math.atan2(-vector.x, vector.y);
  }

  @Override
  public Vector2 angleToVector(Vector2 outVector, float angle) {
    outVector.x = -(float) Math.sin(angle);
    outVector.y = (float) Math.cos(angle);

    return outVector;
  }

  @Override
  public Location<Vector2> newLocation() {
    return null;
  }

  public boolean isIndependentFacing() {
    return true;
  }

  public Body getBody() {
    return body;
  }

  public void setBehavior(SteeringBehavior<Vector2> behavior) {
    this.behavior = behavior;
  }

  public SteeringBehavior<Vector2> getBehavior() {
    return behavior;
  }

  /**
   * Update AI steering.
   *
   * @param delta Time since last update.
   */
  public void update(float delta) {
    if (behavior != null) {
      behavior.calculateSteering(steeringOutput);
      applySteering(delta);
    }
  }

  protected void applySteering(float deltaTime) {
    boolean anyAccelerations = false;

    // Update position and linear velocity.
    if (!steeringOutput.linear.isZero()) {
      // this method internally scales the force by deltaTime
      body.applyForceToCenter(steeringOutput.linear.scl(100), true);
      anyAccelerations = true;
    }

    // Update orientation and angular velocity
    if (isIndependentFacing()) {
      if (steeringOutput.angular != 0) {
        // this method internally scales the torque by deltaTime
        body.applyTorque(steeringOutput.angular, true);
        anyAccelerations = true;
      }
    } else {
      // If we haven't got any velocity, then we can do nothing.
      Vector2 linVel = getLinearVelocity();
      if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
        float newOrientation = vectorToAngle(linVel);
        body.setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime); // this is superfluous if independentFacing is always true
        body.setTransform(body.getPosition(), newOrientation);
      }
    }

    if (anyAccelerations) {
      // Cap the linear speed
      Vector2 velocity = body.getLinearVelocity();
      float currentSpeedSquare = velocity.len2();
      float maxLinearSpeed = getMaxLinearSpeed();
      if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
        body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
      }

      // Cap the angular speed
      float maxAngVelocity = getMaxAngularSpeed();
      if (body.getAngularVelocity() > maxAngVelocity) {
        body.setAngularVelocity(maxAngVelocity);
      }
    }
  }
}
