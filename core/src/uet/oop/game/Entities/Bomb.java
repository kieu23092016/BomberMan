package uet.oop.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import static uet.oop.game.Manager.GameManager.PPM;

public class Bomb extends Entity{

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;


    public float timeToExplode = 120;
    public boolean isExploded = false;
    public boolean isAppeared = false;
    public Bomber player;

    public float stateTimer;

    public Animation normalAnimation;

    public Bomb(Bomber player, TextureAtlas textureAtlas) {
        super(textureAtlas.findRegion("Bomb"));

        this.player = player;
        gameWorld = player.gameWorld;
        stateTimer = 0;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(textureAtlas.findRegion("Bomb"), i * 16, 0, 16, 16));
        }
        normalAnimation = new Animation(0.1f, frames);

        defineCharacter();
        setBounds(player.getBomberX(), player.getBomberY(), WIDTH/PPM, HEIGHT/PPM);
        setPosition(getPosAnimationX(), getPosAnimationY());

    }

    public float getPosAnimationX() {
        return body.getPosition().x-getWidth()/2;
    }

    public float getPosAnimationY() {
        return body.getPosition().y-getHeight()/2;
    }
    public TextureRegion getFrame(float dt) {
        stateTimer += Gdx.graphics.getDeltaTime();
        return normalAnimation.getKeyFrame(stateTimer, true);
    }
    @Override
    public void onHeadHit() {

    }

    public void update(float dt) {
        if (timeToExplode>0)
            timeToExplode--;
        else isExploded = true;


        //setRegion(getFrame(dt));
    }

    public void draw(Batch batch, float dt) {
        stateTimer+=Gdx.graphics.getDeltaTime();
        batch.draw(normalAnimation.getKeyFrame(stateTimer, true), getPosAnimationX(), getPosAnimationY(), WIDTH/PPM, HEIGHT/PPM);
    }
    public void defineCharacter() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(player.getBomberX(), player.getBomberY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = gameWorld.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ PPM);

        fdef.shape = shape;
        body.createFixture(fdef);

    }

}
