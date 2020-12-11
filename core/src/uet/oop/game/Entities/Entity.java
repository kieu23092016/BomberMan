package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import javax.rmi.CORBA.Tie;

import static uet.oop.game.Manager.GameManager.PPM;

public abstract class Entity extends Sprite {
    public Body body;
    protected World gameWorld;
    protected TiledMap map;
    protected Rectangle rectangle;
    protected Fixture fixture;

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
        fixture = body.createFixture(fixtureDef);
    }
    public Entity(TextureRegion region) {
        super(region);
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
    }
    public Entity() {}
    public abstract void onHeadHit();
    public void setCategory(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell(int index){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(index);
        return layer.getCell((int) (body.getPosition().x*PPM/32), (int)(body.getPosition().y*PPM/32));
    }
}
