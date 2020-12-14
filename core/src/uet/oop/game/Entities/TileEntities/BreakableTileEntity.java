package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import uet.oop.game.Screens.PlayScreen;

public abstract class BreakableTileEntity extends TileEntity{
    public enum State {NORMAL, BROKEN}
    public State currentState = State.NORMAL;

    public PlayScreen playScreen;
    public BreakableTileEntity(PlayScreen playScreen, TiledMap map, Rectangle rectangle) {
        super(playScreen, map, rectangle);
        this.playScreen = playScreen;
        gameWorld = playScreen.getGameWorld();
    }

}
