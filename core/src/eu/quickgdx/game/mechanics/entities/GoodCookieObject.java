package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.LastWinner;
import eu.quickgdx.game.ScreenManager;
import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by putzchri on 28.04.2017.
 */

public class GoodCookieObject extends AbstractCookieObject {

    private final int boundsSize;

    public GoodCookieObject(Vector2 position, World world) {
        super(position, world);
        boundsSize = Constants.TILESIZE - Constants.TILESIZE / 3;
    }

    @Override
    public void update(float delta){
        Vector2 newPosition = new Vector2(Math.round(position.x), Math.round(position.y));
        Rectangle newBounds = new Rectangle(newPosition.x, newPosition.y, boundsSize, boundsSize);
        this.bounds = newBounds;
    }

    @Override
    public void hit(PlayerCharacterObject player) {
        world.gameplayScreen.parentGame.setLastWinner(new LastWinner(player.getPlaynr()));
        world.gameplayScreen.parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.GameOver);
    }
}
