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

    private final int boundsSize;

    public BadCookieObject(Vector2 position, World world) {
        super(position, world);
        boundsSize = Constants.TILESIZE - Constants.TILESIZE / 3;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Vector2 newPosition = new Vector2(Math.round(position.x), Math.round(position.y));
        Rectangle newBounds = new Rectangle(newPosition.x, newPosition.y, boundsSize, boundsSize);
        this.bounds = newBounds;
    }

    public void hit(PlayerCharacterObject player) {
        player.addState(new SlowState(player, 1, 0.5f));
        System.out.println("hit player" + player.getPlaynr());
        this.toRemove = true;
    }
}
