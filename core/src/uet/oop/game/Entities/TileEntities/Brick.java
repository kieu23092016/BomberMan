package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import uet.oop.game.Entities.Entity;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.BRICK_BIT;
import static uet.oop.game.Manager.GameManager.DESTROY_BIT;

public class Brick extends BreakableTileEntity {

    public Brick(PlayScreen playScreen, TiledMap map, Rectangle rectangle) {
        super(playScreen, map, rectangle);
        fixture.setUserData(this);
        setCategory(BRICK_BIT);
    }

    public void createItem() {
        /*int random = (int) (Math.random() *(5-1));
        switch (random){
            case 1:

        }*/
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collide");
        //setCategory(DESTROY_BIT);
    }

    @Override
    public void dispose() {
        if (getCell(5) == null) getCell(6).setTile(null);
        else getCell(5).setTile(null);
        gameWorld.destroyBody(body);

    }
}
