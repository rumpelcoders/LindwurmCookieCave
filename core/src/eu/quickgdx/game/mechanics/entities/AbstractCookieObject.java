package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by chzellot on 29.04.17.
 */

public abstract class AbstractCookieObject extends MovableCollisionObject {

    private final Texture cookieTexture;
    private final int boundsSize;

    public AbstractCookieObject(Vector2 position, World world) {
        super(position, world);
        boundsSize = Constanze.TILESIZE - Constanze.TILESIZE / 4;
        this.bounds = new Rectangle(position.x, position.y, boundsSize, boundsSize);
        cookieTexture = world.gameplayScreen.parentGame.getAssetManager().get(Constanze.ASSET_COOKIE_BAG.path, Texture.class);

    }

    @Override
    public void update(float delta){
        Vector2 newPosition = new Vector2(Math.round(position.x), Math.round(position.y));
        Rectangle newBounds = new Rectangle(newPosition.x, newPosition.y, boundsSize, boundsSize);
        this.bounds = newBounds;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        spriteBatch.draw(cookieTexture, position.x, position.y);
    }
}
