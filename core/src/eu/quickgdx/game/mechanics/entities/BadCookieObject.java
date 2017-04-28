package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.states.State;

/**
 * Created by putzchri on 28.04.2017.
 */

public class BadCookieObject extends MoveableObject {

    State state;

    public BadCookieObject(Vector2 position, World world) {
        super(position, world);
        state = new State(0, this, 1000, true) {
        };
        int boundsSize = Constants.TILESIZE - Constants.TILESIZE / 3;
        this.bounds = new Rectangle(position.x + Constants.TILESIZE, position.y, boundsSize, boundsSize);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public boolean hit() {

        return false;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {

    }
}
