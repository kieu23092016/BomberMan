package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Enemy extends Sprite {
    public Body body;
    protected World gameWorld;
    protected TiledMap map;
    public Fixture fixture;
    public FixtureDef fixtureDef;
    public Vector2 velocity;
    public Enemy(World gameWorld, TiledMap map){
        this.gameWorld = gameWorld;
        this.map = map;
        velocity = new Vector2(0.4f,0);
        defineEnemy();
    }
    public abstract void defineEnemy();
    public void reversePath(boolean x, boolean y){
        if(x) velocity.x = -velocity.x;
        if(y) velocity.y = -velocity.y;
    }
}
