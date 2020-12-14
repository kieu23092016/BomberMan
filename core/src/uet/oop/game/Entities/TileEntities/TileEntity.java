package uet.oop.game.Entities.TileEntities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.Entities.Entity;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.PPM;

public abstract class TileEntity extends Entity {
    protected TiledMap map;
    protected Rectangle rectangle;
    protected Fixture fixture;
    public PlayScreen playScreen;

    public TileEntity (PlayScreen playScreen, TiledMap map, Rectangle rectangle){
        this.playScreen = playScreen;
        gameWorld = playScreen.getGameWorld();
        this.map = map;
        this.rectangle = rectangle;

        defineCharacter();
    }

    public void defineCharacter() {
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

    public void setCategory(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell(int index){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(index);
        return layer.getCell((int) (body.getPosition().x*PPM/45), (int)(body.getPosition().y*PPM/45));
    }
}
