package eu.quickgdx.game.mechanics.states;

import eu.quickgdx.game.mechanics.entities.GameObject;

/**
 * Created by putzchri on 29.04.2017.
 */

public class SlowState extends State {

    private float slowPercentage;

    public SlowState(GameObject parentObject, float maxStateTime,float slowPercentage) {
        super(0, parentObject, maxStateTime, false);
        this.slowPercentage = slowPercentage;
    }

    public float getSlowPercentage() {
        return slowPercentage;
    }

}
