package eu.quickgdx.game.mechanics.entities;

/**
 * Created by chzellot on 28.04.17.
 */

public class Controls {
    public final int UP;
    public final int DOWN;
    public final int LEFT;
    public final int RIGHT;

    /**
     * should be used with Input.Keys from gdx
     *
     * @param UP
     * @param DOWN
     * @param LEFT
     * @param RIGHT
     */
    public Controls(int UP, int DOWN, int LEFT, int RIGHT) {
        this.UP = UP;
        this.DOWN = DOWN;
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
    }
}
