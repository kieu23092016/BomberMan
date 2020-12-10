package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static uet.oop.game.Manager.GameManager.PPM;

public class Stone extends Entity {

    public Stone(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
    }
}
