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
        world.gameplayScreen.parentGame.getSoundManager().playEvent("vomit");
        this.toRemove = true;
    }
}
