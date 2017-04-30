package eu.quickgdx.game;


import com.badlogic.gdx.graphics.Texture;

/**
 * Created by chzellot on 28.04.17.
 */

public enum Constanze {

    ASSET_PLAYER("gameplay/test_char_64.png"),

    ASSET_OWL_FRONT("gameplay/owl_front.png"),
    ASSET_FAIRY_FRONT("gameplay/fairy_front.png"),
    ASSET_WIZARD_FRONT("gameplay/wizard_front.png"),
    ASSET_SPACEORG_FRONT("gameplay/spaceorc_front.png"),
    ASSET_COOKIE_BAG("gameplay/cookie_bag.png"),
    ASSET_FOG("level/fog_1.png"),

    ASSET_MAP_CEILING_BORDERLESS_EMPTY("level/ceiling_borderless_empty.png"),
    ASSET_MAP_CEILING_E("level/ceiling_e.png"),
    ASSET_MAP_CEILING_E_EMPTY("level/ceiling_e_empty.png"),
    ASSET_MAP_CEILING_N("level/ceiling_n.png"),
    ASSET_MAP_CEILING_NE_EMPTY("level/ceiling_ne_empty.png"),
    ASSET_MAP_CEILING_NSE_EMPTY("level/ceiling_nse_empty.png"),
    ASSET_MAP_CEILING_NSWE("level/ceiling_nswe.png"),
    ASSET_MAP_CEILING_NSW_EMPTY("level/ceiling_nsw_empty.png"),
    ASSET_MAP_CEILING_NS_EMPTY("level/ceiling_ns_empty.png"),
    ASSET_MAP_CEILING_NWE_EMPTY("level/ceiling_nwe_empty.png"),
    ASSET_MAP_CEILING_NW_EMPTY("level/ceiling_nw_empty.png"),
    ASSET_MAP_CEILING_N_EMPTY("level/ceiling_n_empty.png"),
    ASSET_MAP_CEILING_S("level/ceiling_s.png"),
    ASSET_MAP_CEILING_SE_EMPTY("level/ceiling_se_empty.png"),
    ASSET_MAP_CEILING_SWE_EMPTY("level/ceiling_swe_empty.png"),
    ASSET_MAP_CEILING_SW_EMPTY("level/ceiling_sw_empty.png"),
    ASSET_MAP_CEILING_S_EMPTY("level/ceiling_s_empty.png"),
    ASSET_MAP_CEILING_W("level/ceiling_w.png"),
    ASSET_MAP_CEILING_WE_EMPTY("level/ceiling_we_empty.png"),
    ASSET_MAP_CEILING_W_EMPTY("level/ceiling_w_empty.png"),
    ASSET_MAP_FLOOR("level/floor.png"),
    ASSET_MAP_FLOOR_WALL("level/floor_wall.png"),
    ASSET_MAP_FLOOR_WALL_ENDING_LEFT("level/floor_wall_ending_left.png"),
    ASSET_MAP_FLOOR_WALL_ENDING_RIGHT("level/floor_wall_ending_right.png"),
    ASSET_MAP_FLOOR_WALL_ENDING_RIGHT_LEFT("level/floor_wall_ending_right_left.png"),

    ASSET_CLOUD_CENTER("level/cloud_center.png"),
    ASSET_CLOUD_S("level/cloud_s.png"),
    ASSET_CLOUD_E("level/cloud_e.png"),
    ASSET_CLOUD_N("level/cloud_n.png"),
    ASSET_CLOUD_W("level/cloud_w.png"),
    ASSET_CLOUD_NE("level/cloud_ne.png"),
    ASSET_CLOUD_NW("level/cloud_nw.png"),
    ASSET_CLOUD_SE("level/cloud_se.png"),
    ASSET_CLOUD_SW("level/cloud_sw.png"),

    ASSET_TEST("level/black.png"),

    ASSET_ARENA_S("level/arenaborder_s.png"),
    ASSET_ARENA_E("level/arenaborder_e.png"),
    ASSET_ARENA_N("level/arenaborder_n.png"),
    ASSET_ARENA_W("level/arenaborder_w.png"),
    ASSET_ARENA_NE("level/arenaborder_ne.png"),
    ASSET_ARENA_NW("level/arenaborder_nw.png"),
    ASSET_ARENA_SE("level/arenaborder_se.png"),
    ASSET_ARENA_SW("level/arenaborder_sw.png"),

    ASSET_LINDWURM_STANDARD("gameplay/lindwurm_blow.png"),
    ASSET_LINDWURM_LOOK_LEFT("gameplay/lindwurm_look_left.png"),
    ASSET_LINDWURM_LOOK_RIGHT("gameplay/lindwurm_look_right.png"),
    ASSET_LINDWURM_BLOW("gameplay/lindwurm_blow.png"),
    ;



    public static final int FOG_TIME = 4;

    public final static int TILESIZE = 64;
    public final static float SCALE = 1.0f;
    public final static float SCALED_TILE = TILESIZE * SCALE;

    public static int GAME_WIDTH = 1920;
    public static int GAME_HEIGHT = 1080;


    public final String path;
    public final Class toLOad;

    Constanze(String path) {
        this(path, Texture.class);
    }

    Constanze(String path, Class toLOad) {
        this.path = path;
        this.toLOad = toLOad;
    }


}
