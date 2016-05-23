package com.pqbyte.coherence;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class CollisionListener implements ContactListener {

  private Array<Projectile> bulletsToBeRemoved;

  public CollisionListener(Array<Projectile> bulletsToBeRemoved) {
    this.bulletsToBeRemoved = bulletsToBeRemoved;
  }

  @Override
  public void beginContact(Contact contact) {
    Fixture fixA = contact.getFixtureA();
    Fixture fixB = contact.getFixtureB();
    Actor actorA = (Actor) fixA.getBody().getUserData();
    Actor actorB = (Actor) fixB.getBody().getUserData();
    Filter filterA = fixA.getFilterData();

    if (actorB instanceof Projectile
        && filterA.categoryBits == Constants.WORLD_ENTITY) {
      bulletsToBeRemoved.add((Projectile) actorB);
    } else if (actorB instanceof Projectile
        && actorA instanceof Person) {
      Person target = (Person) actorA;
      Projectile bullet = (Projectile) actorB;
      if (!bullet.getShooter().equals(actorA)) {
        // The player isn't shooting themselves, take damage.
        bulletsToBeRemoved.add(bullet);
        target.takeDamage(10);
      }
    }
  }

  @Override
  public void endContact(Contact contact) {

  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {
  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }
}
