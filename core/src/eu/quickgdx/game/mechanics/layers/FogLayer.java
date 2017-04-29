package eu.quickgdx.game.mechanics.layers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by chzellot on 29.04.17.
 */

public class FogLayer extends TiledMapTileLayer {

    /**
     * Creates TiledMap layer
     *
     * @param width      layer width in tiles
     * @param height     layer height in tiles
     * @param tileWidth  tile width in pixels
     * @param tileHeight tile height in pixels
     */
    public FogLayer(int width, int height, int tileWidth, int tileHeight) {
        super(width, height, tileWidth, tileHeight);
    }
}
