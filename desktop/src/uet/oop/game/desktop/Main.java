package uet.oop.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uet.oop.game.BombermanGame;

public class Main {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 765;
		config.height = 675;
		new LwjglApplication(new BombermanGame(), config);
	}
}
