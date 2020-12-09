package uet.oop.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import uet.oop.game.BombermanGame;
import uet.oop.game.Entities.Bomber;

import static uet.oop.game.Manager.GameManager.*;
import static uet.oop.game.Manager.GameManager.PPM;

public class PlayScreen implements Screen {

    private BombermanGame game;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private World gameWorld;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Bomber player;
    private float dt = 1/5f;

    private TextureAtlas textureAtlas;
    private TextureAtlas playerAtlas;
    private Animation animation;
    private float elapsedTime = 0;

    public World getGameWorld() {
        return gameWorld;
    }

    public PlayScreen(BombermanGame bombermanGame) {

        this.game = bombermanGame;

        camera = new OrthographicCamera(V_WIDTH / PPM, V_HEIGHT / PPM);
        viewport = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        textureAtlas = new TextureAtlas(Gdx.files.internal("sprite/spriteWhite.txt"));
        playerAtlas = new TextureAtlas(Gdx.files.internal(BOMBER_ATLAS));
        animation = new Animation(1 / 5f, textureAtlas.getRegions());

        map = new TmxMapLoader().load(MAP_FILES);
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

            polygonShape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }

        player = new Bomber(this, playerAtlas);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        //batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), 10 / PPM, 20 / PPM, 45 / PPM, 45 / PPM);
        game.batch.end();
        update(dt);

        box2DDebugRenderer.render(gameWorld, camera.combined);
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2) {
            //player.bomberBody.applyLinearImpulse(new Vector2(0.1f, 0), player.bomberBody.getWorldCenter(), true);
            player.body.setLinearVelocity(0.4f, 0.0F);
        }
        else player.body.setLinearVelocity(0.0f, 0.0F);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -2) {
            player.body.applyLinearImpulse(new Vector2(-0.4f, 0), player.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(0, 0.4f), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.body.applyLinearImpulse(new Vector2(0, -0.4f), player.body.getWorldCenter(), true);

    }

    public void update(float dt) {
        handleInput(dt);
        gameWorld.step(1 / 60f, 6, 2);
        player.update(dt);
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