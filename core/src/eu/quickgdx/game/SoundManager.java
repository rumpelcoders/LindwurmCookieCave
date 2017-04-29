package eu.quickgdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by Mathias Lux, mathias@juggle.at, 05.02.2016.
 * Edited and adapted by Xifiggam xifiggam@xifiggam.eu
 */
public class SoundManager {
    private QuickGdx parentGame;
    private HashMap<String, String> event2sound;
    private HashMap<String, String> event2music;
    public Music currentMusic;
    Array<TimedSoundEvent> soundEventArray = new Array<TimedSoundEvent>();

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

        event2music = new HashMap<String, String>(1);
        event2music.put("bg1", "sfx/dub.mp3");

    }

    public void update(float delta){
        for (TimedSoundEvent tse: soundEventArray) {
            tse.update(delta);
        }
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

    public void startBgMusic(String track, boolean loop) {
        if(currentMusic!=null)
            currentMusic.stop();
        if (event2music.get(track) != null) {
            currentMusic = parentGame.getAssetManager().get(event2music.get(track), Music.class);
            parentGame.getAssetManager().get(event2music.get(track), Music.class).setLooping(loop);
            parentGame.getAssetManager().get(event2music.get(track), Music.class).play();
        } else {
            System.err.println("Event unknown.");
        }
    }

    public void playTimedEvent(String event, float time) {
        soundEventArray.add(new TimedSoundEvent(time, soundEventArray, event));
    }

    private class TimedSoundEvent {
        public float timeToPlay;
        Array<TimedSoundEvent> containingArray;
        String event;

        public TimedSoundEvent(float timeToPlay, Array<TimedSoundEvent> containingArray, String event) {
            this.timeToPlay = timeToPlay;
            this.containingArray = containingArray;
            this.event = event;
        }

        public void update(float delta){
            timeToPlay -=delta;
            if(timeToPlay <=delta){
                playEvent(event);
                containingArray.removeValue(this,false);
            }
        }
    }
}
