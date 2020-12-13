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

        int cDef = fixture1.getFilterData().categoryBits | fixture2.getFilterData().categoryBits;
        switch (cDef) {
            case BOMBER_BIT | FLAME_BIT:
                if (fixture1.getFilterData().categoryBits == BOMBER_BIT)
                {
                    ((Entity) fixture2.getUserData()).onHeadHit();
                    //Gdx.app.log("COLLIDE","bOMBER");
                }
                else
                {
                    ((Entity) fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("COLLIDE","BOMBER");

                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("end contact", "end");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        float platform_y;
        float player_y;

        if(fixtureA.getFilterData().categoryBits ==BOMBER_BIT
                && fixtureB.getFilterData().categoryBits == BOMB_BIT ||
                fixtureA.getFilterData().categoryBits == BOMBER_BIT
                        && fixtureB.getFilterData().categoryBits == BOMB_BIT ) {

            //Gdx.app.log("Player Y ", "" + player_y);

            platform_y = fixtureA.getBody().getPosition().y;
            player_y = fixtureB.getBody().getPosition().y;

            float platform_x = fixtureA.getBody().getPosition().x;
            float player_x = fixtureB.getBody().getPosition().x;

            System.out.println(fixtureA.getShape().getRadius() + " r "+fixtureB.getShape().getRadius());
            double DIS = fixtureA.getShape().getRadius() + fixtureB.getShape().getRadius();
            System.out.println(fixtureA.getShape().getRadius() + " r "+fixtureB.getShape().getRadius()+" DIS"+DIS);

            if (fixtureA.getFilterData().categoryBits == BOMB_BIT) {
                platform_y = fixtureA.getBody().getPosition().y;
                platform_x = fixtureA.getBody().getPosition().x;
                player_y = fixtureB.getBody().getPosition().y;
                player_x = fixtureB.getBody().getPosition().x;
            } else if (fixtureA.getFilterData().categoryBits == BOMBER_BIT) {
                player_y = fixtureA.getBody().getPosition().y;
                platform_y = fixtureB.getBody().getPosition().y;
                platform_x = fixtureB.getBody().getPosition().x;
                player_x = fixtureA.getBody().getPosition().x;
            }
            double distance = Math.sqrt((platform_x-player_x)*(platform_x-player_x)
                    + (platform_y-player_y)*(platform_y-player_y)) ;
            System.out.println(distance+"dis");
            if (distance == 0.0) {  //the player is below platform
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
                System.out.println("collide"+distance);

            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
