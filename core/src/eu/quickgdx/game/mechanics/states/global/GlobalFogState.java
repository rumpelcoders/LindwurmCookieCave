package eu.quickgdx.game.mechanics.states.global;

import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.states.NoMovementState;

/**
 * Created by putzchri on 29.04.2017.
 */

public class GlobalFogState extends GlobalState {

    public GlobalFogState(World world, float maxStateTime) {
        super(world, maxStateTime);
    }

    @Override
    protected void onStateRemove() {
        this.world.removeFogLayer();
        this.world.addGlobalState(new GlobalWaitForFogState(world, 15f));
    }

    @Override
    protected void onStateCreated() {
        this.world.addFogLayer();
        for(ControlledObject controlledObject : this.world.controlledObjects) {
            controlledObject.addState(new NoMovementState(controlledObject,5f));
        }
    }

}
