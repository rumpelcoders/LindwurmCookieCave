package eu.quickgdx.game.mechanics.states.global;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 29.04.2017.
 */

public class GlobalWaitForFogState extends GlobalState {

    public GlobalWaitForFogState(World world, float maxStateTime) {
        super(world, maxStateTime);
    }

    @Override
    protected void onStateRemove() {
        this.world.addGlobalState(new GlobalFogState(world, Constants.FOG_TIME));
    }

    @Override
    protected void onStateCreated() {

    }

}
