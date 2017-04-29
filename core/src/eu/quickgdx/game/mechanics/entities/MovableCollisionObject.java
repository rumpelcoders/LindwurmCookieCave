package eu.quickgdx.game.mechanics.entities;

/**
 * Created by chzellot on 29.04.17.
 */

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;

/**
 * Simple class to give you the ability to add collision objects (via bounds)
 * <p>
 * Created by Veit on 25.08.2016.
 */
public abstract class MovableCollisionObject extends MoveableObject {

    public MovableCollisionObject(Vector2 position, World world) {
        super(position, world);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        return;
    }

    public abstract void hit(ControlledObject player);
}
