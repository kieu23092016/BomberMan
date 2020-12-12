package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Stack;

import static uet.oop.game.Manager.GameManager.*;

public class Boss1 extends Enemy {
    public Animation animation;
    private TextureAtlas textureAtlas;
    private TextureRegion bossUp;
    private TextureRegion bossDown;
    private TextureRegion bossRight;
    private TextureRegion bossLeft;
    private TextureRegion region;
    private boolean runRight = false;
    private boolean runUp = false;
    private boolean runLeft = false;
    private boolean runDown = false;


    public enum State {
        WALKING_UP,
        WALKING_DOWN,
        WALKING_LEFT,
        WALKING_RIGHT,
        ATTACKING_UP,
        ATTACKING_DOWN,
        ATTACKING_LEFT,
        ATTACKING_RIGHT,
        DAMAGED,
        DYING
    }

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

        setBounds(43, 12, 43 / PPM, 43 / PPM);
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
        fixtureDef.filter.maskBits = BRICK_BIT | BOMB_BIT | STONE_BIT | BOMBER_BIT;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
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
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(region);
        if(body.getLinearVelocity().y==0 && body.getLinearVelocity().x==0) {
            move = false;
        }
        setState();
        //getRandomWalkingState();
        handleBasic();
    }
    private boolean move = true;
    public void setState() {
        while (!move) {
            //System.out.println(move);
            int currentState = getRandomWalkingState();
            /*switch (currentState) {
                case 1:
                    turnUp();
                case 2:
                    turnDown();

                case 3:
                    turnLeft();

                case 4:
                    turnRight();
            }*/
            if(currentState == 1) turnUp();
            else if(currentState == 2) turnDown();
            else if(currentState == 3) turnLeft();
            else turnRight();
        }
    }
    public  void turnUp(){
        move = true;
        region=bossUp;
        body.applyLinearImpulse(new Vector2(0.0f, 0.8f), body.getWorldCenter(), true);;
        System.out.println("turnUp");
    }
    public  void turnDown(){
        move = true;
        region=bossDown;
        body.applyLinearImpulse(new Vector2(0.0f, -0.4f), body.getWorldCenter(), true);;
        System.out.println("turnDown");
//        if(body.getLinearVelocity().y==0){
//            move = false;
//        }
    }
    public  void turnLeft(){
        body.applyLinearImpulse(new Vector2(-0.4f, 0), body.getWorldCenter(), true);;
        move = true;
        region=bossLeft;
        System.out.println("Left");
//        if(body.getLinearVelocity().x==0){
//            move = false;
//        }
    }
    public  void turnRight(){
        move = true;
        region=bossRight;
        // body.setLinearVelocity(0.4f, 0.0f);
        body.applyLinearImpulse(new Vector2(0.4f, 0), body.getWorldCenter(), true);;
        System.out.println("Right");
//        if(body.getLinearVelocity().x==0){
//            move = false;
//        }
    }
    public void handleBasic() {
//        if (body.getLinearVelocity().x > 0) {
//            region = bossRight;
//            runRight = true;
//        }
//        else if (body.getLinearVelocity().x == 0 && runRight == true) {
//            body.setLinearVelocity(0,-0.4f);
//            region = bossRight;
//        }
        /*else if (body.getLinearVelocity().x == 0 && runRight == false) {
            body.applyLinearImpulse(new Vector2(0.4f, 0), body.getWorldCenter(), true);
            region = bossRight;
        }
        else if (body.getLinearVelocity().x < 0) {
            region = bossLeft;
            runRight = false;
        }
        else if (body.getLinearVelocity().y == 0 && runUp == true) {
            body.applyLinearImpulse(new Vector2(0, 0.4f), body.getWorldCenter(), true);
            region = bossDown;
            System.out.println("UP");
            runUp = false;
        }
        else if (body.getLinearVelocity().y == 0 && runUp == false) {
            body.applyLinearImpulse(new Vector2(0, -0.4f), body.getWorldCenter(), true);
            region = bossUp;
            System.out.println("Down");
            runUp = true;
        }
        if ((body.getLinearVelocity().x == 0 && runUp==false && runDown == false) || (body.getLinearVelocity().y == 0 && runRight==false && runLeft==false)) {
            setState();
        }*/
    }

    public int getRandomWalkingState() {
        // define the range
        int max = 4;
        int min = 1;
        int range = max - min + 1;
        int rand = 0;
        // generate random numbers within 1 to 10
        //for (int i = 0; i < 4; i++) {
        rand = (int) (Math.random() * range) + min;
        // Output is different everytime this code is executed
        //System.out.println(rand);
        // }
        System.out.println(rand);
        return rand;
    }
}
