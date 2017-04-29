package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.states.NoMovementState;
import eu.quickgdx.game.mechanics.states.SlowState;
import eu.quickgdx.game.mechanics.states.State;

/**
 * Gives you an simple object controlled by the user
 * Created by Veit on 06.02.2016.
 */
public class ControlledObject extends MoveableObject {
    private final int boundsSize;
    //    private TextureRegion[] regions = new TextureRegion[12];
    private Vector3 touchCoordinates = new Vector3(0, 0, 0);

    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int heading; // 1 - UP, 2 - Right, 3 - Down, 4 - Left
    protected Animation idleAnimation;
    protected Animation movingUpAnimation;
    protected Animation movingDownAnimation;
    protected Animation movingSideAnimation;
    protected TextureRegion frame;
    protected Controls controls;


    private boolean cameraFollow = false; // If this flag is true the camera will follow this Object (Not tested with multiple conrolledObjects)

    public ControlledObject(Vector2 position, World world, Controls controls) {
        super(position, world);
//        world.gameplayScreen.gameCam.position.x = position.x;
//        world.gameplayScreen.gameCam.position.y = position.y;
        boundsSize = Constanze.TILESIZE - Constanze.TILESIZE / 3;
        this.bounds = new Rectangle(position.x, position.y, boundsSize, boundsSize);
        this.controls = controls;
        this.speed = 12f;
        this.hitpoints = 5;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput();
        handleMovement(delta);
    }

    @Override
    void handleMovement(Float delta) {
        calcDirection();
        Vector2 newPosition = new Vector2(Math.round(position.x), Math.round(position.y));
        float updateSpeed = this.speed;
        for (State state : states) {
            if (state instanceof SlowState) {
                updateSpeed = ((SlowState) state).getSlowPercentage() * updateSpeed;
            }if (state instanceof NoMovementState) {
                return;
            }
        }
        newPosition.add(direction.nor().scl(updateSpeed));
        Rectangle newBounds = new Rectangle(newPosition.x, newPosition.y, boundsSize, boundsSize);

        for (int j = 0; j < world.gameObjects.size; j++) {
            GameObject gameObject = world.gameObjects.get(j);
            if (gameObject.bounds != null) {
                if (gameObject.bounds.overlaps(newBounds)) {
                    if (gameObject instanceof CollisionObject)
                        return;
                }
            }

        }

        this.bounds = newBounds;

        this.position.add(direction.nor().scl(updateSpeed));
        if (cameraFollow) {
            cameraFollow(direction.nor().scl(updateSpeed));
        }
        if (!direction.nor().isZero()) {
            if (direction.x > 0) {
                heading = 2;
            } else if (direction.x < 0) {
                heading = 4;
            }
            if (direction.y > 0) {
                heading = 1;
            } else if (direction.y < 0) {
                heading = 3;
            }
            movement = Movement.MOVING;
        } else {
            movement = Movement.IDLE;
        }
    }


    /**
     * Calculates the direction Vector
     */
    private void calcDirection() {
        direction = new Vector2(0, 0);
        if (moveUp && !moveDown) {
            direction.y = 1;
        } else if (!moveUp && moveDown) {
            direction.y = -1;
        }

        if (moveLeft && !moveRight) {
            direction.x = -1;
        } else if (!moveLeft && moveRight) {
            direction.x = 1;
        }
        moveDown = moveUp = moveRight = moveLeft = false;
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(controls.DOWN)) {
            moveDown = true;
        }
        if (Gdx.input.isKeyPressed(controls.UP)) {
            moveUp = true;
        }
        if (Gdx.input.isKeyPressed(controls.LEFT)) {
            moveLeft = true;
        }
        if (Gdx.input.isKeyPressed(controls.RIGHT)) {
            moveRight = true;
        }
//      ignore touch atm
//        if (Gdx.input.justTouched()) {
//            touch(world.gameplayScreen.gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1)));
//        }
    }

    /**
     * Your typical render function
     *
     * @param delta
     * @param spriteBatch heading must be set accordingly: 1 - UP, 2 - Right, 3 - Down, 4 - Left
     */
    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        switch (heading) {
            case 1:
                frame = movingUpAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 2:
                frame = movingSideAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 3:
                frame = movingDownAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 4:
                frame = movingSideAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            default:
                frame = idleAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
        }
    }

    public void touch(Vector3 touchCoordinates) {
        this.touchCoordinates = touchCoordinates;
    }

    public void cameraFollow(Vector2 vector) {
        world.gameplayScreen.gameCam.translate(vector);
    }
    @Override
    public void addState(State state) {
        super.addState(state);
    }

}
