package eu.quickgdx.game.mechanics.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.text.DecimalFormat;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by Veit on 23.02.2016.
 */
public class HUD {
    private String debugText;
    World world;
    static BitmapFont textFont;
    private GlyphLayout layout = new GlyphLayout();
    boolean multiplierUp = false;
    float multiplierTime = 1f;
    DecimalFormat df = new DecimalFormat("#.##");
    Texture hitpointIndicator;

    public HUD(World world) {
        this.world = world;
        this.debugText = "";
        textFont = world.gameplayScreen.parentGame.getAssetManager().get("fonts/RabbidHighwaySignII.fnt", BitmapFont.class);
        hitpointIndicator = world.gameplayScreen.parentGame.getAssetManager().get("hud/life_small.png");
    }

    public void setDebugText(String debugText) {
        this.debugText = debugText;
    }

    public void render(float delta, SpriteBatch hudBatch) {
        //draws the hitpoint indicator
//        layout.setText(textFont, "Position: " + object.getPosition().x + " | " + object.getPosition().y);
        Texture lindwurm = this.world.gameplayScreen.parentGame.getAssetManager().get(Constanze.ASSET_LINDWURM_STANDARD.path, Texture.class);
        hudBatch.draw(lindwurm, 0, 0);
        if (debugText != null && debugText.length() > 0) {
            layout.setText(textFont, debugText);
        }
        textFont.draw(hudBatch, layout, Constanze.GAME_WIDTH / 2 - layout.width / 2, Constanze.GAME_HEIGHT - layout.height - 650);
    }
}
