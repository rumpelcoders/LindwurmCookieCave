package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import eu.quickgdx.game.CamObject;
import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.ScreenManager;

/**
 * Created by putzchri on 30.04.2017.
 */

public class ChoosingScreen extends ScreenAdapter {

    public QuickGdx parentGame;
    Texture backgroundImage;

    private BitmapFont choosingFont;
    private final CamObject cam;
    private final SpriteBatch batch;
    ScreenViewport screenViewport;
    String[] playerStrings = {"2", "3", "4"};
    int currentMenuItem = 0;
    float offsetLeft = Constanze.GAME_WIDTH / 16, offsetTop = Constanze.GAME_HEIGHT / 16, offsetY = Constanze.GAME_HEIGHT / 16;
    int nrOfPlayers = 2;

    public ChoosingScreen(QuickGdx parentGame) {
        this.parentGame = parentGame;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        choosingFont = parentGame.getAssetManager().get("fonts/retro.fnt");
        choosingFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        cam = new CamObject(0);
        screenViewport = new ScreenViewport(cam);
        screenViewport.update(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        screenViewport.setScreenBounds(0, 0, Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        cam.position.set(0, 0, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        handleInput();
        // camera:
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        screenViewport.apply(true);
        cam.update();
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        choosingFont.draw(batch, "CHOOSE NUMBER OF PLAYERS: ",offsetLeft,Constanze.GAME_HEIGHT - offsetTop);
        choosingFont.draw(batch, "PRESS ENTER TO START THE GAME", offsetLeft, Constanze.GAME_HEIGHT / 4);
        for (int i = 0; i < playerStrings.length; i++) {
            if (i == currentMenuItem) choosingFont.setColor(0.2f, 1f, 0.2f, 1f);
            else choosingFont.setColor(0.2f, 0.2f, 1f, 1f);
            choosingFont.draw(batch, playerStrings[i], offsetLeft, Constanze.GAME_HEIGHT - offsetTop - 100 - i * offsetY);
        }
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            parentGame.setNumberOfPlayers(nrOfPlayers);
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Game);
        } else if (Gdx.input.justTouched()) {
            Vector3 touchWorldCoords = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
            // find the menu item ..
            for (int i = 0; i < playerStrings.length; i++) {
                if (touchWorldCoords.x > offsetLeft) {
                    float pos = Constanze.GAME_HEIGHT - offsetTop * 2 - i * offsetY;
                    if (touchWorldCoords.y < pos && touchWorldCoords.y > pos - choosingFont.getLineHeight()) {
                        currentMenuItem = i;
                        nrOfPlayers = i+2;
                        System.out.println(nrOfPlayers);
                    }
                }
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
    }
}
