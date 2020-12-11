package uet.oop.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.Entities.Enemy;
import uet.oop.game.Entities.Entity;

import static uet.oop.game.Manager.GameManager.*;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixture1 = contact.getFixtureA();
        Fixture fixture2 = contact.getFixtureB();
        if (fixture1.getUserData() == "head" || fixture2.getUserData() == "head") {
            Fixture head = fixture1.getUserData() == "head" ? fixture1 : fixture2;
            Fixture object = head == fixture1 ? fixture2 : fixture1;
            if (object.getUserData() != null && Entity.class.isAssignableFrom(object.getUserData().getClass())) {
                ((Entity) object.getUserData()).onHeadHit();
            }
        }
        int cDef = fixture1.getFilterData().categoryBits | fixture2.getFilterData().categoryBits;

        switch (cDef) {
            case BOMBER_BIT | BRICK_BIT:
                if (fixture1.getFilterData().categoryBits == BOMBER_BIT)
                {
                    ((Entity) fixture2.getUserData()).onHeadHit();
                    Gdx.app.log("COLLIDE","bOMBER");
                }
                else
                {
                    ((Entity) fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("COLLIDE","BOMBER");

                }
                break;
//            case BOSS1_BIT | (STONE_BIT|BRICK_BIT):
//                if (fixture1.getFilterData().categoryBits == BOSS1_BIT)
//                {
//                    ((Enemy) fixture1.getUserData()).reversePath(true, false);
//                    Gdx.app.log("COLLIDE","ENEMY");
//                }
//                else if (fixture2.getFilterData().categoryBits == BOSS1_BIT)
//                {
//                    //((Enemy) fixture2.getUserData()).reversePath(true, false);
//                    Gdx.app.log("COLLIDE","ENEMY2");
//                }
//                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("end contact", "end");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
