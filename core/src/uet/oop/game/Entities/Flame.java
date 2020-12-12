package uet.oop.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static uet.oop.game.Manager.GameManager.*;


public class Flame extends Entity {

    public static final float WIDTH_FLAME = 40;
    public static final float HEIGHT_FLAME = 40;

    public static short defaultMaskBits = BOMBER_BIT | BRICK_BIT | STONE_BIT | BOSS1_BIT;


    public enum Direction {CENTER, LEFT, RIGHT, UP, DOWN};
    public Direction direction;

    public boolean isLastFrame = false;
    public Animation flameAnimation;
    public float stateTimer;

    /**
     * constructor of flame.
     * @param isLastFrame ktra co phai flame cuoi cung khong(do flame cuoi khac voi flame con lai)
     * @param posX vi tri flame
     * @param posY vi tri flame
     */
    public Flame(Bomb bomb, TextureAtlas textureAtlas,Direction direction, boolean isLastFrame, float posX, float posY) {
        this.gameWorld = bomb.gameWorld;
        this.direction = direction;
        this.isLastFrame = isLastFrame;
        stateTimer = 0;

        createAnimation(textureAtlas);

        defineCharacter(bomb, posX, posY);
        setBounds(getX(), getY(), WIDTH_FLAME/PPM, HEIGHT_FLAME/PPM);
        setPosition(getPosAnimationX(), getPosAnimationY());
    }

    public void createAnimation(TextureAtlas textureAtlas) {
        TextureRegion textureRegion = new TextureRegion(textureAtlas.findRegion("Explosion"));
        Array<TextureRegion> frames = new Array<>();

        switch (direction) {
            case CENTER:
                for (int i = 0; i < 5; i++) {
                    frames.add(new TextureRegion(textureRegion, i * 16, 16, 16, 16));
                }
                flameAnimation = new Animation(0.15f, frames);
                break;
            case UP:
                for (int j = 0; j < 5; j++) {
                    if (isLastFrame)
                        frames.add(new TextureRegion(textureRegion, j * 16, 0, 16, 16));
                    else
                        frames.add(new TextureRegion(textureRegion, j * 16, 16 * 2, 16, 16));
                }
                flameAnimation = new Animation(0.15f, frames);
                break;
            case DOWN:
                for (int j = 0; j < 5; j++) {
                    if (isLastFrame)
                        frames.add(new TextureRegion(textureRegion, j * 16, 16 * 3, 16, 16));
                     else
                         frames.add(new TextureRegion(textureRegion, j * 16, 16 * 2, 16, 16));
                }
                flameAnimation = new Animation(0.15f, frames);
                break;
            case LEFT:
                for (int j = 0; j < 5; j++) {
                    if (isLastFrame)
                        frames.add(new TextureRegion(textureRegion, j * 16, 16 * 6, 16, 16));
                    else
                        frames.add(new TextureRegion(textureRegion, j * 16, 16 * 4, 16, 16));
                }
                flameAnimation = new Animation(0.15f, frames);
                break;
            case RIGHT:
                for (int j = 0; j < 5; j++) {
                    if (isLastFrame)
                        frames.add(new TextureRegion(textureRegion, j * 16, 16 * 5, 16, 16));
                    else
                        frames.add(new TextureRegion(textureRegion, j * 16, 16 * 4, 16, 16));
                }
                flameAnimation = new Animation(0.15f, frames);
                break;
        }
    }

    public void defineCharacter(Bomb bomb, float posX, float posY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(posX, posY);
        body = gameWorld.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.2f, 0.2f);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = FLAME_BIT;
        fixtureDef.filter.maskBits = defaultMaskBits;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);

    }

    public void draw(Batch batch) {
        stateTimer += Gdx.graphics.getDeltaTime();
        batch.draw(flameAnimation.getKeyFrame(stateTimer, true), getPosAnimationX(),
                getPosAnimationY(), WIDTH_FLAME / PPM, HEIGHT_FLAME / PPM);
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void dispose() {

    }
}
