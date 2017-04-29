package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 29.04.2017.
 */

public class WallObject extends CollisionObject {
    public WallObject(Vector2 position, World world, float width, float height) {
        super(position, world, width, height);
    }


}
