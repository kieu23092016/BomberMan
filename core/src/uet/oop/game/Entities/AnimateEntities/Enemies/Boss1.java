package uet.oop.game.Entities.AnimateEntities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import uet.oop.game.BombermanGame;
import uet.oop.game.Entities.AnimateEntities.BombManager.Bomb;
import uet.oop.game.Entities.AnimateEntities.Enemies.Enemy;

import static uet.oop.game.Manager.GameManager.*;

public class Boss1 extends Enemy {
    //public float timeToDisappear = 120;
    public float timeDisappering= 100;
    public float countDown = timeDisappering;


    public Animation animation;
    private boolean isLived = true;
    public Boss1(World gameWorld, TiledMap map, TextureAtlas textureAtlas, int x, int y) {
        super(gameWorld, map, x, y);
        this.textureAtlas = textureAtlas;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        //for (int i = 0; i < 4; i++)
        bossDown = new TextureRegion(textureAtlas.findRegion("quaivat1_down"));
        frames.add(bossDown);
        animation = new Animation(0.1f, frames);

        bossLeft = new TextureRegion(textureAtlas.findRegion("quaivat1_left"));
        bossRight = new TextureRegion(textureAtlas.findRegion("quaivat1_right"));
        bossUp = new TextureRegion(textureAtlas.findRegion("quaivat1_up"));
        bossDead = new TextureRegion(new Texture("map/Images/Images/ghost.png"));
        region = bossRight;

        setBounds(43, 12, 43 / PPM, 43 / PPM);
        setRegion(region);
        body.setLinearVelocity(velocity);
    }

    @Override
    public void defineEnemy(int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x/ PPM,y / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(21 / PPM);

        fixtureDef.filter.categoryBits = BOSS1_BIT;
        fixtureDef.filter.maskBits = BRICK_BIT | BOMB_BIT | STONE_BIT | BOMBER_BIT | FLAME_BIT ;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        /*fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        EdgeShape edgeUp = new EdgeShape();
        edgeUp.set(new Vector2(-8 / PPM, 16 / PPM), new Vector2(8 / PPM, 16 / PPM));
        fixtureDef.shape = edgeUp;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape edgeLeft = new EdgeShape();
        edgeLeft.set(new Vector2(16 / PPM, -8 / PPM), new Vector2(16 / PPM, 8 / PPM));
        fixtureDef.shape = edgeLeft;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape edgeRight = new EdgeShape();
        edgeRight.set(new Vector2(-16 / PPM, -8 / PPM), new Vector2(-16 / PPM, 8 / PPM));
        fixtureDef.shape = edgeRight;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape edgeDown = new EdgeShape();
        edgeDown.set(new Vector2(-8 / PPM, -16 / PPM), new Vector2(8 / PPM, -16 / PPM));
        fixtureDef.shape = edgeDown;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);*/
    }


    public void update(float dt) {
        if(isLived) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(region);
            if (body.getLinearVelocity().y == 0 && body.getLinearVelocity().x == 0) {
                move = false;
            }
            setState();
            //getRandomWalkingState();
        }
        else{
            if (timeDisappering > 0) timeDisappering--;
        }
    }

    @Override
    public void onHeadHit() {
        isLived = false;
        if (timeDisappering>0) {
            setRegion(bossDead);
        }
    }

    /*public void draw(Batch batch) {
     *//*if (isLived)
        //setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        batch.draw(region, body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 45/PPM, 45/PPM);
        else  {*//*
            if (timeDisappering>0) {
                batch.draw(region, body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 45/PPM, 45/PPM);
            }
        //}
    }*/
    public TextureRegion getRegion(){
        return region;
    }
    @Override
    public void dispose() {
        gameWorld.destroyBody(body);
    }
}
