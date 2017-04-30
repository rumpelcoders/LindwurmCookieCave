package eu.quickgdx.game.mechanics.states.global;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 29.04.2017.
 */

public class GlobalWaitForFogState extends GlobalState {
    boolean playsound = false;

    public GlobalWaitForFogState(World world, float maxStateTime) {
        super(world, maxStateTime);
    }

    @Override
    protected void onStateRemove() {
        System.out.println(getClass().toString() + " remove");
        this.world.addGlobalState(new GlobalFogState(world, Constanze.FOG_TIME));

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(!playsound && stateTime>=maxStateTime-2){
            System.out.println("Test");
            world.gameplayScreen.parentGame.getSoundManager().playEvent("lw_maze");
            playsound = true;
        }
    }
    @Override
    protected void onStateCreated() {
        System.out.println(getClass().toString() + " created");
    }

}
