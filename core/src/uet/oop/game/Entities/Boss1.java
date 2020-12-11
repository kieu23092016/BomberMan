package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static uet.oop.game.Manager.GameManager.*;

public class Boss1 extends Enemy {
    public Animation animation;
    private TextureAtlas textureAtlas;
    private TextureRegion bossUp;
    private TextureRegion bossDown;
    private TextureRegion bossRight;
    private TextureRegion bossLeft;
    private TextureRegion region;
    private boolean runLeft = false;
    private boolean runRight = false;


    public Boss1(World gameWorld, TiledMap map, TextureAtlas textureAtlas) {
        super(gameWorld, map);
        this.textureAtlas = textureAtlas;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        //for (int i = 0; i < 4; i++)
        bossDown = new TextureRegion(textureAtlas.findRegion("quaivat1_down"));
        frames.add(bossDown);
        animation = new Animation(0.1f, frames);

        bossLeft = new TextureRegion(textureAtlas.findRegion("quaivat1_left"));
        bossRight = new TextureRegion(textureAtlas.findRegion("quaivat1_right"));
        bossUp = new TextureRegion(textureAtlas.findRegion("quaivat1_up"));
        region = bossRight;

        setBounds(43,12,43/PPM,43/PPM);
        setRegion(region);
        body.setLinearVelocity(velocity);
    }

    @Override
    public void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(600 / PPM, 600 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22 / PPM);

        fixtureDef.filter.categoryBits = BOSS1_BIT;
        fixtureDef.filter.maskBits = BRICK_BIT | STONE_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        EdgeShape edgeUp = new EdgeShape();
        edgeUp.set(new Vector2(-8/PPM, 16/PPM), new Vector2(8/PPM, 16/PPM));
        fixtureDef.shape = edgeUp;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape edgeLeft = new EdgeShape();
        edgeLeft.set(new Vector2(16/PPM, -8/PPM), new Vector2(16/PPM, 8/PPM));
        fixtureDef.shape = edgeLeft;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape edgeRight = new EdgeShape();
        edgeRight.set(new Vector2(-16/PPM, -8/PPM), new Vector2(-16/PPM, 8/PPM));
        fixtureDef.shape = edgeRight;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape edgeDown = new EdgeShape();
        edgeDown.set(new Vector2(-8/PPM, -16/PPM), new Vector2(8/PPM, -16/PPM));
        fixtureDef.shape = edgeDown;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
    }
    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        handleBasic();
        setRegion(region);
    }
    public void handleBasic(){
        if(body.getLinearVelocity().x >0){
            region = bossRight;
            runRight = true;
        }
        if (body.getLinearVelocity().x == 0 && runRight == true) {
            body.applyLinearImpulse(new Vector2(-0.4f,0), body.getWorldCenter(), true);
            region = bossRight;
        }
        if (body.getLinearVelocity().x == 0 && runRight == false) {
            body.applyLinearImpulse(new Vector2(0.4f,0), body.getWorldCenter(), true);
            region = bossRight;
        }
        if(body.getLinearVelocity().x < 0){
            region = bossLeft;
            runRight = false;
        }
    }
}
