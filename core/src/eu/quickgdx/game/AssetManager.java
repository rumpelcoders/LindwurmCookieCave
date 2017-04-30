package eu.quickgdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by chzellot on 30.04.17.
 */

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {

    public Texture getTexture(Constanze constanze) {
        return this.get(constanze.path, Texture.class);
    }
}
