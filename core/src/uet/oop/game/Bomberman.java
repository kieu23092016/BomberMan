package uet.oop.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Timer;

import java.awt.event.AdjustmentListener;

public class Bomberman extends ApplicationAdapter {
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;

	public void resize(int width, int height){
		camera.viewportWidth = width;
		camera.viewportWidth = height;
		camera.update();
	}
	private TextureAtlas textureAtlas;
	private Sprite sprite;
	//private Animation animation;
	private int currentFrame = 1;
	private float elapsedTime = 0;
	private String currentAtlasKey = new String("0001");
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("sprite\\asset\\player_down.png");
		map = new TmxMapLoader().load("sprite\\map\\untitled.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera(320, 320);
		camera.viewportHeight = 320;
		camera.viewportWidth = 320;
		camera.update();

		textureAtlas = new TextureAtlas((Gdx.files.internal("sprite\\asset\\pack")));
		TextureAtlas.AtlasRegion region = textureAtlas.findRegion(currentAtlasKey);
		//animation = new Animation(1/5.0f, textureAtlas.getRegions());

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
		}, 0, 1/5.0f);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img,0,0);
		sprite.draw(batch);
		//elapsedTime += Gdx.graphics.getDeltaTime();
		//batch.draw((Texture) animation.getKeyFrame(elapsedTime,true),0,0);
		batch.end();
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		map.dispose();
		renderer.dispose();
		textureAtlas.dispose();
	}
}
