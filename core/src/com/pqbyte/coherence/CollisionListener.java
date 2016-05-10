package com.pqbyte.coherence;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
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
    Actor a = (Actor) contact.getFixtureA().getBody().getUserData();
    Actor b = (Actor) contact.getFixtureB().getBody().getUserData();

    if (a instanceof Projectile && b instanceof Map) {
      bulletsToBeRemoved.add((Projectile) a);
    } else if (b instanceof Projectile && a instanceof Map) {
      bulletsToBeRemoved.add((Projectile) b);
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
