package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class PlayerCharacterObject extends ControlledObject {

    protected int playnr;

    public PlayerCharacterObject(Vector2 position, World world, Controls controls, int playerNr) {
        super(position, world, controls);
        this.playnr = playerNr;
        String assetIdle;
        String assetUp;
        String assetDown;
        String assetSide;
        switch (playnr){
            case 1:
                assetIdle = Constants.ASSET_OWL_FRONT;
                assetUp = Constants.ASSET_OWL_FRONT;
                assetDown = Constants.ASSET_OWL_FRONT;
                assetSide = Constants.ASSET_OWL_FRONT;
                break;
            case 2:
                assetIdle = Constants.ASSET_WIZARD_FRONT;
                assetUp = Constants.ASSET_WIZARD_FRONT;
                assetDown = Constants.ASSET_WIZARD_FRONT;
                assetSide = Constants.ASSET_WIZARD_FRONT;
                break;
            default:
                assetIdle = Constants.ASSET_PLAYER;
                assetUp = Constants.ASSET_PLAYER;
                assetDown = Constants.ASSET_PLAYER;
                assetSide = Constants.ASSET_PLAYER;
        }
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetIdle, 0.3f, Constants.TILESIZE, Constants.TILESIZE);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetUp, 0.3f, Constants.TILESIZE, Constants.TILESIZE);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetDown, 0.3f, Constants.TILESIZE, Constants.TILESIZE);
        this.movingSideAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetSide, 0.3f, Constants.TILESIZE, Constants.TILESIZE);

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


    public int getPlaynr() {
        return playnr;
    }
}
