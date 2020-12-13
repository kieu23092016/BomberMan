package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import uet.oop.game.Entities.Entity;

import static uet.oop.game.Manager.GameManager.STONE_BIT;


public class Stone extends TileEntity {

    public Stone(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
        fixture.setUserData(this);
        setCategory(STONE_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Stone", "Collide");
    }

    @Override
    public void dispose() {

    }
}
