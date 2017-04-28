package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.ScreenManager;
import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class GoodCookieObject extends MoveableObject {


    public GoodCookieObject(Vector2 position, World world) {
        super(position, world);
        int boundsSize = Constants.TILESIZE - Constants.TILESIZE / 3;
        this.bounds = new Rectangle(position.x + Constants.TILESIZE, position.y, boundsSize, boundsSize);
    }

    public void hit(GameObject player) {
        world.gameplayScreen.parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.GameOver);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {

    }
}
