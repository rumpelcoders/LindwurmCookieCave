package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.QuickGdx;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class GameplayScreen extends ScreenAdapter {
    private final SpriteBatch gameBatch;
    private final SpriteBatch hudBatch;
    //public final OrthographicCamera gameCam;
    public final OrthographicCamera player1Cam;
    public final OrthographicCamera player2Cam;
    public final OrthographicCamera player3Cam;
    public final OrthographicCamera player4Cam;
    public final OrthographicCamera hudCam;
    public QuickGdx parentGame;

    Texture backgroundImage;
    BitmapFont menuFont;
    World world;
    String[] menuStrings = {"Play", "Credits", "Exit"};
    int currentMenuItem = 0;

    float offsetLeft = Constants.GAME_WIDTH / 8, offsetTop = Constants.GAME_WIDTH / 8, offsetY = Constants.GAME_HEIGHT / 8;


    public GameplayScreen(QuickGdx game) {
        this.parentGame = game;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //gameCam = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        player1Cam = new OrthographicCamera(Constants.GAME_WIDTH/4, Constants.GAME_HEIGHT);
        player2Cam = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        player3Cam = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        player4Cam = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        float screenMultiplikator = 2f;
        player1Cam.zoom += 1f;
        player1Cam.position.set(player1Cam.viewportWidth / screenMultiplikator, player1Cam.viewportHeight / screenMultiplikator, 0);
        player1Cam.update();
        player2Cam.zoom += 1f;
        player2Cam.position.set(player2Cam.viewportWidth / screenMultiplikator, player2Cam.viewportHeight / screenMultiplikator, 0);
        player2Cam.update();
        player3Cam.zoom += 1f;
        player3Cam.position.set(player3Cam.viewportWidth / screenMultiplikator, player3Cam.viewportHeight / screenMultiplikator, 0);
        player3Cam.update();
        player4Cam.zoom += 1f;
        player4Cam.position.set(player4Cam.viewportWidth / screenMultiplikator, player4Cam.viewportHeight / screenMultiplikator, 0);
        player4Cam.update();
        hudCam = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        hudCam.position.set(hudCam.viewportWidth / screenMultiplikator, hudCam.viewportHeight / screenMultiplikator, 0);
        hudCam.update();
        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        this.world = new World(this);
    }

    @Override
    public void render(float delta) {
        handleInput();
        //gameBatch.setProjectionMatrix(gameCam.combined);
        gameBatch.setProjectionMatrix(player1Cam.combined);
        hudBatch.setProjectionMatrix(hudCam.combined);
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Left upper quarter
        Gdx.gl.glViewport(0, Gdx.graphics.getHeight(),
                Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

        //Left bottom quarter
        Gdx.gl.glViewport(0, 0,
                Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

//        //Right upper quarter
//        Gdx.gl.glViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
//                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        //Right bottom quarter
//        Gdx.gl.glViewport(Gdx.graphics.getWidth(), 0,
//                Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);


        world.update(delta);
        world.render(delta, gameBatch);
        world.renderHUD(delta, hudBatch);
        //gameCam.update();
        player1Cam.update();
        hudCam.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
        }
    }

}
