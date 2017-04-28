package eu.quickgdx.game.mechanics.level;

import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.entities.GameObject;
import eu.quickgdx.game.mechanics.entities.MoveableObject;

/**
 * Created by Veit on 28.04.2017.
 */
public class LevelGenerator {

    public static Level generateLevel(int levelsize, Array<ControlledObject> playerlist, MoveableObject goodcookie){
        Level lvl = new Level(levelsize);
        for (ControlledObject player: playerlist) {
            generatePath(lvl, player, goodcookie);
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
        int startX = start.getTileX();
        int startY = start.getTileY();
        lvl.typemap[startX][startY] = Tiletype.FREE;
        int currentX = startX;
        int currentY = startY;
        int goalX,goalY;

        for (int i = 0; i< (1 + (int)(Math.random() * 5));i++){
            goalX = (0 + (int)(Math.random() * lvl.levelsize-1));
            goalY = (0 + (int)(Math.random() * lvl.levelsize-1));
            while(goalX!=currentX){
                if(currentX<goalX){
                    currentX++;
                }
                else if(currentX>goalX){
                    currentX--;
                }
                lvl.typemap[currentX][currentY] = Tiletype.FREE;
            }
            while(goalY!=currentY){
                if(currentY<goalY){
                    currentY++;
                }
                else if(currentY>goalY){
                    currentY--;
                }
                lvl.typemap[currentX][currentY] = Tiletype.FREE;
            }
        }
        goalX = end.getTileX();
        goalY = end.getTileY();
        while(goalX!=currentX){
            if(currentX<goalX){
                currentX++;
            }
            else if(currentX>goalX){
                currentX--;
            }
            lvl.typemap[currentX][currentY] = Tiletype.FREE;
        }
        while(goalY!=currentY){
            if(currentY<goalY){
                currentY++;
            }
            else if(currentY>goalY){
                currentY--;
            }
            lvl.typemap[currentX][currentY] = Tiletype.FREE;
        }

    }
}
