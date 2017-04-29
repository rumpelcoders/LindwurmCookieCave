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

    public static Level generateLevel(int levelsize, Array<ControlledObject> playerlist, Array<AbstractCookieObject> cookielist){
       lvl = new Level(levelsize);
        for (ControlledObject player: playerlist) {
            for (MoveableObject cookie: cookielist) {
                generatePath(lvl, player, cookie);
            }
        }
        obscureMap(lvl);
        finishMap(lvl);
        return lvl;
    }

    private static void finishMap(Level lvl) {

    }

    private static void obscureMap(Level lvl) {

    }

    private static void generatePath(Level lvl, GameObject start, GameObject end){
        GridPoint startPoint = new GridPoint(start.getTileX(),start.getTileY());
        lvl.typemap[startPoint.x][startPoint.y] = Tiletype.FREE;
        GridPoint currentPoint = new GridPoint(startPoint.x, startPoint.y);
        GridPoint goalPoint;
        GridPoint endPoint = new GridPoint(end.getTileX(),end.getTileY());


        int distance = 0;
        if(start.getTileX()>end.getTileX())
            distance+=start.getTileX()-end.getTileX();
        else
            distance+=end.getTileX()-start.getTileX();
        if(start.getTileY()>end.getTileY())
            distance+=start.getTileY()-end.getTileY();
        else
            distance+=end.getTileY()-start.getTileY();


        for (int i = 0; i< (1 + (int)(Math.random() * distance/15));i++){
            goalPoint = getNextPoint(currentPoint,endPoint);
            new GridPoint((0 + (int)(Math.random() * lvl.levelsize-1)),(0 + (int)(Math.random() * lvl.levelsize-1)));
            moveToPoint(currentPoint,goalPoint);
            currentPoint = new GridPoint(goalPoint.x, goalPoint.y);
        }
        moveToPoint(currentPoint,endPoint);

    }

    private static GridPoint getNextPoint(GridPoint currentPoint, GridPoint goalPoint){
        int x  = 0,y = 0;

        if(Math.random()>0.5){
            if(goalPoint.x>currentPoint.x){
                x= currentPoint.x + (int)(Math.random() * ((goalPoint.x - currentPoint.x) + 1));
            }
            else{
                x = goalPoint.x + (int)(Math.random() * ((currentPoint.x - goalPoint.x) + 1));
            }
        }
        else{
            x= (0 + (int)(Math.random() * lvl.levelsize-1));
        }
        if(Math.random()>0.5){
            if(goalPoint.y>currentPoint.y){
                y= currentPoint.y + (int)(Math.random() * ((goalPoint.y - currentPoint.y) + 1));
            }
            else{
                y = goalPoint.y + (int)(Math.random() * ((currentPoint.y - goalPoint.y) + 1));
            }
        }
        else{
            y= (0 + (int)(Math.random() * lvl.levelsize-1));
        }

        return new GridPoint(x,y);
    }

    private static void moveToPoint(GridPoint currentPoint, GridPoint goalPoint){
        while(goalPoint.x!=currentPoint.x){
            if(goalPoint.x>currentPoint.x){
                currentPoint.x++;
            }
            else{
                currentPoint.x--;
            }
            lvl.typemap[currentPoint.x][currentPoint.y] = Tiletype.FREE;
        }
        while(goalPoint.y!=currentPoint.y){
            if(goalPoint.y>currentPoint.y){
                currentPoint.y++;
            }
            else{
                currentPoint.y--;
            }
            lvl.typemap[currentPoint.x][currentPoint.y] = Tiletype.FREE;
        }
    }

    public static class GridPoint{
        public int x, y;

        public GridPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
