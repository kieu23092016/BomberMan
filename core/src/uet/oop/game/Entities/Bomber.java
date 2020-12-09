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
    private TextureRegion bomberLeft;
    private TextureRegion bomberUp;
    private TextureRegion bomberRight;
    private TextureRegion bomberDown;
    private float stateTimer;
    private  boolean runningRight;

    public Bomber(World world, TextureAtlas textureAtlas){
        super(textureAtlas.findRegion("bebong_down"));
        this.world = world;
        currentState = State.Left;
        previousState = State.Left;
        stateTimer = 0;
        runningRight = true;

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

        setBounds(45,0,45/BombermanGame.PPM,56/BombermanGame.PPM);
        setRegion(bomberLeft);
    }
    public void update(float dt){
        setPosition(bomberBody.getPosition().x-getWidth()/2, bomberBody.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        TextureRegion region;
        /*switch (currentState){
            case Up:
                region = bomberUp;
                break;
            case Down:
                region = bomberDown;
            case Left:
                region = bomberLeft;
            case Right:
                region = bomberRight;
            default:
                region = bomberRight;
                break;
        }
        //if((bomberBody.getLinearVelocity().x<0 || !runningRight)&& region.isFlipX())
        //stateTimer = currentState==previousState? stateTimer+dt:0;
        //previousState = currentState;*/
        if(bomberBody.getLinearVelocity().y>0)
            region = bomberUp;
        else if(bomberBody.getLinearVelocity().y<0)
            region = bomberDown;
        else if(bomberBody.getLinearVelocity().x <0)
            region = bomberLeft;
        else region = bomberRight;
        return region;
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
