package eu.quickgdx.game.mechanics.layers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by chzellot on 29.04.17.
 */

public class WallLayer extends TiledMapTileLayer {

    /**
     * Creates TiledMap layer
     *
     * @param width      layer width in tiles
     * @param height     layer height in tiles
     * @param tileWidth  tile width in pixels
     * @param tileHeight tile height in pixels
     */
    public WallLayer(int width, int height, int tileWidth, int tileHeight) {
        super(width, height, tileWidth, tileHeight);
    }
}
