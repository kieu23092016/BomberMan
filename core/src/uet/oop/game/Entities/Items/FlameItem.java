package uet.oop.game.Entities.Items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import uet.oop.game.Screens.PlayScreen;

import static uet.oop.game.Manager.GameManager.PPM;

public class FlameItem extends Item{
    public FlameItem(PlayScreen playScreen, TextureAtlas itemAtlas, float x, float y) {
        super(playScreen);
        image = new TextureRegion(itemAtlas.findRegion("Items"), 16, 0, 16, 16);

        defineCharacter(x, y);
        setBounds(getX(), getY(), ITEM_WIDTH / PPM, ITEM_HEIGHT / PPM);
        setPosition(getPosAnimationX(), getPosAnimationY());
    }



    @Override
    public void onHeadHit() {

    }


}
