package com.pqbyte.coherence;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Iterator;

public class TheGame2 extends ApplicationAdapter {
  private static final float VIEWPORT_WIDTH = 30;

  private Stage stage;
  private World world;
  private Box2DDebugRenderer debugRenderer;
  private Array<Projectile> bulletToBeRemoved;

  @Override
  public void create() {
    world = new World(new Vector2(0, 0), true);
    bulletToBeRemoved = new Array<Projectile>();
    world.setContactListener(new CollisionListener(bulletToBeRemoved));

    Map map = new Map(
        new Texture(Gdx.files.internal("wallpaper.jpg")),
        Constants.WORLD_WIDTH,
        Constants.WORLD_HEIGHT,
        world
    );

    Player player = new Player(
        new Texture(Gdx.files.internal("cube128.png")),
        0,
        Constants.WORLD_HEIGHT - 10,
        world
    );
    player.addListener(new PlayerControlListener(player));

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    stage = new Stage(new ExtendViewport(
        VIEWPORT_WIDTH, VIEWPORT_WIDTH * (screenHeight / screenWidth)));
    stage.addListener(new ShootingListener(player));
    stage.addActor(map);
    stage.addActor(player);
    stage.setKeyboardFocus(player);

    Gdx.input.setInputProcessor(stage);

    if (Constants.isDebug()) {
      debugRenderer = new Box2DDebugRenderer();
    }
  }

  @Override
  public void dispose() {
    stage.dispose();
    world.dispose();
    if (Constants.isDebug()) {
      debugRenderer.dispose();
    }
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    removeUsedBullets();
    world.step(1f / 60f, 6, 2);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();

    if (Constants.isDebug()) {
      Matrix4 debugMatrix = stage
          .getBatch()
          .getProjectionMatrix()
          .cpy();

      debugRenderer.render(world, debugMatrix);
    }
  }

  /**
   * Removes bullet actors from scene.
   */
  private void removeUsedBullets() {
    Iterator<Projectile> bulletsIterator = bulletToBeRemoved.iterator();
    while (bulletsIterator.hasNext()) {
      Projectile bullet = bulletsIterator.next();
      bullet.remove();
      bulletsIterator.remove();
    }
  }
}
