package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static uet.oop.game.Manager.GameManager.PPM;

public class Bomb extends Entity{

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;


    public float timeToExplode = 120;
    public boolean isExploded = false;
    public Bomber player;

    public float stateTimer;

    public Animation animation;
    public TextureRegion normalState;

    public Bomb(Bomber player, TextureAtlas textureAtlas) {
        super(textureAtlas.findRegion("0001"));

        this.player = player;
        gameWorld = player.gameWorld;
        stateTimer = 0;

        normalState = new TextureRegion(textureAtlas.findRegion("0001"));
        animation = new Animation(0.1f, textureAtlas.getRegions());

        defineCharacter();
        setBounds(player.getBomberX(), player.getBomberY(), WIDTH/PPM, HEIGHT/PPM);
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);

        //setRegion(normalState);
    }

    public TextureRegion getFrame(float dt) {
        stateTimer += dt;
        return animation.getKeyFrame(stateTimer, true);
    }
    @Override
    public void onHeadHit() {

    }

    public void update(float dt) {
        if (timeToExplode>0)
            timeToExplode--;
        else isExploded = true;

        setRegion(getFrame(dt));
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
