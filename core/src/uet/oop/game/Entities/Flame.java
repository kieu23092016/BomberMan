package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import javafx.animation.Animation;

import static uet.oop.game.Manager.GameManager.PPM;

public class Flame extends Entity {

    public Animation verticalAnimation;
    public Animation horizontalAnimation;

    public float stateTimer;


    public Flame(Bomb bomb, int direction) {
        this.gameWorld = bomb.gameWorld;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();


        defineCharacter(bomb);
    }

    public void defineCharacter(Bomb bomb) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(bomb.getX(),bomb.getY());
        body = gameWorld.createBody(bodyDef);

        polygonShape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);

    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void dispose() {

    }
}
