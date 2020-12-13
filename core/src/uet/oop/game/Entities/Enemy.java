package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    protected TextureRegion bossDown;
    protected TextureRegion bossRight;
    protected TextureRegion bossLeft;
    protected TextureRegion bossUp;
    protected TextureRegion region;
    protected TextureAtlas textureAtlas;

    public Enemy(World gameWorld, TiledMap map){
        this.gameWorld = gameWorld;
        this.map = map;
        velocity = new Vector2(0.4f,0);
        defineEnemy();
    }
    public abstract void defineEnemy();
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
        //System.out.println(rand);
        return rand;
    }
    protected boolean move = true;
    public void setState() {
        while (!move) {
            int currentState = getRandomWalkingState();
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
        //System.out.println("turnUp");
    }
    public  void turnDown(){
        move = true;
        region=bossDown;
        body.applyLinearImpulse(new Vector2(0.0f, -0.4f), body.getWorldCenter(), true);;
       // System.out.println("turnDown");
//        if(body.getLinearVelocity().y==0){
//            move = false;
//        }
    }
    public  void turnLeft(){
        body.applyLinearImpulse(new Vector2(-0.4f, 0), body.getWorldCenter(), true);;
        move = true;
        region=bossLeft;
        //System.out.println("Left");
//        if(body.getLinearVelocity().x==0){
//            move = false;
//        }
    }
    public  void turnRight(){
        move = true;
        region=bossRight;
        // body.setLinearVelocity(0.4f, 0.0f);
        body.applyLinearImpulse(new Vector2(0.4f, 0), body.getWorldCenter(), true);;
       // System.out.println("Right");
//        if(body.getLinearVelocity().x==0){
//            move = false;
//        }
    }
}
