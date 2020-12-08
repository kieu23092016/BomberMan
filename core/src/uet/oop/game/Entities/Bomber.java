package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import uet.oop.game.BombermanGame;


public class Bomber extends Sprite {
    public enum State{Dead, Up, Down, Left, Right};
    public State currentState;
    public State previousState;
    public World world;
    public Body bomberBody;
    private TextureRegion player_left;
    private Animation bomberLeft;
    private Animation bomberUp;
    private Animation bomberRight;
    private Animation bomberDown;
    private float stateTimer;
    private  boolean runningRight;

    public Bomber(World world, TextureAtlas textureAtlas){
        super(textureAtlas.findRegion("player_left2"));
        this.world = world;
        currentState = State.Left;
        previousState = State.Left;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
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
        frames.clear();

        defineCharacter();
        player_left = new TextureRegion(getTexture(), 74,2,16,16);
        setBounds(16,0,32/BombermanGame.PPM,32/BombermanGame.PPM);
        setRegion(player_left);
    }
    public void update(float dt){
        setPosition(bomberBody.getPosition().x-getWidth()/2, bomberBody.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case Up:
                region = (TextureRegion) bomberUp.getKeyFrame(stateTimer);
                break;
            case Down:
                region = (TextureRegion) bomberDown.getKeyFrame(stateTimer);
            case Left:
                region = (TextureRegion) bomberLeft.getKeyFrame(stateTimer);
            case Right:
                region = (TextureRegion) bomberRight.getKeyFrame(stateTimer);
            default:
                region = player_left;
                break;
        }
        //if((bomberBody.getLinearVelocity().x<0 || !runningRight)&& region.isFlipX())
        stateTimer = currentState==previousState? stateTimer+dt:0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if(bomberBody.getLinearVelocity().y>0 || (bomberBody.getLinearVelocity().y<0 && previousState == State.Up))
            return State.Up;
        else if(bomberBody.getLinearVelocity().y<0)
            return State.Down;
        else if(bomberBody.getLinearVelocity().x <0)
            return State.Left;
        else return State.Right;
    }
    public void defineCharacter(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/ BombermanGame.PPM, 32/ BombermanGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bomberBody = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(16/ BombermanGame.PPM);

        fdef.shape = shape;
        bomberBody.createFixture(fdef);
    }
}
