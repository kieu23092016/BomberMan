package uet.oop.game.Scence;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uet.oop.game.BombermanGame;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel ;
    Label scoreLabel ;
    Label timeLabel;
    Label levelLabel ;
    Label worldLabel;
    Label gameLabel;
    public Hud(SpriteBatch spriteBatch){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(BombermanGame.V_WIDTH, BombermanGame.V_HEIGHT);
        stage = new Stage(viewport, spriteBatch);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.BLUE ));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.BLACK ));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.BLACK));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.BLACK));
        worldLabel = new Label("World", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.BLACK));
        gameLabel = new Label("Bomberman", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.BLACK));

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(gameLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
}
