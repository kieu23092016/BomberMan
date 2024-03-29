package uet.oop.game.Entities.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import uet.oop.game.Entities.TileEntities.Brick;
import uet.oop.game.Entities.Entity;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.*;

public abstract class Item extends Entity {

    public PlayScreen playScreen;
    FixtureDef fixtureDef;
    public enum Name {LIVE_ITEM, SPEED_ITEM, FLAME_ITEM, TIME_ITEM}

    public Name itemName;

    public float ITEM_WIDTH = 40;
    public float ITEM_HEIGHT = 40;

    public float timerAppear = 200;
    public boolean isAppear = true;
    protected TextureRegion image;


    public Item() {
    }

    public Item(PlayScreen playScreen) {
        this.playScreen = playScreen;
        gameWorld = playScreen.getGameWorld();
    }

    public void defineCharacter(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        body = gameWorld.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(ITEM_WIDTH / 2 / PPM, ITEM_HEIGHT / 2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.filter.categoryBits = ITEM_BIT;
//        fixtureDef.filter.maskBits = BRICK_BIT | BOMB_BIT | STONE_BIT | BOMBER_BIT | FLAME_BIT;
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef).setUserData(this);

        //fixtureDef.filter.categoryBits = ;
        //fixtureDef.filter.maskBits = ;
        //fixtureDef.isSensor = true;
    }

    public void setTimerAppear(float timerAppear) {
        this.timerAppear = timerAppear;
    }

    public void update(float dt) {
        if (timerAppear > 0) timerAppear--;
        else isAppear = false;
    }

    public void draw(Batch batch) {
        if (isAppear)
            batch.draw(image, getPosAnimationX(), getPosAnimationY(), ITEM_WIDTH / PPM, ITEM_HEIGHT / PPM);
    }

    public void onHeadHit() {
        System.out.println("onheadhit");
        isAppear = false;
        //fixtureDef.filter.maskBits = DESTROY_BIT;
    }

    @Override
    public void dispose() {
        gameWorld.destroyBody(body);
    }
}
