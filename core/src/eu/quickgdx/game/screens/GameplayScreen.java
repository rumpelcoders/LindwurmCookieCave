package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

    //float offsetLeft = Constanze.GAME_WIDTH / 8, offsetTop = Constanze.GAME_WIDTH / 8, offsetY = Constanze.GAME_HEIGHT / 8;


    public GameplayScreen(QuickGdx game) {
        this.parentGame = game;
        this.nrPlayers = game.getNumberOfPlayers();
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.gameCams = new Array<>();
        this.viewports = new Array<>();
        for (int playNr = 0; playNr < nrPlayers; playNr++) {
//            OrthographicCamera gameCam = new OrthographicCamera((Constanze.GAME_WIDTH / 2), (Constanze.GAME_HEIGHT / 2));
            CamObject gameCam = new CamObject(playNr);
            ScreenViewport screenViewport = new ScreenViewport(gameCam);
            viewports.add(screenViewport);
            gameCams.add(gameCam);
        }
        hudCam = new OrthographicCamera(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        ScreenViewport screenViewport = new ScreenViewport(hudCam);
        viewports.add(screenViewport);
//        hudCam.position.set(hudCam.viewportWidth / screenMultiplikator, hudCam.viewportHeight / screenMultiplikator, 0);
        hudCam.update();
        resize(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        this.world = new World(this, nrPlayers);
        parentGame.getSoundManager().startBgMusic();
    }

    @Override
    public void resize(int width, int height) {
        Constanze.GAME_HEIGHT = height;
        Constanze.GAME_WIDTH = width;
        for (int playNr = 0; playNr < nrPlayers; playNr++) {
//            OrthographicCamera gameCam = new OrthographicCamera((Constanze.GAME_WIDTH / 2), (Constanze.GAME_HEIGHT / 2));
            CamObject gameCam = this.gameCams.get(playNr);
            Viewport screenViewport = this.viewports.get(playNr);
            screenViewport.update(width / 2, height / 2);
            int xStart = 0;
            int withView = (width - Constanze.borderLeft - Constanze.borderRight) / 2;
            int yStart = 0;
            int heightView = (height - Constanze.borderTop - Constanze.borderBottom) / 2;
            switch (playNr) {
                case 0:
                    xStart = Constanze.borderLeft;
                    yStart = Constanze.borderBottom + heightView;
                    break;
                case 1:
                    xStart = width - Constanze.borderRight - withView;
                    yStart = Constanze.borderBottom + heightView;
                    break;
                case 2:
                    xStart = Constanze.borderLeft;
                    yStart = Constanze.borderBottom;
                    break;
                case 3:
                    xStart = width - Constanze.borderRight - withView;
                    yStart = Constanze.borderBottom;
                    break;
            }
            screenViewport.setScreenBounds(xStart, yStart, withView, heightView);
//            gameCam.zoom += 1.2f;
            gameCam.position.set(0, 0, 0);
            gameCam.update();

        }
        this.viewports.get(this.viewports.size - 1).update(width, height);
//        this.viewports.get(this.viewports.size - 1).setScreenBounds(0, 0, width, height);
        this.hudCam.position.set(0, 0, 0);
        this.hudCam.update();

    }

    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);
        parentGame.getSoundManager().update(delta);
        for (int i = 0; i < gameCams.size; i++) {
            CamObject gameCam = gameCams.get(i);
            gameBatch.setProjectionMatrix(gameCam.combined);
            viewports.get(i).apply();
            world.render(delta, gameBatch, gameCam);
            gameCam.update();

        }

        hudBatch.setProjectionMatrix(hudCam.combined);
        viewports.get(viewports.size - 1).apply();
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
