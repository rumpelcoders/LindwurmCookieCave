package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.LastWinner;
import eu.quickgdx.game.ScreenManager;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class GoodCookieObject extends AbstractCookieObject {

    public GoodCookieObject(Vector2 position, World world) {
        super(position, world);
    }

    @Override
    public void hit(PlayerCharacterObject player) {
        world.gameplayScreen.parentGame.setLastWinner(new LastWinner(player.getPlaynr()));
        world.gameplayScreen.parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.ContinueExitGame);

        int rand = 1 + (int)(Math.random() * ((4 - 1) + 1));
        switch (rand){
            case 1: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie1"); break;
            case 2: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie2"); break;
            case 3: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie3"); break;
            case 4: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie4"); break;
        }
    }
}
