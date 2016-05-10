package com.pqbyte.coherence;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ShootingListener extends InputListener {

  private Player player;

  public ShootingListener(Player player) {
    this.player = player;
  }

  @Override
  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    player.shoot(x, y);
    return true;
  }
}
