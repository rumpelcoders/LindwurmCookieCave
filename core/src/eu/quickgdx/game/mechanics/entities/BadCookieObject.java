package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.states.SlowState;
import eu.quickgdx.game.mechanics.states.State;

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
        this.toRemove = true;
    }
}
