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

public class BadCookieObject extends MovableCollisionObject {

    State state;

    public BadCookieObject(Vector2 position, World world) {
        super(position, world);
        int boundsSize = Constants.TILESIZE - Constants.TILESIZE / 3;
        this.bounds = new Rectangle(position.x + Constants.TILESIZE, position.y, boundsSize, boundsSize);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void hit(ControlledObject player) {
        player.addState(new SlowState(player, 1, 0.5f));
        System.out.println("hit player" + player.getPlaynr());
        this.toRemove = true;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {

    }
}
