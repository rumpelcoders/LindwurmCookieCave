package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.CamObject;
import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class PlayerCharacterObject extends ControlledObject {

    protected int playnr;

    public PlayerCharacterObject(Vector2 position, World world, Controls controls, int playerNr, CamObject camera) {
        super(position, world, controls, camera);
        this.playnr = playerNr;
        Constanze assetIdle;
        String assetUp;
        String assetDown;
        Constanze assetSide;
        switch (playnr) {
            case 1:
                assetIdle = Constanze.ASSET_OWL_FRONT;
                assetUp = "gameplay/animation/owl_move_up_";
                assetDown = "gameplay/animation/owl_move_down_";
                assetSide = Constanze.ASSET_OWL_FRONT;
                break;
            case 2:
                assetIdle = Constanze.ASSET_WIZARD_FRONT;
                assetUp = "gameplay/animation/wizard_move_up_";
                assetDown = "gameplay/animation/wizard_move_down_";
                assetSide = Constanze.ASSET_WIZARD_FRONT;
                break;
            case 3:
                assetIdle = Constanze.ASSET_SPACEORG_FRONT;
                assetUp = "gameplay/animation/spaceorc_move_up_";
                assetDown = "gameplay/animation/spaceorc_move_down_";
                assetSide = Constanze.ASSET_SPACEORG_FRONT;
                break;
            case 4:
                assetIdle = Constanze.ASSET_FAIRY_FRONT;
                assetUp = "gameplay/animation/fairy_move_up_";
                assetDown = "gameplay/animation/fairy_move_down_";
                assetSide = Constanze.ASSET_FAIRY_FRONT;
                break;
            default:
                assetIdle = Constanze.ASSET_PLAYER;
                assetUp = Constanze.ASSET_PLAYER.path;
                assetDown = Constanze.ASSET_PLAYER.path;
                assetSide = Constanze.ASSET_PLAYER;
        }
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetIdle.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetUp, 2, 0.1f);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetDown, 2, 0.1f);
        this.movingSideAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetSide.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleMovement(delta, false);
        handleHit(delta);
    }

    public void handleHit(float delta) {
        for (int i = 0; i < world.gameObjects.size; i++) {
            GameObject gameObject = world.gameObjects.get(i);
            if (gameObject instanceof MovableCollisionObject) {
                MovableCollisionObject movableCollisionObject = (MovableCollisionObject) gameObject;
                if (movableCollisionObject.getBounds().overlaps(this.bounds)) {
                   movableCollisionObject.hit(this);
                }
            } else if (gameObject instanceof Projectile) {
                if (gameObject.getBounds()!=null && gameObject.getBounds().overlaps(this.bounds)) {
                    if (((Projectile) gameObject).originObject != this) {
                        ((Projectile) gameObject).hit(this);
                    }
                }
            }
        }
    }


    public int getPlaynr() {
        return playnr;
    }
}
