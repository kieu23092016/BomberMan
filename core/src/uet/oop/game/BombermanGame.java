package uet.oop.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import uet.oop.game.Screens.PlayScreen;

public class BombermanGame extends Game {
      public SpriteBatch batch;
      public static AssetManager manager;

    @Override
    public void create() {
        batch = new SpriteBatch();

        manager = new AssetManager();
        manager.load("audio/music/playmusic (2).ogg", Music.class);
        //manager.load("audio/music/menu1.ogg", Music.class);
        //manager.load("audio/sound/bomb_bang.wav", Sound.class);
        //manager.load("audio/sound/bomber_die.wav", Sound.class);
        //manager.load("audio/sound/item.wav", Sound.class);
        //manager.load("audio/sound/boss_die.wav", Sound.class);
        //manager.load("audio/sound/newbomb.wav", Sound.class);
        //manager.load("audio/sound/win.wav", Sound.class);
        manager.finishLoading();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
        manager.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

}
