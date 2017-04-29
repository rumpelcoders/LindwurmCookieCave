package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.states.SlowState;

/**
 * Created by putzchri on 28.04.2017.
 */

public class BadCookieObject extends AbstractCookieObject {

    public BadCookieObject(Vector2 position, World world) {
        super(position, world);
    }

    public void hit(PlayerCharacterObject player) {
        player.addState(new SlowState(player, 1, 0.5f));
        System.out.println("hit player" + player.getPlaynr());
        int rand = 1 + (int)(Math.random() * ((4 - 1) + 1));
        switch (rand){
            case 1: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie1"); break;
            case 2: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie2"); break;
            case 3: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie3"); break;
            case 4: world.gameplayScreen.parentGame.getSoundManager().playEvent("cookie4"); break;
        }
        world.gameplayScreen.parentGame.getSoundManager().playTimedEvent("vomit",2);
        world.gameplayScreen.parentGame.getSoundManager().increaseIntensity();
        this.toRemove = true;
    }
}
