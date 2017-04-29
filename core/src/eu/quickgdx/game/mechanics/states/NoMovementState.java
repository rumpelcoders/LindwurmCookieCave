package eu.quickgdx.game.mechanics.states;

import eu.quickgdx.game.mechanics.entities.GameObject;

/**
 * Created by putzchri on 29.04.2017.
 */

public class NoMovementState extends State{
    public NoMovementState(GameObject parentObject, float maxStateTime) {
        super(0, parentObject, maxStateTime, false);
    }
}