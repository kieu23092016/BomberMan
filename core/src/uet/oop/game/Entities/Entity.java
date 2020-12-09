package uet.oop.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Entity extends Sprite {
    public Body body;
    public World gameWorld;

    public Entity(TextureRegion region) {
        super(region);
    }
}
