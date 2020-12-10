package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import javax.rmi.CORBA.Tie;

import static uet.oop.game.Manager.GameManager.PPM;

public class Entity extends Sprite {
    public Body body;
    protected World gameWorld;
    protected TiledMap map;
    protected Rectangle rectangle;

    public  Entity(World world, TiledMap map, Rectangle rectangle){
        gameWorld = world;
        this.map = map;
        this.rectangle = rectangle;

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM,
                (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
        body = gameWorld.createBody(bodyDef);

        polygonShape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
    }
    public Entity(TextureRegion region) {
        super(region);
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
    }
}
