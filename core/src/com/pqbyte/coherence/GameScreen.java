package com.pqbyte.coherence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Iterator;

public class GameScreen extends ScreenAdapter {
  private static final float VIEWPORT_WIDTH = 80;
  final Coherence game;
  private Stage gameStage;
  private World world;
  private Box2DDebugRenderer debugRenderer;
  private Array<Projectile> bulletToBeRemoved;
  private Array<Player> alivePlayers;
  private Player player;
  private Hud hud;
  Music gameMusic;


  /**
   * The screen where the game is played.
   */
  public GameScreen(final Coherence game) {
    this.game = game;
    world = new World(new Vector2(0, 0), true);
    bulletToBeRemoved = new Array<Projectile>();
    alivePlayers = new Array<Player>();

    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Gamemusic.ogg"));
    gameMusic.setLooping(true);

    world.setContactListener(new CollisionListener(bulletToBeRemoved));

    player = new Player(
        new Texture(Gdx.files.internal("cube128.png")),
        20,
        20,
        world,
        0,
        0,
        1,
        0
    );

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    Map map = new Map(
        new Texture(Gdx.files.internal("Gamemap.png")),
        Constants.WORLD_WIDTH,
        Constants.WORLD_HEIGHT,
        world
    );
//wallpaper.jpg
    gameStage = new Stage(
        new ExtendViewport(
            VIEWPORT_WIDTH,
            VIEWPORT_WIDTH * (screenHeight / screenWidth))
    );
    gameStage.addActor(map);
    addObstacles();
    gameStage.addActor(player);
    gameStage.setKeyboardFocus(player);

    Player enemy = new Player(
        new Texture(Gdx.files.internal("cube128.png")),
        10,
        10,
        world,
        1,
        0,
        0,
        0
    );
    gameStage.addActor(enemy);

    alivePlayers.add(player);
    alivePlayers.add(enemy);

    Gdx.input.setInputProcessor(gameStage);

    if (Constants.isDebug()) {
      debugRenderer = new Box2DDebugRenderer();
    }

    hud = new Hud(gameStage.getBatch());
    player.setHud(hud);
    Gdx.input.setInputProcessor(hud.getStage());
  }

  @Override
  public void dispose() {
    gameStage.dispose();
    world.dispose();
    if (Constants.isDebug()) {
      debugRenderer.dispose();
    }
    gameMusic.dispose();
  }

  @Override
  public void show() {
    // Start playing when screen is shown
    gameMusic.play();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    removeUsedBullets();
    removeDeadPlayers();
    world.step(1f / 60f, 6, 2);
    gameStage.act(delta);
    gameStage.getCamera().position.set(
        player.getX() + player.getWidth() / 2f,
        player.getY() + player.getHeight() / 2f,
        0
    );


    //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
   // player.setColor(Color.BLUE);

    //shapeRenderer.end();
    gameStage.draw();

    if (Constants.isDebug()) {
      Matrix4 debugMatrix = gameStage
          .getBatch()
          .getProjectionMatrix()
          .cpy();

      debugRenderer.render(world, debugMatrix);
    }

    Stage hudStage = hud.getStage();
    hudStage.getBatch().setProjectionMatrix(hudStage.getCamera().combined);
    hudStage.act(delta);
    hudStage.draw();
  }

  /**
   * Removes bullet actors from scene.
   */
  private void removeUsedBullets() {
    Iterator<Projectile> iterator = bulletToBeRemoved.iterator();
    while (iterator.hasNext()) {
      Projectile bullet = iterator.next();
      bullet.remove();
      iterator.remove();
    }
  }

  /**
   * Removes dead players from scene.
   */
  private void removeDeadPlayers() {
    Iterator<Player> iterator = alivePlayers.iterator();
    while (iterator.hasNext()) {
      Player player = iterator.next();
      if (!player.isAlive()) {
        player.remove();
        iterator.remove();
        game.setScreen(new WinnerScreen(game));
      }
    }
  }

  private void addObstacles() {
    Vector2 bottomLeftCorner = new Vector2(-Constants.WORLD_WIDTH / 2, -Constants.WORLD_HEIGHT / 2);
    Vector2 bottomRightCorner = new Vector2(Constants.WORLD_WIDTH / 2, -Constants.WORLD_HEIGHT / 2);
    Vector2 topLeftCorner = new Vector2(-Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
    Vector2 topRightCorner = new Vector2(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
    Vector2 center = new Vector2(0, 0);
    float shortLength = 20;
    float longLength = 30;
    float breath = 5;
    float sideOffset = 20;

    Gdx.app.log(getClass().getSimpleName(), bottomLeftCorner.x + ", " + bottomLeftCorner.y);

    Obstacle obstacleLeftHorizontal = new Obstacle(
        null,
        bottomLeftCorner.x + sideOffset,
        bottomLeftCorner.y + (shortLength - breath) / 2f + sideOffset,
        shortLength,
        breath,
        0,
        world);

    Obstacle obstacleLeftVertical = new Obstacle(
        null,
        bottomLeftCorner.x + (shortLength - breath) / 2f + sideOffset,
        bottomLeftCorner.y + sideOffset,
        shortLength,
        breath,
        90,
        world
    );

    Obstacle obstacleRightHorizontal = new Obstacle(
        null,
        topRightCorner.x - sideOffset,
        topRightCorner.y - (shortLength - breath) / 2f - sideOffset,
        shortLength,
        breath,
        0,
        world);

    Obstacle obstacleRightVertical = new Obstacle(
        null,
        topRightCorner.x - (shortLength - breath) / 2f - sideOffset,
        topRightCorner.y - sideOffset,
        shortLength,
        breath,
        90,
        world
    );

    Obstacle obstacleCenterVertical = new Obstacle(
        null,
        center.x,
        center.y,
        longLength,
        breath,
        90,
        world
    );

    Obstacle obstacleBottomRight = new Obstacle(
        null,
        bottomRightCorner.x - sideOffset,
        bottomRightCorner.y + sideOffset,
        longLength,
        breath,
        135,
        world
    );

    Obstacle obstacleTopLeft = new Obstacle(
        null,
        topLeftCorner.x + sideOffset,
        topLeftCorner.y - sideOffset,
        longLength,
        breath,
        135,
        world
    );

    gameStage.addActor(obstacleLeftHorizontal);
    gameStage.addActor(obstacleLeftVertical);
    gameStage.addActor(obstacleRightHorizontal);
    gameStage.addActor(obstacleRightVertical);
    gameStage.addActor(obstacleCenterVertical);
    gameStage.addActor(obstacleBottomRight);
    gameStage.addActor(obstacleTopLeft);
  }
}
