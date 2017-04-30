package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import eu.quickgdx.game.CamObject;
import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.ScreenManager;

/**
 * Created by putzchri on 28.04.2017.
 */

public class GameOverScreen extends ScreenAdapter {

    public QuickGdx parentGame;
    private final SpriteBatch batch;
    private final CamObject cam;
    ScreenViewport screenViewport;

    Texture backgroundImage;

    private BitmapFont gameOverFont;

    public GameOverScreen(QuickGdx parentGame) {
        this.parentGame = parentGame;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        gameOverFont = parentGame.getAssetManager().get("fonts/retro.fnt");
        gameOverFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Create camera that projects the game onto the actual screen size.
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
        // draw bgImage
        batch.draw(backgroundImage, 0, 0, Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        //TODO Maybe more elegant solution
        String[] gameOver = ("Statistics:\n").split("\\n");
                //"The winner is: Player " + parentGame.getLastWinner().getPlaynr() +"\n").split("\\n");
        for (int i = gameOver.length-1,j=0; i >= 0; i--,j++) {
            gameOverFont.draw(batch, gameOver[i].toUpperCase(), Constanze.GAME_WIDTH/2, Constanze.GAME_HEIGHT/2 + j*50);
        }

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
    }
}
