package com.pqbyte.coherence;
    import com.badlogic.gdx.Game;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Pixmap;
    import com.badlogic.gdx.graphics.Pixmap.Format;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.ui.Image;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;
    import com.badlogic.gdx.scenes.scene2d.ui.Table;
    import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
    import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
    import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
    import com.badlogic.gdx.utils.Scaling;
    import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class MenuScreen implements Screen {
  Coherence game;
  Skin skin;
  Stage stage;
  SpriteBatch batch;
  private static final float VIEWPORT_WIDTH = 80;
  private static final float VIEWPORT_HEIGHT = 50;

  public MenuScreen( Coherence game){
    create();
    this.game = game ;
  }

  public MenuScreen(){
    create();
  }


  public void create(){
    /**
    batch = new SpriteBatch();
    //stage = new Stage();
    stage = new Stage(
        new ScalingViewport(
            Scaling.stretch,
            VIEWPORT_WIDTH,
            VIEWPORT_HEIGHT,
            new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT))
    );
    Gdx.input.setInputProcessor(stage);

    // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
    // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
    skin = new Skin();
    // Generate a 1x1 white texture and store it in the skin named "white".
    Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
    pixmap.setColor(Color.GREEN);
    pixmap.fill();

    skin.add("white", new Texture(pixmap));

    // Store the default libgdx font under the name "default".
    BitmapFont bfont=new BitmapFont();
   // bfont.scale(1);
    skin.add("default",bfont);

    // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
    TextButtonStyle textButtonStyle = new TextButtonStyle();
    textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
    textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
    textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
    textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

    textButtonStyle.font = skin.getFont("default");

    skin.add("default", textButtonStyle);

    // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
    final TextButton textButton=new TextButton("PLAY",textButtonStyle);
    textButton.setPosition(200, 200);
    stage.addActor(textButton);
    stage.addActor(textButton);
    stage.addActor(textButton);

    // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
    // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
    // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
    // revert the checked state.
    textButton.addListener(new ChangeListener() {
      public void changed (ChangeEvent event, Actor actor) {
        //System.out.println("Clicked! Is checked: " + button.isChecked());
        textButton.setText("Starting new game");
        game.setScreen( new GameScreen());

      }
    });
     */
    Stage stage = new Stage();
    Gdx.input.setInputProcessor(stage);// Make the stage consume events

    createBasicSkin();
    TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
    newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
    stage.addActor(newGameButton);
  }

  private void createBasicSkin(){
    //Create a font
    BitmapFont font = new BitmapFont();
    skin = new Skin();
    skin.add("default", font);

    //Create a texture
    Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
    pixmap.setColor(Color.WHITE);
    pixmap.fill();
    skin.add("background",new Texture(pixmap));

    //Create a button style
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
    textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
    textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
    textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
    textButtonStyle.font = skin.getFont("default");
    skin.add("default", textButtonStyle);

  }



  public void render (float delta) {
    /**
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
   // table.drawDebug(stage);
    stage.setDebugAll(true);
     */
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act();
    stage.draw();
  }

  @Override
  public void resize (int width, int height) {
    //stage.setViewport(width, height, false);
  }

  @Override
  public void dispose () {
    stage.dispose();
    skin.dispose();
  }

  @Override
  public void show() {
    // TODO Auto-generated method stub

  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub

  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }
}
