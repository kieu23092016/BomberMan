package uet.oop.game.Character;

import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.Bomberman;

public class Bomber {
    public World world;
    public Body b2body;
    public Bomber(World world){
        this.world = world;
        defineCharacter();
    }
    public void defineCharacter(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32, 32);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
