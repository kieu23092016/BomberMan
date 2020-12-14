package uet.oop.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import uet.oop.game.BombermanGame;
import uet.oop.game.Entities.AnimateEntities.BombManager.Bomb;
import uet.oop.game.Entities.AnimateEntities.Bomber;

import javax.swing.*;
import java.awt.*;

import static uet.oop.game.Manager.GameManager.*;
import static uet.oop.game.Manager.GameManager.PPM;

public class GameOverScreen implements Screen {
    public Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;

    private BombermanGame game;
    private TextureRegion imgGameOverScreen;

    public GameOverScreen(Game game) {
        this.game = (BombermanGame) game;

        viewport = new FitViewport(Bomber.BOMBER_WIDTH, Bomber.BOMBER_HEIGHT, new OrthographicCamera());
        camera = new OrthographicCamera(V_WIDTH / PPM, V_HEIGHT / PPM);
        //stage = new Stage(viewport, ((BombermanGame) game).batch);
        imgGameOverScreen = new TextureRegion(new Texture("map/Images/Images/gameOverScreen.png"),765,674);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            System.out.println("touch");
            game.setScreen(new PlayScreen((BombermanGame) game));
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        if (imgGameOverScreen == null) {
            System.out.println(1);
        } else {
            System.out.println(0);
            game.batch.draw(imgGameOverScreen,0,0);
        }
        game.batch.end();
        //stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}