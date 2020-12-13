package uet.oop.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import uet.oop.game.Entities.AnimateEntities.BombManager.Bomb;
import uet.oop.game.Entities.AnimateEntities.BombManager.Flame;
import uet.oop.game.Entities.AnimateEntities.Bomber;
import uet.oop.game.Entities.AnimateEntities.Enemies.Boss1;
import uet.oop.game.Entities.AnimateEntities.Enemies.Ghost;
import uet.oop.game.Entities.Items.*;
import uet.oop.game.Entities.TileEntities.Brick;
import uet.oop.game.Entities.TileEntities.Stone;
import uet.oop.game.Manager.WorldContactListener;
import uet.oop.game.Scence.Hud;

import static uet.oop.game.Manager.GameManager.*;
import static uet.oop.game.Manager.GameManager.PPM;

public class PlayScreen implements Screen {

    //TODO: test flame
    /*private Flame flame;
    private Bomb bomb;*/

    //TODO: test item
    private Item liveItem;

    private BombermanGame game;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private World gameWorld;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Bomber player;
    private Boss1 boss1;
    private Ghost ghost1;
    private float dt = 1 / 5f;

    private Animation animation;
    private Music music;
    private Hud hud;
    public float elapsedTime = 0;

    public World getGameWorld() {
        return gameWorld;
    }

    public PlayScreen(BombermanGame bombermanGame) {

        this.game = bombermanGame;
        hud = new Hud(game.batch);
        camera = new OrthographicCamera(V_WIDTH / PPM, V_HEIGHT / PPM);
        viewport = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        animation = new Animation(1 / 5f, bombAndItemAtlas.getRegions());

        map = new TmxMapLoader().load(MAP_FILES);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        camera.update();

        gameWorld = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer(
                /*drawBodies*/         true,
                /*drawJoints*/         true,
                /*drawAABBs*/          false,
                /*drawInactiveBodies*/ true,
                /*drawVelocities*/     false,
                /*drawContacts*/       true);

        // TODO: create ground bodies.
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Brick(gameWorld, map, rectangle);
        }
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Stone(gameWorld, map, rectangle);
        }
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Brick(gameWorld, map, rectangle);
        }

        player = new Bomber(this, playerAtlas);
        boss1 = new Boss1(gameWorld, map, boss1Atlas);
        ghost1 = new Ghost(gameWorld, map, bossMiniAtlas);

        animation = boss1.animation;
        gameWorld.setContactListener(new WorldContactListener());

        //TODO: test flame
       // bomb = new Bomb(player, bombAtlas);
        //flame = new Flame(bomb, bombAtlas, Flame.Direction.CENTER, true, 32/PPM, 32/PPM);
        //TODO: test item
        liveItem = new FlameItem(this, bombAndItemAtlas, 100/PPM, 100/PPM);


        music = BombermanGame.manager.get("audio/music/playmusic (2).ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

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

        player.draw(game.batch, dt);

        boss1.draw(game.batch);
        ghost1.draw(game.batch);


        //TODO: test flame
        //bomb.draw(game.batch);
        //flame.draw(game.batch);
        //TODO: test live item
        liveItem.draw(game.batch);

        elapsedTime += Gdx.graphics.getDeltaTime();
        //game.batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), 10 / PPM, 20 / PPM, 45 / PPM, 45 / PPM);
        game.batch.end();
        update(dt);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        //box2DDebugRenderer.setDrawBodies(false);
        box2DDebugRenderer.render(gameWorld, camera.combined);
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2) {
            //player.bomberBody.applyLinearImpulse(new Vector2(0.1f, 0), player.bomberBody.getWorldCenter(), true);
            player.body.setLinearVelocity(player.SPEED, 0);
        } else player.body.setLinearVelocity(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -2) {
            player.body.applyLinearImpulse(new Vector2(-player.SPEED, 0), player.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(0, player.SPEED), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.body.applyLinearImpulse(new Vector2(0, -player.SPEED), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.placeBomb(game.batch);
        }
    }

    public void update(float dt) {
        handleInput(dt);
        gameWorld.step(1 / 60f, 6, 2);
        hud.update(dt);
        player.update(dt);
        boss1.update(dt);
        ghost1.update(dt);
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
        hud.dispose();

    }

    public Hud getHud() {
        return hud;
    }
}
