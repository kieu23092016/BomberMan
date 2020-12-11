package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.*;


public class Bomber extends Entity {
    @Override
    public void onHeadHit() {

    }
    public enum State{DEAD, UP, DOWN, LEFT, RIGHT};
    public State currentState;
    public State previousState;
    private TextureRegion player_left;
    private TextureRegion bomberLeft;
    private TextureRegion bomberUp;
    private TextureRegion bomberRight;
    private TextureRegion bomberDown;
    private float stateTimer;
    private  boolean runningRight;

    public Bomber(PlayScreen playScreen, TextureAtlas textureAtlas){
        super(textureAtlas.findRegion("bebong_down"));
        this.gameWorld = playScreen.getGameWorld();
        /*currentState = State.LEFT;
        previousState = State.LEFT;
        stateTimer = 0;
        runningRight = true;*/

        /*Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i<4; i++)
            frames.add(new TextureRegion(textureAtlas.findRegion("player_left"+i)));
            bomberLeft = new Animation(0.1f, frames);
            frames.clear();
        for(int i = 1; i<4; i++)
            frames.add(new TextureRegion(textureAtlas.findRegion("player_up"+i)));
            bomberUp = new Animation(0.1f, frames);
            frames.clear();
        for(int i = 1; i<4; i++)
            frames.add(new TextureRegion(textureAtlas.findRegion("player_down"+i)));
            bomberDown = new Animation(0.1f, frames);
        frames.clear();
        for(int i = 1; i<4; i++)
            frames.add(new TextureRegion(textureAtlas.findRegion("player_right"+i)));
        bomberRight= new Animation(0.1f, frames);
        frames.clear();*/

        defineCharacter();
        bomberLeft = new TextureRegion(getTexture(), 45,0,45,56);
        bomberRight = new TextureRegion(getTexture(), 90,0,45,56);
        bomberDown= new TextureRegion(getTexture(), 0,0,45,56);
        bomberUp = new TextureRegion(getTexture(), 135,2,45,56);

        setBounds(45,0,45/PPM,56/PPM);
        setRegion(bomberLeft);
    }
    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        TextureRegion region;
        if(body.getLinearVelocity().y>0)
            region = bomberUp;
        else if(body.getLinearVelocity().y<0)
            region = bomberDown;
        else if(body.getLinearVelocity().x <0)
            region = bomberLeft;
        else region = bomberRight;
        return region;
    }
    public void defineCharacter(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/ PPM, 32/ PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(16/ PPM);

        fdef.filter.categoryBits = BOMBER_BIT;
        fdef.filter.maskBits = BRICK_BIT | STONE_BIT;
        fdef.shape = shape;
        body.createFixture(fdef);

        EdgeShape edgeUp = new EdgeShape();
        edgeUp.set(new Vector2(-8/PPM, 16/PPM), new Vector2(8/PPM, 16/PPM));
        fdef.shape = edgeUp;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("head");

        EdgeShape edgeLeft = new EdgeShape();
        edgeLeft.set(new Vector2(16/PPM, -8/PPM), new Vector2(16/PPM, 8/PPM));
        fdef.shape = edgeLeft;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("head");

        EdgeShape edgeRight = new EdgeShape();
        edgeRight.set(new Vector2(-16/PPM, -8/PPM), new Vector2(-16/PPM, 8/PPM));
        fdef.shape = edgeRight;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("head");

        EdgeShape edgeDown = new EdgeShape();
        edgeDown.set(new Vector2(-8/PPM, -16/PPM), new Vector2(8/PPM, -16/PPM));
        fdef.shape = edgeDown;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("head");
    }
}
