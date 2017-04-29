package eu.quickgdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by chzellot on 29.04.17.
 */

public class CamObject extends OrthographicCamera {

    private int nr;

    public CamObject(float viewportWidth, float viewportHeight, int nr) {
        super(viewportWidth, viewportHeight);
        this.nr = nr;
    }

    public CamObject(int nr) {
        super();
        this.nr = nr;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }
}
