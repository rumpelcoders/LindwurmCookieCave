package eu.quickgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.QuickGdx;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.GAME_WIDTH;
        config.height = Constants.GAME_HEIGHT;
		config.fullscreen = false;
		new LwjglApplication(new QuickGdx(), config);

	}
}
