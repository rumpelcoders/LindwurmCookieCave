package eu.quickgdx.game.mechanics.states.global;

import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.SoundManager;
import eu.quickgdx.game.Utils;
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
        this.world.addGlobalState(new GlobalWaitForFogState(world, Utils.randRange(4, 20)));
        world.gameplayScreen.parentGame.getSoundManager().startBgMusic();
    }

    @Override
    protected void onStateCreated() {
        world.gameplayScreen.parentGame.getSoundManager().stopBgMusic();
        System.out.println(getClass().toString() + " created");
        this.world.addFogLayer();
        for (ControlledObject controlledObject : this.world.controlledObjects) {
            controlledObject.addState(new NoMovementState(controlledObject, maxStateTime));
        }

        //world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange3");

        int rand = 1 + (int)(Math.random() * ((5 - 1) + 1));
        switch (rand){
            case 1: world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange1"); break;
            case 2: world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange2"); break;
            case 3: world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange3"); break;
            case 4: world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange4"); break;
            case 5: world.gameplayScreen.parentGame.getSoundManager().playEvent("mapchange5"); break;

        }
    }

}
