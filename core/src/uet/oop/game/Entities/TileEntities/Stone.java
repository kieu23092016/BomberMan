package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import uet.oop.game.Entities.Entity;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.STONE_BIT;


public class Stone extends TileEntity {

    public Stone(PlayScreen playScreen, TiledMap map, Rectangle rectangle) {
        super(playScreen, map, rectangle);
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
