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
    public static final String BOMB_ATLAS = "img/actors.txt";


    public static final TextureAtlas bombAtlas = new TextureAtlas(Gdx.files.internal(BOMB_ATLAS));
    public static final TextureAtlas boss1Atlas = new TextureAtlas(Gdx.files.internal(BOSS1_ATLAS));
    public static final TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal(BOMBER_ATLAS));

    public static final short DEFAULT_BIT = 1;
    public static final short BOMBER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short STONE_BIT = 6;
    public static final short DESTROY_BIT = 8;
    public static final short BOSS1_BIT = 10;
    public static final short FLAME_BIT = 12;


}
