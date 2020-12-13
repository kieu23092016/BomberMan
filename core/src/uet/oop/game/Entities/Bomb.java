package uet.oop.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.game.Manager.GameManager.*;

public class Bomb extends Entity {

    public Bomber player;
    public List<Flame> explosion = new ArrayList<Flame>();
    public int maxLength_flame=1;

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;
    public static final float RADIUS_BODY = 10;

    public enum State {NORMAL, EXPLODED}
    public State state;

    public float timeToExplode = 120; //thời gian hẹn để bom nổ
    public float timeExploding = 100; //thời gian nổ
    public boolean canExplodeThrough = false;

    public boolean isExploded = false;

    public float stateTimer; //biến này dùng để chạy animation.
    private TextureAtlas bombAtlas;
    public Animation normalAnimation;

    Vector2 fromV = new Vector2();
    Vector2 toV = new Vector2();
    FixtureDef fdef;
    private boolean checkCanExplodeThrough(Vector2 fromV, Vector2 toV) {
        canExplodeThrough = true;
        System.out.println(fromV.x + " x1"+fromV.y+"y1"+toV.x+"x2"+toV.y+"y2");
        RayCastCallback rayCastCallback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getFilterData().categoryBits == STONE_BIT) {
                    canExplodeThrough = false;
                    return 0;
                }

                if (fixture.getFilterData().categoryBits == BRICK_BIT) {
                    canExplodeThrough = false;

                    return 0;
                }
                return 0;
            }
        };
        gameWorld.rayCast(rayCastCallback, fromV, toV);
        return canExplodeThrough;
    }

    public Bomb(Bomber player, TextureAtlas bombAtlas) {

        this.player = player;
        gameWorld = player.gameWorld;
        stateTimer = 0;
        this.bombAtlas = bombAtlas;
        state = State.NORMAL;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(bombAtlas.findRegion("Bomb"), i * 16, 0, 16, 16));
        }
        normalAnimation = new Animation(0.1f, frames);


        defineCharacter();
        exploded(getPosAnimationX(), getPosAnimationY());

        setBounds(player.getX(), player.getY(), WIDTH / PPM, HEIGHT / PPM);
        setPosition(getPosAnimationX(), getPosAnimationY());

    }

    public void exploded(float x, float y) {

        float distance = 45/PPM;
        boolean isLast = false;
        //center flame
        explosion.add(new Flame(this, bombAtlas, Flame.Direction.CENTER, false, getX(), getY()));

        // up
        for (int i = 0; i < maxLength_flame; i++) {
            if (!checkCanExplodeThrough(fromV.set(x, y + distance*i), toV.set(x, y + (i+1)*distance))) {
                break;
            }
            if (i == maxLength_flame - 1) isLast = true;
            explosion.add(new Flame(this, bombAtlas, Flame.Direction.UP, isLast, x, y+(i+1)*distance));
        }
        isLast = false;

        // down
        for (int i = 0; i < maxLength_flame; i++) {
            if (!checkCanExplodeThrough(fromV.set(x, y - i*distance), toV.set(x, y -(i+1)*distance ))) {
                break;
            }
            if (i == maxLength_flame - 1) isLast = true;
            explosion.add(new Flame(this, bombAtlas, Flame.Direction.DOWN, isLast, x, y - (i + 1)*distance));
        }
        isLast = false;

        // left
        for (int i = 0; i < maxLength_flame; i++) {
            if (!checkCanExplodeThrough(fromV.set(x - i*distance, y), toV.set(x - (i + 1)*distance, y))) {
                break;
            }
            if (i == maxLength_flame - 1) isLast = true;
            explosion.add(new Flame(this, bombAtlas, Flame.Direction.LEFT, isLast, x - (i + 1)*distance, y));
        }
        isLast = false;

        // right
        for (int i = 0; i < maxLength_flame; i++) {
            if (!checkCanExplodeThrough(fromV.set(x + i*distance, y), toV.set(x + (i + 1)*distance, y))) {
                break;
            }
            if (i == maxLength_flame - 1) isLast = true;
            explosion.add(new Flame(this, bombAtlas, Flame.Direction.RIGHT, isLast, x + (i + 1)*distance, y));
        }
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
            if (timeExploding > 0) timeExploding--;
        }
    }

    public void draw(Batch batch) {
        stateTimer += Gdx.graphics.getDeltaTime();
        if (timeToExplode>0) {
            batch.draw(normalAnimation.getKeyFrame(stateTimer, true), getPosAnimationX(), getPosAnimationY(), WIDTH / PPM, HEIGHT / PPM);
        }
        if (timeToExplode==0 && timeExploding>0)
            for (int i = 0; i<explosion.size(); i++) {
                explosion.get(i).draw(batch);
            }
        //fdef.filter.categoryBits = DESTROY_BIT;
        //System.out.println(fdef.filter.categoryBits +" mask"+fdef.filter.maskBits);
    }

    public void defineCharacter() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(player.getX(), player.getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = gameWorld.createBody(bodyDef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS_BODY / PPM);

        fdef.filter.categoryBits = BOMB_BIT;
        //fdef.filter.maskBits = BOSS1_BIT|BOMBER_BIT;
        fdef.shape = shape;
        body.createFixture(fdef);
    }

    @Override
    public void dispose() {
        gameWorld.destroyBody(body);
        this.body = null;
        this.normalAnimation = null;
    }
}
