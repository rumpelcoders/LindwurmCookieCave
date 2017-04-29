package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;

/**
 * Created by Veit on 29.04.2017.
 */
public abstract class Projectile extends MoveableObject {

    public int direction;

    public Projectile(Vector2 position, World world, int direction) {
        super(position, world);
    }

    public void hit(GameObject object){
        //if(object)
    }
}
