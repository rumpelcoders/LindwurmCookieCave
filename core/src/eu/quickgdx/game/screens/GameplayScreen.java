package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import eu.quickgdx.game.CamObject;
import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class GameplayScreen extends ScreenAdapter {
    private final SpriteBatch gameBatch;
    private final SpriteBatch hudBatch;
    public final Array<CamObject> gameCams;
    public final Array<Viewport> viewports;
    public final OrthographicCamera hudCam;
    private final int nrPlayers;
    public QuickGdx parentGame;

    Texture backgroundImage;
    BitmapFont menuFont;
    World world;
    String[] menuStrings = {"Play", "Credits", "Exit"};
    int currentMenuItem = 0;

    float offsetLeft = Constanze.GAME_WIDTH / 8, offsetTop = Constanze.GAME_WIDTH / 8, offsetY = Constanze.GAME_HEIGHT / 8;


    public GameplayScreen(QuickGdx game) {
        this.parentGame = game;
        this.nrPlayers = 4;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        float screenMultiplikator = 2f;
        this.gameCams = new Array<>();
        this.viewports = new Array<>();
        for (int playNr = 0; playNr < nrPlayers; playNr++) {
//            OrthographicCamera gameCam = new OrthographicCamera((Constanze.GAME_WIDTH / 2), (Constanze.GAME_HEIGHT / 2));
            CamObject gameCam = new CamObject(playNr);
            FitViewport screenViewport = new FitViewport(Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT / 2, gameCam);
            screenViewport.update(Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT / 2);
            int xStart = 0;
            int xEnd = 0;
            int yStart = 0;
            int yEnd = 0;
            switch (playNr) {
                case 0:
                    xStart = 10;
                    xEnd = 950;
                    yStart = 10;
                    yEnd = 530;
                    break;
                case 1:
                    xStart = 970;
                    xEnd = 1910;
                    yStart = 10;
                    yEnd = 530;
                    break;
                case 2:
                    xStart = 10;
                    xEnd = 950;
                    yStart = 550;
                    yEnd = 1070;
                    break;
                case 3:
                    xStart = 970;
                    xEnd = 1910;
                    yStart = 550;
                    yEnd = 1070;
                    break;
            }
            screenViewport.setScreenBounds(xStart, yStart, xEnd, yEnd);
            viewports.add(screenViewport);
//            gameCam.zoom += 2.5f;
            gameCam.position.set(0, 0, 0);
            gameCam.update();
            gameCams.add(gameCam);
        }

        hudCam = new OrthographicCamera(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        hudCam.position.set(hudCam.viewportWidth / screenMultiplikator, hudCam.viewportHeight / screenMultiplikator, 0);
        hudCam.update();
        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        this.world = new World(this, nrPlayers);
    }

    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);
        for (int i = 0; i < gameCams.size; i++) {
            CamObject gameCam = gameCams.get(i);
            viewports.get(i).apply();
            gameBatch.setProjectionMatrix(gameCam.combined);
            gameCam.update();
            world.render(delta, gameBatch);

        }

        hudBatch.setProjectionMatrix(hudCam.combined);
        world.renderHUD(delta, hudBatch);
        hudCam.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
        }
    }

}
