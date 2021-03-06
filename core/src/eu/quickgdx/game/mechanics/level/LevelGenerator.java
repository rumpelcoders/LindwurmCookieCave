package eu.quickgdx.game.mechanics.level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.mechanics.entities.AbstractCookieObject;
import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.entities.GameObject;
import eu.quickgdx.game.mechanics.entities.MoveableObject;

/**
 * Created by Veit on 28.04.2017.
 */
public class LevelGenerator {
    static Level lvl;

    public static Level generateLevel(int levelsize, Array<ControlledObject> playerlist, Array<AbstractCookieObject> cookielist) {
        lvl = new Level(levelsize);
        for (ControlledObject player : playerlist) {
            for (MoveableObject cookie : cookielist) {
                generatePath(lvl, player, cookie);
            }
        }
        obscureMap(lvl);
        finishMap(lvl);
        makePretty(lvl);
        return lvl;
    }

    private static void makePretty(Level lvl) {
        boolean n, s, w, e;
        boolean wallse, wallsw;
        for (int i = 0; i < lvl.levelsize; i++) {
            for (int j = 0; j < lvl.levelsize; j++) {
                wallse = false;
                wallsw = false;
                if (lvl.typemap[i][j] == Tiletype.NONWALKABLE) {
                    if (j - 1 >= 0 && lvl.typemap[i][j - 1] == Tiletype.FLOOR) {
                        if (i - 1 >= 0 && lvl.typemap[i - 1][j] == Tiletype.FLOOR) {
                            wallsw = true;
                        }
                        if (i + 1 < lvl.levelsize && lvl.typemap[i + 1][j] == Tiletype.FLOOR) {
                            wallse = true;
                        }
                        if (wallse) {
                            if (wallsw) {
                                lvl.typemap[i][j] = Tiletype.WALL_END_BOTH;
                            } else {
                                lvl.typemap[i][j] = Tiletype.WALL_END_RIGHT;
                            }

                        } else if (wallsw) {
                            lvl.typemap[i][j] = Tiletype.WALL_END_LEFT;
                        } else {
                            lvl.typemap[i][j] = Tiletype.WALL;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < lvl.levelsize; i++) {
            for (int j = 0; j < lvl.levelsize; j++) {
                if (lvl.typemap[i][j] == Tiletype.NONWALKABLE) {
                    n = false;
                    e = false;
                    s = false;
                    w = false;

                    if (j - 1 >= 0) {
                        if (lvl.typemap[i][j-1] == Tiletype.WALL || lvl.typemap[i][j-1] == Tiletype.WALL_END_LEFT || lvl.typemap[i][j-1] == Tiletype.WALL_END_RIGHT|| lvl.typemap[i][j-1] == Tiletype.WALL_END_BOTH)
                            s = true;
                    }
                    if (i - 1 >= 0) {
                        if (lvl.typemap[i-1][j] == Tiletype.FLOOR)
                            w = true;
                    }
                    if (i + 1 < lvl.levelsize) {
                        if (lvl.typemap[i+1][j] == Tiletype.FLOOR)
                            e = true;
                    }
                    if (j + 1 < lvl.levelsize) {
                        if (lvl.typemap[i][j+1] == Tiletype.FLOOR)
                            n = true;
                    }

                    if (n) {
                        if (s) {
                            if (e) {
                                if (w) {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NSEW;
                                } else {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NSE;
                                }
                            } else {
                                if (w) {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NSW;
                                } else {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NS;
                                }
                            }
                        } else {
                            if (e) {
                                if (w) {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NWE;
                                } else {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NE;
                                }
                            } else {
                                if (w) {
                                    lvl.typemap[i][j] = Tiletype.CEILING_NW;
                                } else {
                                    lvl.typemap[i][j] = Tiletype.CEILING_N;
                                }
                            }
                        }
                    } else if (s) {
                        if (e) {
                            if (w) {
                                lvl.typemap[i][j] = Tiletype.CEILING_SWE;
                            } else {
                                lvl.typemap[i][j] = Tiletype.CEILING_SE;
                            }

                        } else {
                            if (w) {
                                lvl.typemap[i][j] = Tiletype.CEILING_SW;
                            } else {
                                lvl.typemap[i][j] = Tiletype.CEILING_S;
                            }

                        }
                    } else if (w) {
                        if (e) {
                            lvl.typemap[i][j] = Tiletype.CEILING_WE;
                        } else {
                            lvl.typemap[i][j] = Tiletype.CEILING_W;
                        }
                    } else if (e) {
                        lvl.typemap[i][j] = Tiletype.CEILING_E;
                    }


                }
            }
        }
    }

    private static void finishMap(Level lvl) {

    }

    private static void obscureMap(Level lvl) {

    }

    private static void generatePath(Level lvl, GameObject start, GameObject end) {
        GridPoint startPoint = new GridPoint(start.getTileX(), start.getTileY());
        lvl.typemap[startPoint.x][startPoint.y] = Tiletype.FLOOR;
        GridPoint currentPoint = new GridPoint(startPoint.x, startPoint.y);
        GridPoint goalPoint;
        GridPoint endPoint = new GridPoint(end.getTileX(), end.getTileY());


        int distance = 0;
        if (start.getTileX() > end.getTileX())
            distance += start.getTileX() - end.getTileX();
        else
            distance += end.getTileX() - start.getTileX();
        if (start.getTileY() > end.getTileY())
            distance += start.getTileY() - end.getTileY();
        else
            distance += end.getTileY() - start.getTileY();


        for (int i = 0; i < (1 + (int) (Math.random() * distance / 15)); i++) {
            goalPoint = getNextPoint(currentPoint, endPoint);
            new GridPoint((0 + (int) (Math.random() * lvl.levelsize - 1)), (0 + (int) (Math.random() * lvl.levelsize - 1)));
            moveToPoint(currentPoint, goalPoint);
            currentPoint = new GridPoint(goalPoint.x, goalPoint.y);
        }
        moveToPoint(currentPoint, endPoint);

    }

    private static GridPoint getNextPoint(GridPoint currentPoint, GridPoint goalPoint) {
        int x = 0, y = 0;

        if (Math.random() > 0.5) {
            if (goalPoint.x > currentPoint.x) {
                x = currentPoint.x + (int) (Math.random() * ((goalPoint.x - currentPoint.x) + 1));
            } else {
                x = goalPoint.x + (int) (Math.random() * ((currentPoint.x - goalPoint.x) + 1));
            }
        } else {
            x = (0 + (int) (Math.random() * lvl.levelsize - 1));
        }
        if (Math.random() > 0.5) {
            if (goalPoint.y > currentPoint.y) {
                y = currentPoint.y + (int) (Math.random() * ((goalPoint.y - currentPoint.y) + 1));
            } else {
                y = goalPoint.y + (int) (Math.random() * ((currentPoint.y - goalPoint.y) + 1));
            }
        } else {
            y = (0 + (int) (Math.random() * lvl.levelsize - 1));
        }

        return new GridPoint(x, y);
    }

    private static void moveToPoint(GridPoint currentPoint, GridPoint goalPoint) {
        while (goalPoint.x != currentPoint.x) {
            if (goalPoint.x > currentPoint.x) {
                currentPoint.x++;
            } else {
                currentPoint.x--;
            }
            lvl.typemap[currentPoint.x][currentPoint.y] = Tiletype.FLOOR;
        }
        while (goalPoint.y != currentPoint.y) {
            if (goalPoint.y > currentPoint.y) {
                currentPoint.y++;
            } else {
                currentPoint.y--;
            }
            lvl.typemap[currentPoint.x][currentPoint.y] = Tiletype.FLOOR;
        }
    }

    public static class GridPoint {
        public int x, y;

        public GridPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
