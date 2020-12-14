package uet.oop.game.Entities.AnimateEntities.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import uet.oop.game.Entities.AnimateEntities.Enemies.Enemy;

import static uet.oop.game.Manager.GameManager.*;
import static uet.oop.game.Manager.GameManager.BOMBER_BIT;

public class Ghost extends Enemy {
    public Ghost(World gameWorld, TiledMap map, TextureAtlas textureAtlas, int x, int y) {
        super(gameWorld, map, x, y);
        this.textureAtlas = textureAtlas;

        bossDown = new TextureRegion(textureAtlas.findRegion("quaivat 3_down"));
        bossLeft = new TextureRegion(textureAtlas.findRegion("quaivat 3_left"));
        bossRight = new TextureRegion(textureAtlas.findRegion("quaivat 3_right"));
        bossUp = new TextureRegion(textureAtlas.findRegion("quaivat 3_up"));

        region = bossRight;

        setBounds(43, 12, 43 / PPM, 43 / PPM);
        setRegion(region);
        body.setLinearVelocity(velocity);
    }

    @Override
    public void defineEnemy(int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(21 / PPM);

        fixtureDef.filter.categoryBits = BOSSMINI_BIT;
        fixtureDef.filter.maskBits =  BOMB_BIT | STONE_BIT | BOMBER_BIT | FLAME_BIT;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }
    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(region);
        if(body.getLinearVelocity().y==0 && body.getLinearVelocity().x==0) {
            move = false;
        }
        setState();
        //getRandomWalkingState();
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void dispose() {

    }
}
