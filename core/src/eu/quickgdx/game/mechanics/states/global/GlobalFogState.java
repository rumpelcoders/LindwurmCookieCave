package eu.quickgdx.game.mechanics.states.global;

import eu.quickgdx.game.SoundManager;
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
        System.out.println(getClass().toString() + " remove");
        this.world.removeFogLayer();
        this.world.addGlobalState(new GlobalWaitForFogState(world, 15f)); //TODO make random here
    }

    @Override
    protected void onStateCreated() {
        System.out.println(getClass().toString() + " created");
        this.world.addFogLayer();
        for (ControlledObject controlledObject : this.world.controlledObjects) {
            controlledObject.addState(new NoMovementState(controlledObject, maxStateTime));
        }
        world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange1");
    }

}
