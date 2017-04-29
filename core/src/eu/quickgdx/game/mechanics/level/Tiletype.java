package eu.quickgdx.game.mechanics.level;

import eu.quickgdx.game.Constanze;

/**
 * Created by Veit on 28.04.2017.
 */
public enum Tiletype {
    FLOOR(Constanze.ASSET_MAP_FLOOR, false),
    NONWALKABLE(Constanze.ASSET_MAP_CEILING_BORDERLESS_EMPTY),
    WALL(Constanze.ASSET_MAP_FLOOR_WALL),
    WALL_END_LEFT(Constanze.ASSET_MAP_FLOOR_WALL_ENDING_LEFT),
    WALL_END_RIGHT(Constanze.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT),
    WALL_END_BOTH(Constanze.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT_LEFT),
    CEILING_NSEW(Constanze.ASSET_MAP_CEILING_NSWE),
    CEILING_N(Constanze.ASSET_MAP_CEILING_N),
    CEILING_E(Constanze.ASSET_MAP_CEILING_E),
    CEILING_W(Constanze.ASSET_MAP_CEILING_W),
    CEILING_S(Constanze.ASSET_MAP_CEILING_S),
    CEILING_NE(Constanze.ASSET_MAP_CEILING_NE_EMPTY),
    CEILING_NS(Constanze.ASSET_MAP_CEILING_NS_EMPTY),
    CEILING_NSE(Constanze.ASSET_MAP_CEILING_NSE_EMPTY),
    CEILING_NSW(Constanze.ASSET_MAP_CEILING_NSW_EMPTY),
    CEILING_NW(Constanze.ASSET_MAP_CEILING_NW_EMPTY),
    CEILING_NWE(Constanze.ASSET_MAP_CEILING_NWE_EMPTY),
    CEILING_SE(Constanze.ASSET_MAP_CEILING_SE_EMPTY),
    CEILING_SW(Constanze.ASSET_MAP_CEILING_SW_EMPTY),
    CEILING_SWE(Constanze.ASSET_MAP_CEILING_SWE_EMPTY),
    CEILING_WE(Constanze.ASSET_MAP_CEILING_WE_EMPTY);

    private final boolean collision;
    private final Constanze constanze;

    Tiletype(Constanze asset) {
        this(asset, true);
    }

    Tiletype(Constanze asset, boolean collision) {
        this.constanze = asset;
        this.collision = collision;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getAssetPath() {
        return constanze.path;
    }
}
