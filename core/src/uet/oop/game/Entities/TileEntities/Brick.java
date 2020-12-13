package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import uet.oop.game.Entities.Entity;

import static uet.oop.game.Manager.GameManager.BRICK_BIT;
import static uet.oop.game.Manager.GameManager.DESTROY_BIT;

public class Brick extends BreakableTileEntity {

    public Brick(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
        fixture.setUserData(this);
        setCategory(BRICK_BIT);
    }
    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collide");
        //setCategory(DESTROY_BIT);
        //getCell(1).setTile(null);
    }

    @Override
    public void dispose() {

    }
}
