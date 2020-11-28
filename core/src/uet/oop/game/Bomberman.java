package uet.oop.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uet.oop.game.Scence.Hud;

public class Bomberman extends ApplicationAdapter {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewPort;
    SpriteBatch batch;
    Texture img;
    private float delta;
    public static final float V_WIDTH = 640;
    public static final float V_HEIGHT = 640;
    private Hud hud;
    private TextureAtlas textureAtlas;
    private Sprite sprite;
    private int currentFrame = 1;
    private float elapsedTime = 0;
    private String currentAtlasKey = new String("0001");

    @Override
    public void create() {
        batch = new SpriteBatch();
        hud = new Hud(batch);
        img = new Texture("sprite\\asset\\player_down.png");
        map = new TmxMapLoader().load("sprite\\map\\untitled.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        viewPort = new FitViewport(V_WIDTH, V_HEIGHT, camera);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera.position.set(320,480,0);//viewPort.getScreenX()/2, viewPort.getScreenY()/2, 0);
        camera.setToOrtho(false, w, h);
        camera.update();
        textureAtlas = new TextureAtlas((Gdx.files.internal("sprite\\asset\\pack")));
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(currentAtlasKey);
        sprite = new Sprite(region);
        sprite.setPosition(120, 100);
        sprite.scale(2.5f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentFrame++;
                if (currentFrame > 6) {
                    currentFrame = 1;
                }
                currentAtlasKey = String.format("%04d", currentFrame);
                sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
            }
        }, 0, 1 / 5.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        batch.begin();
        //batch.draw(img,0,0);
        sprite.draw(batch);
        //elapsedTime += Gdx.graphics.getDeltaTime();
        //batch.draw((Texture) animation.getKeyFrame(elapsedTime,true),0,0);

        renderer.setView(camera);
        renderer.render();
        batch.end();hud.stage.draw();
        update(delta);
    }
    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            camera.position.x += 100*dt;
    }
    public void update(float dt){
        handleInput(dt);
        camera.update();
        renderer.setView(camera);
    }
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        map.dispose();
        renderer.dispose();
        textureAtlas.dispose();
    }
}
