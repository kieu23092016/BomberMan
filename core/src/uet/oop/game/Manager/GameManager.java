package uet.oop.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameManager {
    public static final float PPM = 100;
    public static final float V_WIDTH = 765;
    public static final float V_HEIGHT = 675;

    public static final String MAP_FILES = "map/mapfinal.tmx";
    public static final String BOMBER_ATLAS = "sprite/spriteWhite.txt";
    public static final String BOSS1_ATLAS = "sprite/boss1.txt";
    public static final String BOMB_AND_ITEMS_ATLAS = "img/actors.txt";
    public static final String BOSSMINI_ATLAS = "sprite/bossMini.txt";



    public static final TextureAtlas bombAndItemAtlas = new TextureAtlas(Gdx.files.internal(BOMB_AND_ITEMS_ATLAS));
    public static final TextureAtlas boss1Atlas = new TextureAtlas(Gdx.files.internal(BOSS1_ATLAS));
    public static final TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal(BOMBER_ATLAS));
    public static final TextureAtlas bossMiniAtlas = new TextureAtlas(Gdx.files.internal(BOSSMINI_ATLAS));


    public static final short DEFAULT_BIT = 1;
    public static final short BOMBER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short STONE_BIT = 8;
    public static final short DESTROY_BIT = 128;
    public static final short BOSS1_BIT = 16;
    public static final short FLAME_BIT = 32;
    public static final short BOMB_BIT = 64;
    public static final short BOSSMINI_BIT = 256;


}
