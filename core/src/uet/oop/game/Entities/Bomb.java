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

public class Bomb extends Entity {

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;
    public static final float RADIUS_BODY = 10;

    public enum State {NORMAL, EXPLODED};
    public State state;

    public float timeToExplode = 120;
    public boolean isExploded = false;
    public boolean isAppeared = false;
    public Bomber player;

    public float stateTimer;

    public Animation normalAnimation;

    public Bomb(Bomber player, TextureAtlas textureAtlas) {
        // super(textureAtlas.findRegion("Bomb"));

        this.player = player;
        gameWorld = player.gameWorld;
        stateTimer = 0;
        state = State.NORMAL;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(textureAtlas.findRegion("Bomb"), i * 16, 0, 16, 16));
        }
        normalAnimation = new Animation(0.1f, frames);

        defineCharacter();
        setBounds(player.getX(), player.getY(), WIDTH / PPM, HEIGHT / PPM);
        setPosition(getPosAnimationX(), getPosAnimationY());

    }



    public TextureRegion getFrame(float dt) {
        //stateTimer += Gdx.graphics.getDeltaTime();
        TextureRegion textureRegion = null;
        switch (state) {
            case EXPLODED:
                //
                break;
            case NORMAL:
            default:
                textureRegion = normalAnimation.getKeyFrame(stateTimer, true);
                break;

        }
        return textureRegion;
    }

    @Override
    public void onHeadHit() {

    }

    public void update(float dt) {
        if (timeToExplode > 0)
            timeToExplode--;
        else {
            state = State.EXPLODED;
        }
    }

    public void draw(Batch batch, float dt) {
        stateTimer += Gdx.graphics.getDeltaTime();
        batch.draw(normalAnimation.getKeyFrame(stateTimer, true), getPosAnimationX(), getPosAnimationY(), WIDTH / PPM, HEIGHT / PPM);
    }

    public void defineCharacter() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(player.getX(), player.getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = gameWorld.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS_BODY/ PPM);

        fdef.shape = shape;
        body.createFixture(fdef);

    }

    @Override
    public void dispose() {
        this.body = null;
        this.normalAnimation = null;
    }
}
