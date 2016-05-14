package com.pqbyte.coherence;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


/**
 * The map where the game is played.
 */
public class Map extends Actor {

  private Texture texture;
  private World world;
  //TextButton button;
 // TextButton.TextButtonStyle textButtonStyle;
  //BitmapFont font;
 // Skin skin;
 // TextureAtlas buttonAtlas;
 // Stage stage;

  /**
   * The map where the game is played.
   * @param texture The map texture.
   * @param width The width of the map.
   * @param height The height of the map.
   * @param world The Box2D world.
   */
  public Map(Texture texture, float width, float height, World world) {
    this.texture = texture;
    this.world = world;
    setBounds(getX(), getY(), width, height);
    createWalls();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    if (!Constants.isDebug()) {
      batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
  }

  /**
   * Creates the Box2D walls for the map,
   * which stop things from escaping the map.
   */
  private void createWalls() {
    createWall(0, 0, getWidth(), 0);
    createWall(0, getHeight(), getWidth(), getHeight());
    createWall(0, 0, 0, getHeight());
    createWall(getWidth(), 0, getWidth(), getHeight());
  }

  private void createWall(float x1, float y1, float x2, float y2) {
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.StaticBody;
    def.position.set(0, 0);

    EdgeShape shape = new EdgeShape();
    shape.set(x1, y1, x2, y2);

    FixtureDef properties = new FixtureDef();
    properties.shape = shape;
    properties.filter.categoryBits = Constants.WORLD_ENTITY;
    properties.filter.maskBits = Constants.BULLET_ENTITY | Constants.PLAYER_ENTITY;

    Body wall = world.createBody(def);
    wall.createFixture(properties);
    wall.setUserData(this);

    shape.dispose();
  }
}
