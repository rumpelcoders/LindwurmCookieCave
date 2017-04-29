package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.ScreenManager;

/**
 * Created by putzchri on 28.04.2017.
 */

public class GameOverScreen extends ScreenAdapter {

    public QuickGdx parentGame;
    private final SpriteBatch batch;
    private final OrthographicCamera cam;

    Texture backgroundImage;

    private BitmapFont gameOverFont;

    public GameOverScreen(QuickGdx parentGame) {
        this.parentGame = parentGame;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        gameOverFont = parentGame.getAssetManager().get("menu/Ravie_42.fnt");

        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        handleInput();
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw bgImage
        batch.draw(backgroundImage, 0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        //TODO Maybe more elegant solution
        String[] gameOver = ("Game Over!\n" +
                "The winner is: " + parentGame.getLastWinner().getPlaynr() +"\n").split("\\n");
        for (int i = gameOver.length-1,j=0; i >= 0; i--,j++) {
            gameOverFont.draw(batch, gameOver[i], Constants.GAME_WIDTH/2,Constants.GAME_HEIGHT/2 + j*50);
        }

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
    }
}
