package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class PlayerCharacterObject extends ControlledObject {

    protected int playnr;

    public PlayerCharacterObject(Vector2 position, World world, Controls controls, int playerNr) {
        super(position, world, controls);
        this.playnr = playerNr;
        Constanze assetIdle;
        Constanze assetUp;
        Constanze assetDown;
        Constanze assetSide;
        switch (playnr){
            case 1:
                assetIdle = Constanze.ASSET_OWL_FRONT;
                assetUp = Constanze.ASSET_OWL_FRONT;
                assetDown = Constanze.ASSET_OWL_FRONT;
                assetSide = Constanze.ASSET_OWL_FRONT;
                break;
            case 2:
                assetIdle = Constanze.ASSET_WIZARD_FRONT;
                assetUp = Constanze.ASSET_WIZARD_FRONT;
                assetDown = Constanze.ASSET_WIZARD_FRONT;
                assetSide = Constanze.ASSET_WIZARD_FRONT;
                break;
            case 3:
                assetIdle = Constanze.ASSET_SPACEORG_FRONT;
                assetUp = Constanze.ASSET_SPACEORG_FRONT;
                assetDown = Constanze.ASSET_SPACEORG_FRONT;
                assetSide = Constanze.ASSET_SPACEORG_FRONT;
                break;
            case 4:
                assetIdle = Constanze.ASSET_FAIRY_FRONT;
                assetUp = Constanze.ASSET_FAIRY_FRONT;
                assetDown = Constanze.ASSET_FAIRY_FRONT;
                assetSide = Constanze.ASSET_FAIRY_FRONT;
                break;
            default:
                assetIdle = Constanze.ASSET_PLAYER;
                assetUp = Constanze.ASSET_PLAYER;
                assetDown = Constanze.ASSET_PLAYER;
                assetSide = Constanze.ASSET_PLAYER;
        }
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetIdle.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetUp.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetDown.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);
        this.movingSideAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetSide.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);

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
