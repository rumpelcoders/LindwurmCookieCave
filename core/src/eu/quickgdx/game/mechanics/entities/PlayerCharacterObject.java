package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class PlayerCharacterObject extends ControlledObject {

    public PlayerCharacterObject(Vector2 position, World world, Controls controls, int playerNr) {
        super(position, world, controls, playerNr);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleHit(delta);
    }

    public void handleHit(float delta) {
        handleMovement(delta);

        for (int i = 0; i < world.gameObjects.size; i++) {
            GameObject gameObject = world.gameObjects.get(i);
            if (gameObject instanceof MovableCollisionObject) {
                MovableCollisionObject movableCollisionObject = (MovableCollisionObject) gameObject;
                if (movableCollisionObject.getBounds().overlaps(this.bounds)) {
                    movableCollisionObject.hit(this);
                }
            }
        }
    }
}
