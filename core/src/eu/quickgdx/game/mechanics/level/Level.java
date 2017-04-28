package eu.quickgdx.game.mechanics.level;

import java.util.Arrays;

/**
 * Created by Veit on 28.04.2017.
 */
public class Level {
    public int levelsize;
    public Tiletype[][] typemap;

    public Level(int levelsize) {
        this.levelsize = levelsize;
        typemap = new Tiletype[levelsize][levelsize];
        Arrays.fill(typemap, Tiletype.WALL);
    }
}
