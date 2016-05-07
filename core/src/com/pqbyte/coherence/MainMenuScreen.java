package com.pqbyte.coherence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Carl on 5/7/2016.
 */
public abstract class MainMenuScreen implements Screen {
    final Coherence game;

    OrthographicCamera camera;

    public MainMenuScreen(final Coherence gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

}
