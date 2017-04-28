package eu.quickgdx.game.mechanics;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import org.omg.CORBA.MARSHAL;

import eu.quickgdx.game.Constants;
import eu.quickgdx.game.mechanics.entities.BadCookieObject;
import eu.quickgdx.game.mechanics.entities.CollisionObject;
import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.entities.Controls;
import eu.quickgdx.game.mechanics.entities.GameObject;
import eu.quickgdx.game.mechanics.entities.GoodCookieObject;
import eu.quickgdx.game.mechanics.entities.MoveableObject;
import eu.quickgdx.game.mechanics.entities.PlayerCharacterObject;
import eu.quickgdx.game.mechanics.hud.HUD;
import eu.quickgdx.game.screens.GameplayScreen;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {
    public static final float SCALE = 1.0f;
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public HUD hud;
    ShapeRenderer sr = new ShapeRenderer();
    Array<ControlledObject> controlledObjects;
    public GoodCookieObject goodCookieObject;
    public BadCookieObject badCookieObject;

    //Tiled Map Variables
    String level = "level/sampleMap.tmx"; //This is your example Tiled Map.
    TiledMap map;
    TiledMapRenderer tiledMapRenderer;
    int mapWidth;
    int tileWidth;
    int mapHeight;
    int tileHeight;

    public World(GameplayScreen gameplayScreen) {
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;
        this.controlledObjects = new Array<ControlledObject>();
        loadTiledMap();
        //Add HUD
        //this.hud = new HUD(controlledObject, this);


    }

    public void update(float delta) {
        for (GameObject go : gameObjects) {
            go.update(delta);
        }
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        tiledMapRenderer.setView(gameplayScreen.gameCam);
        tiledMapRenderer.render();
        spriteBatch.begin();
        for (GameObject go : gameObjects) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();

        //Debug Renderer
        sr.setProjectionMatrix(gameplayScreen.gameCam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0, 1, 0, 1);
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getBounds() != null)
                sr.rect(gameObject.getBounds().x, gameObject.getBounds().y, gameObject.getBounds().width, gameObject.getBounds().height);
        }
        sr.end();
    }

    public void renderHUD(float delta, SpriteBatch hudBatch) {
        //hudBatch.begin();
        //this.hud.render(delta, hudBatch);
        //hudBatch.end();
    }

    public void touch(Vector3 touchCoords) {
        //controlledObject.touch(touchCoords);
    }

    /**
     * create the map out of the tmx files
     */
    public void loadTiledMap() {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Linear;
        map = new TmxMapLoader().load(level);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, SCALE); //Just scaled the map - This is not how you should do it obviously!
        mapWidth = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        //load collision map
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Object property = cell.getTile().getProperties().get("collision");
                    if (property != null) {
                        gameObjects.add(new CollisionObject(new Vector2(x * SCALE * Constants.TILESIZE, y * SCALE * Constants.TILESIZE), this, SCALE * Constants.TILESIZE, SCALE * Constants.TILESIZE));
                    }
                }
            }
        }

        // load controlled objects from map
        MapObjects objects = map.getLayers().get("objects").getObjects();

        // create controlled objects
        for (int i = 0; i < objects.getCount(); i++) {
            MapProperties object = objects.get(i).getProperties();
            String type = object.get("type", String.class);
            if (type.equals("controllableObject")) {
                Controls controls1 = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
                PlayerCharacterObject playerObj1 = new PlayerCharacterObject(new Vector2(Math.round(object.get("x", Float.class) * SCALE), Math.round(object.get("y", Float.class) * SCALE)), this, controls1, 1);
                gameObjects.add(playerObj1);
                controlledObjects.add(playerObj1);

                Controls controls2 = new Controls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D);
                PlayerCharacterObject playerObj2 = new PlayerCharacterObject(new Vector2(Math.round(object.get("x", Float.class) * SCALE), Math.round(object.get("y", Float.class) * SCALE)), this, controls2, 2);
                gameObjects.add(playerObj2);
                controlledObjects.add(playerObj2);

                Controls controls3 = new Controls(Input.Keys.T, Input.Keys.G, Input.Keys.F, Input.Keys.H);
                PlayerCharacterObject playerObj3 = new PlayerCharacterObject(new Vector2(Math.round(object.get("x", Float.class) * SCALE), Math.round(object.get("y", Float.class) * SCALE)), this, controls3, 3);
                gameObjects.add(playerObj3);
                controlledObjects.add(playerObj3);

                Controls controls4 = new Controls(Input.Keys.I, Input.Keys.K, Input.Keys.J, Input.Keys.L);
                PlayerCharacterObject playerObj4 = new PlayerCharacterObject(new Vector2(Math.round(object.get("x", Float.class) * SCALE), Math.round(object.get("y", Float.class) * SCALE)), this, controls4, 4);
                gameObjects.add(playerObj4);
                controlledObjects.add(playerObj4);

                //TODO: delete later on
                goodCookieObject = new GoodCookieObject(new Vector2(160,160),this);
                badCookieObject = new BadCookieObject(new Vector2(200,200),this);

                gameObjects.add(goodCookieObject);
                gameObjects.add(badCookieObject);
            }
        }
    }
}
