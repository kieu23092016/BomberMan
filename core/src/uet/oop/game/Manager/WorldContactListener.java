package uet.oop.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import uet.oop.game.Entities.AnimateEntities.Bomber;
import uet.oop.game.Entities.AnimateEntities.Enemies.Boss1;
import uet.oop.game.Entities.AnimateEntities.Enemies.Ghost;
import uet.oop.game.Entities.Entity;
import uet.oop.game.Entities.Items.SpeedItem;

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
                    ((Bomber)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("bomber","flame");
                }
                else
                {
                    ((Bomber)fixture2.getUserData()).onHeadHit();
                    Gdx.app.log("flame", "bomber");

                }
                break;
            case BOMBER_BIT | BOSS1_BIT:
                if (fixture1.getFilterData().categoryBits == BOMBER_BIT)
                {
                    ((Bomber)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("bomber","boss");
                }
                else
                {
                    ((Bomber)fixture2.getUserData()).onHeadHit();
                    Gdx.app.log("boss", "bomber");

                }
                break;
            case FLAME_BIT | BOSS1_BIT:
                if (fixture1.getFilterData().categoryBits == BOSS1_BIT && fixture2.getFilterData().categoryBits == FLAME_BIT)
                {
                    ((Boss1)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("FLAME","boss");
                }
                else if (fixture1.getFilterData().categoryBits == FLAME_BIT && fixture2.getFilterData().categoryBits == BOSS1_BIT)
                {
                    Boss1 b= (Boss1)fixture2.getUserData();
                    b.onHeadHit();
                    Gdx.app.log("boss", "FLAME");
                }
                break;
            case FLAME_BIT | BOSSMINI_BIT:
                if (fixture1.getFilterData().categoryBits == BOSSMINI_BIT)
                {
                    ((Ghost)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("FLAME","boss");
                }
                else if (fixture2.getFilterData().categoryBits == BOSSMINI_BIT)
                {
                    ((Ghost)fixture2.getUserData()).onHeadHit();
                    Gdx.app.log("boss", "FLAME");
                }
                break;
            case BOMBER_BIT | BOSSMINI_BIT:
                if (fixture1.getFilterData().categoryBits == BOMBER_BIT)
                {
                    ((Bomber)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("FLAME","boss");
                }
                else if (fixture1.getFilterData().categoryBits == BOSSMINI_BIT)
                {
                    ((Bomber)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("boss", "FLAME");
                }
                break;
            case BOMBER_BIT | ITEM_BIT:
                if (fixture1.getFilterData().categoryBits == ITEM_BIT)
                {
                    ((SpeedItem)fixture1.getUserData()).isAppear = false;
                    ((SpeedItem)fixture1.getUserData()).onHeadHit();
                    Gdx.app.log("bomber","item");
                }
                else
                {
                    ((SpeedItem)fixture2.getUserData()).isAppear = false;
                    ((SpeedItem)fixture2.getUserData()).onHeadHit();
                    Gdx.app.log("item", "bomber");

                }
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

            //System.out.println(fixtureA.getShape().getRadius() + " r "+fixtureB.getShape().getRadius());
            double DIS = fixtureA.getShape().getRadius() + fixtureB.getShape().getRadius();
            //System.out.println(fixtureA.getShape().getRadius() + " r "+fixtureB.getShape().getRadius()+" DIS"+DIS);

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
            //System.out.println(distance+"dis");
            if (distance == 0.0) {  //the player is below platform
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
                //System.out.println("collide"+distance);

            }
        }
        if(fixtureA.getFilterData().categoryBits ==BOMBER_BIT
                && fixtureB.getFilterData().categoryBits == ITEM_BIT ||
                fixtureA.getFilterData().categoryBits == ITEM_BIT
                        && fixtureB.getFilterData().categoryBits == BOMBER_BIT ) {

            platform_y = fixtureA.getBody().getPosition().y;
            player_y = fixtureB.getBody().getPosition().y;

            float platform_x = fixtureA.getBody().getPosition().x;
            float player_x = fixtureB.getBody().getPosition().x;

            if (fixtureA.getFilterData().categoryBits == ITEM_BIT) {
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
            double DIS = fixtureA.getShape().getRadius() + fixtureB.getShape().getRadius();
            double distance = Math.sqrt((platform_x-player_x)*(platform_x-player_x)
                    + (platform_y-player_y)*(platform_y-player_y)) ;
            //System.out.println(distance+"dis");
            if (distance != DIS) {  //the player is below platform
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
                //System.out.println("collide"+distance);

            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
