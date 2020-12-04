package uet.oop.game.Entities;

import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.BombermanGame;

public class Bomber {
    public World world;
    public Body bomberBody;
    public Bomber(World world){
        this.world = world;
        defineCharacter();
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
