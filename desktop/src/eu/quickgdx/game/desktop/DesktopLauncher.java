package eu.quickgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.QuickGdx;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constanze.GAME_WIDTH;
        config.height = Constanze.GAME_HEIGHT;
		config.fullscreen = false;
//		config.resizable = true;
		new LwjglApplication(new QuickGdx(), config);

	}
}
