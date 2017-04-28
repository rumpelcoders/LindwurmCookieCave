package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class GoodCookieObject extends MoveableObject {


    public GoodCookieObject(Vector2 position, World world) {
        super(position, world);
        this.bounds = new Rectangle(position.x + 16, position.y, 10, 10);
    }

    public boolean hit() {

        return false;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        
    }
}
