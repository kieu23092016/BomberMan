package uet.oop.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uet.oop.game.Entities.Bomber;

public class BombermanGame extends ApplicationAdapter {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;


    private SpriteBatch batch;
    private Viewport viewport;
    public static final float PPM = 100;


    private World gameWorld;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Bomber player;
    private float dt = 1/5f;

    private TextureAtlas textureAtlas;
    private TextureAtlas playerAtlas;
    private Animation animation;
    private float elapsedTime = 0;

    public static final float V_WIDTH = 675;
    public static final float V_HEIGHT = 675;

    @Override
    public void create() {
        camera = new OrthographicCamera(V_WIDTH / PPM, V_HEIGHT / PPM);

        viewport = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("sprite/spriteWhite.txt"));
        playerAtlas = new TextureAtlas(Gdx.files.internal("sprite/pack"));
        animation = new Animation(1 / 5f, textureAtlas.getRegions());

        map = new TmxMapLoader().load("map/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        camera.update();


        gameWorld = new World(new Vector2(0,0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;
        // TODO: create ground bodies.
        for (MapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM,
                    (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
            body = gameWorld.createBody(bodyDef);

            //TODO:
            polygonShape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }
        for (MapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM,
                    (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
            body = gameWorld.createBody(bodyDef);

            //TODO:
            polygonShape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }
        for (MapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM,
                    (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
            body = gameWorld.createBody(bodyDef);

            //TODO:
            polygonShape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }

        player = new Bomber(gameWorld, playerAtlas);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true),
                10 / PPM, 20 / PPM, 45 / PPM, 45 / PPM);
        batch.end();
        update(dt);

        box2DDebugRenderer.render(gameWorld, camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public static float convertToBox(float x) {
        return x * PPM;
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.bomberBody.getLinearVelocity().x <= 2) {
            //player.bomberBody.applyLinearImpulse(new Vector2(0.1f, 0), player.bomberBody.getWorldCenter(), true);
            player.bomberBody.setLinearVelocity(0.4f, 0.0F);
        }
        else player.bomberBody.setLinearVelocity(0.0f, 0.0F);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.bomberBody.getLinearVelocity().x >= -2) {
            player.bomberBody.applyLinearImpulse(new Vector2(-0.4f, 0), player.bomberBody.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.bomberBody.applyLinearImpulse(new Vector2(0, 0.4f), player.bomberBody.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.bomberBody.applyLinearImpulse(new Vector2(0, -0.4f), player.bomberBody.getWorldCenter(), true);

    }

    public void update(float dt) {
        handleInput(dt);
        gameWorld.step(1 / 60f, 6, 2);
        player.update(dt);
    }
}
