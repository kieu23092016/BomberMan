package uet.oop.game.Entities.AnimateEntities;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.Entities.AnimateEntities.BombManager.Bomb;
import uet.oop.game.Entities.Entity;
import uet.oop.game.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.game.Manager.GameManager.*;


public class Bomber extends AnimateEntity {

    public static final int BOMBER_WIDTH = 45;
    public static final int BOMBER_HEIGHT = 56;

    // public static short bomberMaskBit = BRICK_BIT | STONE_BIT | BOSS1_BIT | FLAME_BIT;

    public float SPEED = 1f;


    private List<Bomb> bombList = new ArrayList<Bomb>();


    public enum State {DEAD, UP, DOWN, LEFT, RIGHT}

    public State currentState;
    public State previousState;
    private TextureRegion player_left;
    private TextureRegion bomberLeft;
    private TextureRegion bomberUp;
    private TextureRegion bomberRight;
    private TextureRegion bomberDown;
    private float stateTimer;
    private boolean runningRight;

    public Bomber(PlayScreen playScreen, TextureAtlas textureAtlas) {
        super(textureAtlas.findRegion("bebong_down"));
        this.gameWorld = playScreen.getGameWorld();
        currentState = State.DOWN;
        //previousState = State.LEFT;
        /*stateTimer = 0;
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
        bomberLeft = new TextureRegion(getTexture(), 45, 0, BOMBER_WIDTH, BOMBER_HEIGHT);
        bomberRight = new TextureRegion(getTexture(), 90, 0, BOMBER_WIDTH, BOMBER_HEIGHT);
        bomberDown = new TextureRegion(getTexture(), 0, 0, BOMBER_WIDTH, BOMBER_HEIGHT);
        bomberUp = new TextureRegion(getTexture(), 135, 2, BOMBER_WIDTH, BOMBER_HEIGHT);

        setBounds(45, 0, BOMBER_WIDTH / PPM, BOMBER_HEIGHT / PPM);
        setRegion(bomberLeft);
    }


    public void update(float dt) {
        setPosition(getPosAnimationX(), getPosAnimationY());
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case RIGHT:
                region = bomberRight;
                break;
            case LEFT:
                region = bomberLeft;
                break;
            case DOWN:
                region = bomberDown;
                break;
            case UP:
            default:
                region = bomberUp;
                break;
        }
        /*if (body.getLinearVelocity().y > 0 )
            region = bomberUp;
        else if (body.getLinearVelocity().y < 0)
            region = bomberDown;
        else if (body.getLinearVelocity().x < 0)
        else */
        //previousState = currentState;
        return region;
    }

    public State getState() {
        if (body.getLinearVelocity().y > 0 )
            return State.UP;
        else if (body.getLinearVelocity().y < 0 )
            return State.DOWN;
        else if (body.getLinearVelocity().x < 0 )
            return State.LEFT;
        else if (body.getLinearVelocity().x > 0)
            return State.RIGHT;
        else return currentState;
    }

    public void defineCharacter() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(96 / PPM, 632 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(16 / PPM);

        fdef.filter.categoryBits = BOMBER_BIT;
        fdef.filter.maskBits = BRICK_BIT | STONE_BIT | BOMB_BIT | BOSS1_BIT;
        fdef.shape = shape;
        body.createFixture(fdef);

        /*EdgeShape edgeUp = new EdgeShape();
        edgeUp.set(new Vector2(-8 / PPM, 16 / PPM), new Vector2(8 / PPM, 16 / PPM));
        fdef.shape = edgeUp;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);

        EdgeShape edgeLeft = new EdgeShape();
        edgeLeft.set(new Vector2(16 / PPM, -8 / PPM), new Vector2(16 / PPM, 8 / PPM));
        fdef.shape = edgeLeft;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);

        EdgeShape edgeRight = new EdgeShape();
        edgeRight.set(new Vector2(-16 / PPM, -8 / PPM), new Vector2(-16 / PPM, 8 / PPM));
        fdef.shape = edgeRight;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);

        EdgeShape edgeDown = new EdgeShape();
        edgeDown.set(new Vector2(-8 / PPM, -16 / PPM), new Vector2(8 / PPM, -16 / PPM));
        fdef.shape = edgeDown;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);*/
    }

    public void placeBomb(Batch batch) {
        Bomb bomb = new Bomb(this, bombAndItemAtlas);
        bombList.add(bomb);
        System.out.println("SIZE OF BOMB LIST IS: " + bombList.size());

    }

    public void draw(Batch batch, float dt) {
        if (bombList.size() > 0) {
            for (int i = 0; i < bombList.size(); i++) {
                bombList.get(i).draw(batch);
                bombList.get(i).update(dt);
                // System.out.println("BOMB" + " " + i + " : timeToExplode is: " + bombList.get(i).timeToExplode);

                if (bombList.get(i).timeExploding == 0) {
                    bombList.get(i).dispose();
                    bombList.remove(i);
                }
                //System.out.println("SIZE OF BOMB LIST IS: " + bombList.size());
            }
        }

        this.draw(batch);

    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void dispose() {

    }
}
