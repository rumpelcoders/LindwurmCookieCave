package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import eu.quickgdx.game.CamObject;
import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.ScreenManager;

/**
 * Created by putzchri on 30.04.2017.
 */

public class ContinueExitGameScreen extends ScreenAdapter {
    public QuickGdx parentGame;
    private final SpriteBatch batch;
    private final CamObject cam;
    ScreenViewport screenViewport;
    Array<Texture> playerChars;
    Texture backgroundImage;

    private BitmapFont gameOverFont;
    float offsetLeft = Constanze.GAME_WIDTH / 16, offsetTop = Constanze.GAME_HEIGHT / 16, offsetY = Constanze.GAME_HEIGHT / 16;

    public ContinueExitGameScreen(QuickGdx parentGame) {
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
        playerChars = new Array<>();
        playerChars.add(new Texture(Constanze.ASSET_OWL_FRONT.path));
        playerChars.add(new Texture(Constanze.ASSET_WIZARD_FRONT.path));
        playerChars.add(new Texture(Constanze.ASSET_SPACEORG_FRONT.path));
        playerChars.add(new Texture(Constanze.ASSET_FAIRY_FRONT.path));

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
        if(parentGame.getScreenManager().getCurrentState() != ScreenManager.ScreenState.Game) {
            //TODO Maybe more elegant solution
            String[] gameOver = ("Game Over!\n" +
                    "The winner is: Player " + parentGame.getLastWinner().getPlaynr() + "\n").split("\\n");
            for (int i = 0; i < gameOver.length; i++) {
                gameOverFont.draw(batch, gameOver[i].toUpperCase(), (-1)*gameOver[i].length()/2+Constanze.GAME_WIDTH/2, Constanze.GAME_HEIGHT / 2 + i * Constanze.TILESIZE);
            }
            switch (parentGame.getLastWinner().getPlaynr()) {
                case 1:
                    batch.draw(playerChars.get(0),Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT / 2 - 3 * 64, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                    break;
                case 2:
                    batch.draw(playerChars.get(1),Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT / 2 - 3 * 64, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                    break;
                case 3:
                    batch.draw(playerChars.get(2),Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT / 2 - 3 * 64, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                    break;
                case 4:
                    batch.draw(playerChars.get(3),Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT / 2 - 3 * 64, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                    break;
            }
        }
        gameOverFont.draw(batch,"PRESS ENTER TO START A NEW GAME OR ESCAPE TO END IT!".toUpperCase(),offsetLeft, Constanze.GAME_HEIGHT / 4);
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.GameOver);
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Game);
        }
    }
}
