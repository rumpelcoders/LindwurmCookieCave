package eu.quickgdx.game.mechanics.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.text.DecimalFormat;

import eu.quickgdx.game.AssetManager;
import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.Utils;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.states.global.GlobalFogState;

/**
 * Created by Veit on 23.02.2016.
 */
public class HUD {
    private final Array<Constanze> lindWurmIdleAssets;
    private final AssetManager assetManager;
    private String debugText;
    World world;
    static BitmapFont textFont;
    private GlyphLayout layout = new GlyphLayout();
    boolean multiplierUp = false;
    float multiplierTime = 1f;
    DecimalFormat df = new DecimalFormat("#.##");
    Texture hitpointIndicator;
    float switchTimer = 0;
    float switchTimerReset = 5;

    private Texture lindwurm;

    public HUD(World world) {
        this.world = world;
        this.debugText = "";
        textFont = world.gameplayScreen.parentGame.getAssetManager().get("fonts/RabbidHighwaySignII.fnt", BitmapFont.class);
        hitpointIndicator = world.gameplayScreen.parentGame.getAssetManager().get("hud/life_small.png");
        lindWurmIdleAssets = Constanze.getByGroup(Constanze.GROUP_LINDWURM_IDLE);
        assetManager = this.world.gameplayScreen.parentGame.getAssetManager();
        lindwurm = assetManager.get(Constanze.ASSET_LINDWURM_STANDARD.path);
        switchTimer = switchTimerReset;
    }

    public void setDebugText(String debugText) {
        this.debugText = debugText;
    }

    public void render(float delta, SpriteBatch hudBatch) {
        //draws the hitpoint indicator
//        layout.setText(textFont, "Position: " + object.getPosition().x + " | " + object.getPosition().y);

        if (this.world.hasGlobalState(GlobalFogState.class)) {
            lindwurm = assetManager.get(Constanze.ASSET_LINDWURM_BLOW.path, Texture.class);
            switchTimer = 0;
        } else {
            switchTimer -= delta;
            if (switchTimer < 0) {
                lindwurm = assetManager.get(lindWurmIdleAssets.get(Utils.randRange(0, lindWurmIdleAssets.size)).path);
                switchTimer = switchTimerReset;
            }
        }


        int lindwurmStart = lindwurm.getWidth() / 2 * -1;
        int lindwurmEnd = lindwurm.getWidth() / 2;

        float xStart = (-1) * Constanze.GAME_WIDTH / 2 + Constanze.borderLeft - Constanze.TILESIZE;
        float xEnd = Constanze.GAME_WIDTH / 2 - Constanze.borderRight;
        float yTop = Constanze.GAME_HEIGHT / 2 - Constanze.borderTop;
        float yBottom = (-1) * Constanze.GAME_HEIGHT / 2 + Constanze.borderBottom;
        hudBatch.draw(assetManager.getTexture(Constanze.ASSET_ARENA_NW), xStart, yTop);
        hudBatch.draw(assetManager.getTexture(Constanze.ASSET_ARENA_NE), xEnd, yTop);
        hudBatch.draw(assetManager.getTexture(Constanze.ASSET_ARENA_SW), xStart, yBottom);
        hudBatch.draw(assetManager.getTexture(Constanze.ASSET_ARENA_SE), xEnd, yBottom);
        float x = xStart;
        Texture arenaN = assetManager.getTexture(Constanze.ASSET_ARENA_N);
        Texture arenaS = assetManager.getTexture(Constanze.ASSET_ARENA_S);
        while ((x + Constanze.TILESIZE) < xEnd) {
            x += Constanze.TILESIZE;

            hudBatch.draw(arenaN, x, yTop);
            hudBatch.draw(arenaS, x, yBottom);
        }
        float y = yBottom;
        Texture arenaW = assetManager.getTexture(Constanze.ASSET_ARENA_W);
        Texture arenaE = assetManager.getTexture(Constanze.ASSET_ARENA_E);
        while ((y + Constanze.TILESIZE) < yTop){
            y += Constanze.TILESIZE;
            hudBatch.draw(arenaW, xStart, y);
            hudBatch.draw(arenaE, xEnd, y);
        }

        hudBatch.draw(lindwurm, lindwurmStart, (Constanze.GAME_HEIGHT / 2) - lindwurm.getHeight());
        if (debugText != null && debugText.length() > 0) {
            layout.setText(textFont, debugText);
        }
        textFont.draw(hudBatch, layout, Constanze.GAME_WIDTH / 2 - layout.width / 2, Constanze.GAME_HEIGHT - layout.height - 650);
    }
}
