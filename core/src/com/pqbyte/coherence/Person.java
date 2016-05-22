package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * A moving person (player or enemy).
 */
public abstract class Person extends Actor {

  private static final int PLAYER_SIZE = 2;
  private static final int FULL_HEALTH = 100;

  protected Body body;
  private Sprite sprite;
  private World world;
  private Array<Projectile> projectiles;
  private int currentHealth = FULL_HEALTH;
  private boolean alive = true;
  //private Sound laserSound;
  private Sound crashSound;

  /**
   * The player entity that is controlled.
   *
   * @param texture The player's texture.
   * @param startX  The starting x-position.
   * @param startY  The starting y-position.
   * @param world   The Box2D world.
   */
  public Person(Texture texture, float startX, float startY, World world) {
    this.world = world;
    sprite = new Sprite(texture);
    setBounds(startX, startY, PLAYER_SIZE, PLAYER_SIZE);
    body = createPlayerBody(world);
    projectiles = new Array<Projectile>();
    //laserSound = Gdx.audio.newSound(Gdx.files.internal("Lasersound.ogg"));
    crashSound = Gdx.audio.newSound(Gdx.files.internal("Crashsound.wav"));
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
    //laserSound.dispose();

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
    //laserSound.play();
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
}
