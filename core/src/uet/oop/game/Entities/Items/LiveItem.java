package uet.oop.game.Entities.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import uet.oop.game.Entities.AnimateEntities.Bomber;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.PPM;

public class LiveItem extends Item {

    public LiveItem(PlayScreen playScreen, TextureAtlas itemAtlas, float x, float y) {
        super(playScreen);
        image = new TextureRegion(itemAtlas.findRegion("Items"), 2 * 16, 16, 16, 16);

        itemName = Name.LIVE_ITEM;
        defineCharacter(x, y);
        body.setUserData(this);
        setBounds(getX(), getY(), ITEM_WIDTH / PPM, ITEM_HEIGHT / PPM);
        setPosition(getPosAnimationX(), getPosAnimationY());
    }


    @Override
    public void onHeadHit() {

    }


}
