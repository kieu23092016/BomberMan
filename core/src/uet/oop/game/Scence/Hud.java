package uet.oop.game.Scence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uet.oop.game.BombermanGame;

import static uet.oop.game.Manager.GameManager.V_HEIGHT;
import static uet.oop.game.Manager.GameManager.V_WIDTH;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private boolean timeUp;
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private static Label scoreLabel;
    Label countdownLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label gameLabel;

    public Hud(SpriteBatch spriteBatch) {
        worldTimer = 1000;
        timeCount = 0;
        score = 3;
        viewport = new FitViewport(V_WIDTH, V_HEIGHT);
        stage = new Stage(viewport, spriteBatch);

        countdownLabel = new Label(String.format("%04d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        scoreLabel = new Label(String.format("%01d", score), new Label.LabelStyle(new BitmapFont(),Color.YELLOW));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        worldLabel = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        gameLabel = new Label("Heart", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(gameLabel).expandX().padTop(0);
        table.add(timeLabel).expandX().padTop(0);
        table.add(worldLabel).expandX().padTop(0);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(levelLabel).expandX();


        stage.addActor(table);
    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }
    public static void addScore(int value){
        score+= value;
        scoreLabel.setText(String.format("%01d", score));
    }


    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() {
        return timeUp;
    }
}