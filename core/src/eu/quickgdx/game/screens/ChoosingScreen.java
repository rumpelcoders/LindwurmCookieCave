package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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
    Texture cookieBagImage;
    Array<Texture> playerChars;
    private BitmapFont choosingFont;
    private final CamObject cam;
    private final SpriteBatch batch;
    ScreenViewport screenViewport;
    String[] playerStrings = {"2", "3", "4"};
    int currentMenuItem = 0;
    float offsetLeft = Constanze.GAME_WIDTH / 16, offsetTop = Constanze.GAME_HEIGHT / 16, offsetY = Constanze.GAME_HEIGHT / 16;
    int nrOfPlayers = 2;
    String[] introduction =
            ("The goal of this game\n" +
                    " is to find the\n" +
                    "good cookie and pick\n" +
                    "it up with your\n" +
                    "character.\n" +
                    "There is one good and\n" +
                    "many bad cookies!").split("\\n");

    public ChoosingScreen(QuickGdx parentGame) {
        this.parentGame = parentGame;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        cookieBagImage = parentGame.getAssetManager().get(Constanze.ASSET_COOKIE_BAG.path);
        choosingFont = parentGame.getAssetManager().get("fonts/retro.fnt");
        choosingFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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
        batch.draw(backgroundImage, 0, 0, Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        choosingFont.draw(batch, "CHOOSE NUMBER OF PLAYERS: ", offsetLeft, Constanze.GAME_HEIGHT - offsetTop);
        choosingFont.draw(batch, "PRESS ENTER TO START THE GAME", offsetLeft, Constanze.GAME_HEIGHT / 4);
        for (int i = 0; i < playerStrings.length; i++) {
            if (i == currentMenuItem) choosingFont.setColor(0.2f, 1f, 0.2f, 1f);
            else choosingFont.setColor(0.2f, 0.2f, 1f, 1f);
            choosingFont.draw(batch, playerStrings[i], offsetLeft, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2 - i * offsetY);
        }
        switch (nrOfPlayers){
            case 2:
                batch.draw(playerChars.get(0),offsetLeft * 2 + Constanze.TILESIZE, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                batch.draw(playerChars.get(1),offsetLeft * 2 + Constanze.TILESIZE*2, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                break;
            case 3:
                batch.draw(playerChars.get(0),offsetLeft * 2 + Constanze.TILESIZE, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                batch.draw(playerChars.get(1),offsetLeft * 2 + Constanze.TILESIZE*2, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                batch.draw(playerChars.get(2),offsetLeft * 2 + Constanze.TILESIZE*3, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                break;
            case 4:
                batch.draw(playerChars.get(0),offsetLeft * 2 + Constanze.TILESIZE, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                batch.draw(playerChars.get(1),offsetLeft * 2 + Constanze.TILESIZE*2, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                batch.draw(playerChars.get(2),offsetLeft * 2 + Constanze.TILESIZE*3, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                batch.draw(playerChars.get(3),offsetLeft * 2 + Constanze.TILESIZE*4, Constanze.GAME_HEIGHT - offsetTop - Constanze.TILESIZE*2, playerChars.get(0).getWidth(),playerChars.get(0).getHeight());
                break;
        }
        for (int i = 0; i < introduction.length; i++) {
            choosingFont.draw(batch, introduction[i].toUpperCase(), offsetLeft + Constanze.GAME_WIDTH / 2, Constanze.GAME_HEIGHT - offsetTop - i * 50);
        }
        batch.draw(cookieBagImage,offsetLeft + Constanze.GAME_WIDTH / 2,Constanze.GAME_HEIGHT/2 - cookieBagImage.getHeight() * 4,cookieBagImage.getWidth()*4, cookieBagImage.getHeight()*4);
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
                        nrOfPlayers = i + 2;
                        System.out.println(nrOfPlayers);
                    }
                }
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        } if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentMenuItem = (currentMenuItem + 1) % playerStrings.length;
            nrOfPlayers = currentMenuItem+2;
            parentGame.getSoundManager().playEvent("blip");
            System.out.println(nrOfPlayers);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            currentMenuItem = (currentMenuItem - 1) % playerStrings.length;
            parentGame.getSoundManager().playEvent("blip");
            nrOfPlayers = currentMenuItem+2;
            System.out.println(nrOfPlayers);
        }
    }
}
