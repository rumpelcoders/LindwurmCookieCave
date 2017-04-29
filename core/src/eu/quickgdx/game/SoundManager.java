package eu.quickgdx.game;

import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Mathias Lux, mathias@juggle.at, 05.02.2016.
 * Edited and adapted by Xifiggam xifiggam@xifiggam.eu
 */
public class SoundManager {
    private QuickGdx parentGame;
    private HashMap<String, String> event2sound;

    public SoundManager(QuickGdx parentGame) {
        this.parentGame = parentGame;

        // register the available events.
        event2sound = new HashMap<String, String>(20);
        event2sound.put("blip", "sfx/blip.wav");
        event2sound.put("explode", "sfx/explosion.wav");
        event2sound.put("hit", "sfx/hit.wav");
        event2sound.put("jump", "sfx/jump.wav");
        event2sound.put("laser", "sfx/laser.wav");
        event2sound.put("pickup", "sfx/pickup.wav");
        event2sound.put("powerup", "sfx/powerup.wav");

        event2sound.put("cookie1", "sfx/cookie1.mp3");
        event2sound.put("cookie2", "sfx/cookie2.mp3");
        event2sound.put("cookie3", "sfx/cookie3.mp3");
        event2sound.put("cookie4", "sfx/cookie4.mp3");
        event2sound.put("vomit", "sfx/vomit.mp3");

        event2sound.put("mapchange1", "sfx/mapchange/mapchange_1.mp3");
        event2sound.put("mapchange2","sfx/mapchange/mapchange_2.mp3");
        event2sound.put("mapchange3","sfx/mapchange/mapchange_3.mp3");
        event2sound.put("mapchange4","sfx/mapchange/mapchange_4.mp3");
        event2sound.put("mapchange5","sfx/mapchange/mapchange_5.mp3");
        event2sound.put("mapchange6","sfx/mapchange/mapchange_6.mp3");

    }

    /**
     * Plays an event registered in the constructor. Make sure that (i) the event is known and (ii) the
     * asset is loaded in the constructor of GdxGame.
     *
     * @param event
     */
    public void playEvent(String event) {
        if (event2sound.get(event) != null) {
            parentGame.getAssetManager().get(event2sound.get(event), Sound.class).play();
        } else {
            System.err.println("Event unknown.");
        }
    }

}
