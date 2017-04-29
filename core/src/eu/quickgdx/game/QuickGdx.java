package eu.quickgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Main entry point in our game. Asset loading should be done here.
 * provides you with the basic manager classes (assets, screen, sound) and the animator helper class
 */
public class QuickGdx extends ApplicationAdapter {
    private SpriteBatch batch;
    private AssetManager assMan;
    private ScreenManager screenManager;
    private SoundManager soundManager;
    private Animator animator;
    private LastWinner lastWinner;

    // gives the original size for all screen working with the scaling orthographic camera
    // set in DesktopLauncher to any resolution and it will be scaled automatically.


    @Override
    public void create() {
        screenManager = new ScreenManager(this);
        soundManager = new SoundManager(this);
        animator = new Animator(this);

        // LOAD ASSETS HERE ...
        // Loading screen will last until the last one is loaded.
        assMan = new AssetManager();
        // for the menu
        assMan.load("menu/Ravie_42.fnt", BitmapFont.class);
        assMan.load("menu/Ravie_72.fnt", BitmapFont.class);
        assMan.load("menu/menu_background.jpg", Texture.class);
        // for the credits
        assMan.load("credits/gradient_top.png", Texture.class);
        assMan.load("credits/gradient_bottom.png", Texture.class);
        // for the sounds
        assMan.load("sfx/blip.wav", Sound.class);
        assMan.load("sfx/explosion.wav", Sound.class);
        assMan.load("sfx/hit.wav", Sound.class);
        assMan.load("sfx/jump.wav", Sound.class);
        assMan.load("sfx/laser.wav", Sound.class);
        assMan.load("sfx/pickup.wav", Sound.class);
        assMan.load("sfx/powerup.wav", Sound.class);

        assMan.load("sfx/mapchange/mapchange_1.mp3", Sound.class);
        assMan.load("sfx/mapchange/mapchange_2.wav", Sound.class);
        assMan.load("sfx/mapchange/mapchange_3.wav", Sound.class);
        assMan.load("sfx/mapchange/mapchange_4.wav", Sound.class);
        assMan.load("sfx/mapchange/mapchange_5.wav", Sound.class);
        assMan.load("sfx/mapchange/mapchange_6.wav", Sound.class);

        //HUD
        assMan.load("hud/life_small.png", Texture.class);

        //Fonts
        assMan.load("fonts/RabbidHighwaySignII.fnt", BitmapFont.class);

        //Entities
        assMan.load("gameplay/spritesheet.png", Texture.class);
        assMan.load("gameplay/movingAnimation_Down.png", Texture.class);
        assMan.load("gameplay/movingAnimation_Down.png", Texture.class);
        assMan.load(Constants.ASSET_PLAYER, Texture.class);
        assMan.load(Constants.ASSET_OWL_FRONT, Texture.class);
        assMan.load(Constants.ASSET_WIZARD_FRONT, Texture.class);
        assMan.load(Constants.ASSET_FAIRY_FRONT, Texture.class);
        assMan.load(Constants.ASSET_SPACEORG_FRONT, Texture.class);
        assMan.load(Constants.ASSET_WIZARD_FRONT, Texture.class);
        assMan.load(Constants.ASSET_FOG, Texture.class);
        assMan.load(Constants.ASSET_COOKIE_BAG, Texture.class);

        loadMapAssets();

    }

    protected void loadMapAssets() {
        assMan.load(Constants.ASSET_MAP_CEILING_BORDERLESS_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_E, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_E_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_N, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NE_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NSE_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NSWE, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NSW_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NS_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NWE_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_NW_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_N_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_S, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_SE_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_SWE_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_SW_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_S_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_W, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_WE_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_CEILING_W_EMPTY, Texture.class);
        assMan.load(Constants.ASSET_MAP_FLOOR, Texture.class);
        assMan.load(Constants.ASSET_MAP_FLOOR_WALL, Texture.class);
        assMan.load(Constants.ASSET_MAP_FLOOR_WALL_ENDING_LEFT, Texture.class);
        assMan.load(Constants.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT, Texture.class);
        assMan.load(Constants.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT_LEFT, Texture.class);
    }

    @Override
    public void render() {
        screenManager.getCurrentScreen().render(Gdx.graphics.getDeltaTime());
    }

    public AssetManager getAssetManager() {
        return assMan;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public Animator getAnimator() {
        return animator;
    }

    public LastWinner getLastWinner() {
        return lastWinner;
    }

    public void setLastWinner(LastWinner lastWinner) {
        this.lastWinner = lastWinner;
    }
}
