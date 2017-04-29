package eu.quickgdx.game.mechanics.level;

import eu.quickgdx.game.Constants;

/**
 * Created by Veit on 28.04.2017.
 */
public enum Tiletype {
    FLOOR(Constants.ASSET_MAP_FLOOR, false),
    NONWALKABLE(Constants.ASSET_MAP_CEILING_BORDERLESS_EMPTY),
    WALL(Constants.ASSET_MAP_FLOOR_WALL),
    WALL_END_LEFT(Constants.ASSET_MAP_FLOOR_WALL_ENDING_LEFT),
    WALL_END_RIGHT(Constants.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT),
    WALL_END_BOTH(Constants.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT_LEFT),
    CEILING_NSEW(Constants.ASSET_MAP_CEILING_NSWE),
    CEILING_N(Constants.ASSET_MAP_CEILING_N),
    CEILING_E(Constants.ASSET_MAP_CEILING_E),
    CEILING_W(Constants.ASSET_MAP_CEILING_W),
    CEILING_S(Constants.ASSET_MAP_CEILING_S),
    CEILING_NE(Constants.ASSET_MAP_CEILING_NE_EMPTY),
    CEILING_NS(Constants.ASSET_MAP_CEILING_NS_EMPTY),
    CEILING_NSE(Constants.ASSET_MAP_CEILING_NSE_EMPTY),
    CEILING_NSW(Constants.ASSET_MAP_CEILING_NSW_EMPTY),
    CEILING_NW(Constants.ASSET_MAP_CEILING_NW_EMPTY),
    CEILING_NWE(Constants.ASSET_MAP_CEILING_NWE_EMPTY),
    CEILING_SE(Constants.ASSET_MAP_CEILING_SE_EMPTY),
    CEILING_SW(Constants.ASSET_MAP_CEILING_SW_EMPTY),
    CEILING_SWE(Constants.ASSET_MAP_CEILING_SWE_EMPTY),
    CEILING_WE(Constants.ASSET_MAP_CEILING_WE_EMPTY);

    private final boolean collision;
    private final String assetPath;

    Tiletype(String assetPath) {
        this(assetPath, true);
    }

    Tiletype(String assetPath, boolean collision) {
        this.assetPath = assetPath;
        this.collision = collision;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getAssetPath() {
        return assetPath;
    }
}
