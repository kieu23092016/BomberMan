package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BreakableTileEntity extends TileEntity{
    public enum State {NORMAL, BROKEN}
    public State currentState = State.NORMAL;
    public BreakableTileEntity(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
    }

}
