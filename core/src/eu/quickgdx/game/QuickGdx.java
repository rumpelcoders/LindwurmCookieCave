package eu.quickgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
    private int numberOfPlayers;

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
        assMan.load("sfx/mapchange/mapchange_2.mp3", Sound.class);
        assMan.load("sfx/mapchange/mapchange_3.mp3", Sound.class);
        assMan.load("sfx/mapchange/mapchange_4.mp3", Sound.class);
        assMan.load("sfx/mapchange/mapchange_5.mp3", Sound.class);
        assMan.load("sfx/mapchange/mapchange_6.mp3", Sound.class);

        assMan.load("sfx/cookie1.mp3", Sound.class);
        assMan.load("sfx/cookie2.mp3", Sound.class);
        assMan.load("sfx/cookie3.mp3", Sound.class);
        assMan.load("sfx/cookie4.mp3", Sound.class);
        assMan.load("sfx/vomit.mp3", Sound.class);

        assMan.load("sfx/dub.mp3", Music.class);

        assMan.load("music/game-loop-2-1.mp3", Music.class);
        assMan.load("music/game-loop-2-2.mp3", Music.class);
        assMan.load("music/game-loop-2-3.mp3", Music.class);
        assMan.load("music/game-loop-2-4.mp3", Music.class);
        assMan.load("music/game-loop-3-1.mp3", Music.class);
        assMan.load("music/game-loop-3-2.mp3", Music.class);

        //HUD
        assMan.load("hud/life_small.png", Texture.class);

        //Fonts
        assMan.load("fonts/RabbidHighwaySignII.fnt", BitmapFont.class);
        assMan.load("fonts/retro.fnt", BitmapFont.class);

        //Entities
        assMan.load("gameplay/spritesheet.png", Texture.class);
        assMan.load("gameplay/movingAnimation_Down.png", Texture.class);
        assMan.load("gameplay/movingAnimation_Down.png", Texture.class);
        for (Constanze constanze : Constanze.values()) {
            assMan.load(constanze.path, constanze.toLOad);
        }


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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
