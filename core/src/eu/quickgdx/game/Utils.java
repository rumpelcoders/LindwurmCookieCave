package eu.quickgdx.game;

/**
 * Created by chzellot on 29.04.17.
 */

public class Utils {

    public static double calculateRandomX(int mapWidth) {
        double t = 2 * Math.PI * Math.random();
        double u = Math.random() + Math.random();
        double r;
        if (u > 1) {
            r = 2 - u;
        } else {
            r = u;
        }
        return Math.abs(r * Math.cos(t)) * mapWidth;
    }

    public static double calculateRandomY(int mapWidth) {
        double t = 2 * Math.PI * Math.random();
        double u = Math.random() + Math.random();
        double r;
        if (u > 1) {
            r = 2 - u;
        } else {
            r = u;
        }
        return Math.abs(r * Math.sin(t)) * mapWidth;
    }

    public static int randRange(int minimum, int maximum) {
        return minimum + (int) (Math.random() * maximum);
    }
}
