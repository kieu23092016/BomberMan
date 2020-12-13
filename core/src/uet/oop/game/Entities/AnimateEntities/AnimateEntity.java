package uet.oop.game.Entities.AnimateEntities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import uet.oop.game.Entities.Entity;

public abstract class AnimateEntity extends Entity {

    public AnimateEntity() {

    }

    public AnimateEntity(TextureRegion region) {
        super(region);
        /*BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();*/
    }
}
