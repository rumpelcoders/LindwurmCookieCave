package eu.quickgdx.game.mechanics.states.global;

import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 29.04.2017.
 */

public abstract class GlobalState {

    protected float stateTime;
    protected float maxStateTime;
    protected boolean hasMaxStateTime;
    protected World world;

    public GlobalState(World world, float maxStateTime) {
        this.world = world;
        this.stateTime = 0;
        this.maxStateTime = maxStateTime;
        this.hasMaxStateTime = maxStateTime != 0;
        this.onStateCreated();
    }

    public void update(float delta) {
        this.stateTime += delta;
        if (this.hasMaxStateTime) {
            if (this.maxStateTime < this.stateTime) {
                this.world.removeGlobalState(this);
                this.onStateRemove();
            }
        }
    }

    protected abstract void onStateRemove();

    protected abstract void onStateCreated();

}
