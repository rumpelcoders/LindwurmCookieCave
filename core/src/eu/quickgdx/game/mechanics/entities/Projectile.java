package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.states.SlowState;

/**
 * Created by Veit on 29.04.2017.
 */
public class Projectile extends MoveableObject {

    public ControlledObject originObject;
    Constanze assetEverything;
    Animation everyAnimation;
    int heading;
    protected TextureRegion frame;

    public Projectile(Vector2 position, World world, float speed, Vector2 direction, ControlledObject originObject,int heading) {
        super(position, world);
        this.speed = speed;
        this.direction = direction;
        this.originObject = originObject;
        this.heading = heading;
        switch (((PlayerCharacterObject)originObject).getPlaynr()){
            case 1: assetEverything = Constanze.ASSET_TEST; break;
            case 2: assetEverything = Constanze.ASSET_TEST; break;
            case 3: assetEverything = Constanze.ASSET_TEST; break;
            case 4: assetEverything = Constanze.ASSET_TEST; break;
        }
        this.setBounds(new Rectangle(position.x,position.y,10,10));

        this.everyAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(assetEverything.path, 0.3f, Constanze.TILESIZE, Constanze.TILESIZE);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        super.handleMovement(delta, false);
        this.setBounds(new Rectangle(position.x, position.y, 10, 10));
        for (int i = 0; i < world.gameObjects.size; i++) {
            GameObject gameObject = world.gameObjects.get(i);
            if (gameObject instanceof CollisionObject) {
                if (gameObject.getBounds()!=null && gameObject.getBounds().overlaps(this.bounds)) {
                    world.gameObjects.removeValue(this, false);
                }
            }
        }
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        switch (heading) {
            case 1:
                frame = everyAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 2:
                frame = everyAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 3:
                frame = everyAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 4:
                frame = everyAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            default:
                frame = everyAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
        }
    }

    public void hit(GameObject object){
        if(object instanceof PlayerCharacterObject)
            object.addState(new SlowState(object,2,50));
        world.gameObjects.removeValue(this,false);
    }
}
