package eu.quickgdx.game.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.Iterator;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.Utils;
import eu.quickgdx.game.mechanics.entities.AbstractCookieObject;
import eu.quickgdx.game.mechanics.entities.BadCookieObject;
import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.entities.Controls;
import eu.quickgdx.game.mechanics.entities.GameObject;
import eu.quickgdx.game.mechanics.entities.GoodCookieObject;
import eu.quickgdx.game.mechanics.entities.MoveableObject;
import eu.quickgdx.game.mechanics.entities.PlayerCharacterObject;
import eu.quickgdx.game.mechanics.entities.WallObject;
import eu.quickgdx.game.mechanics.hud.HUD;
import eu.quickgdx.game.mechanics.layers.FogLayer;
import eu.quickgdx.game.mechanics.layers.GroundLayer;
import eu.quickgdx.game.mechanics.layers.WallLayer;
import eu.quickgdx.game.mechanics.level.Level;
import eu.quickgdx.game.mechanics.level.LevelGenerator;
import eu.quickgdx.game.mechanics.level.Tiletype;
import eu.quickgdx.game.mechanics.states.global.GlobalState;
import eu.quickgdx.game.mechanics.states.global.GlobalWaitForFogState;
import eu.quickgdx.game.screens.GameplayScreen;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {

    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public HUD hud;
    ShapeRenderer sr = new ShapeRenderer();
    public Array<ControlledObject> controlledObjects;
    public GoodCookieObject goodCookieObject;
    private Array<GlobalState> globalStates;

    //Tiled Map Variables
    String level = "level/sampleMap.tmx"; //This is your example Tiled Map.
    TiledMap map;
    TiledMapRenderer tiledMapRenderer;
    int mapWidth;
    int tileWidth;
    int mapHeight;
    int tileHeight;
    private int cookieCount;

    public World(GameplayScreen gameplayScreen) {
        mapWidth = 32;
        mapHeight = 32;
        tileHeight = Constanze.TILESIZE;
        tileWidth = Constanze.TILESIZE;
        gameplayScreen.parentGame.setLastWinner(null);
        gameObjects = new Array<GameObject>();
        this.globalStates = new Array<GlobalState>();
        this.gameplayScreen = gameplayScreen;
        this.controlledObjects = new Array<ControlledObject>();
        //loadTiledMap();
        loadMap();
        //Add HUD
        this.hud = new HUD(this);
        this.hud.setDebugText("debugText");
        this.addGlobalState(new GlobalWaitForFogState(this, 5));
    }

    public void update(float delta) {
        this.hud.setDebugText(Gdx.graphics.getFramesPerSecond() + "");
        for (GlobalState globalState : globalStates) {
            globalState.update(delta);
        }
        Iterator<GameObject> gameObjectIterator = gameObjects.iterator();
        while (gameObjectIterator.hasNext()) {
            GameObject go = gameObjectIterator.next();
            if (go instanceof MoveableObject) {
                if (((MoveableObject) go).toRemove) {
                    gameObjectIterator.remove();
                    continue;
                }
            }
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
        hudBatch.begin();
        this.hud.render(delta, hudBatch);
        hudBatch.end();
    }

    public void touch(Vector3 touchCoords) {

    }

    /**
     * load map
     */
    public void loadMap() {
        map = new TiledMap();
        Controls controls1 = new Controls(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
        PlayerCharacterObject playerObj1 = new PlayerCharacterObject(new Vector2(1f * Constanze.SCALED_TILE, 1f * Constanze.SCALED_TILE), this, controls1, 1);
        gameObjects.add(playerObj1);
        controlledObjects.add(playerObj1);

        Controls controls2 = new Controls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D);
        PlayerCharacterObject playerObj2 = new PlayerCharacterObject(new Vector2((mapWidth - 1) * Constanze.SCALED_TILE, 1f * Constanze.SCALED_TILE), this, controls2, 2);
        gameObjects.add(playerObj2);
        controlledObjects.add(playerObj2);

        Controls controls3 = new Controls(Input.Keys.T, Input.Keys.G, Input.Keys.F, Input.Keys.H);
        PlayerCharacterObject playerObj3 = new PlayerCharacterObject(new Vector2(1 * Constanze.SCALED_TILE, (mapHeight - 1) * Constanze.SCALED_TILE), this, controls3, 3);
        gameObjects.add(playerObj3);
        controlledObjects.add(playerObj3);

        Controls controls4 = new Controls(Input.Keys.I, Input.Keys.K, Input.Keys.J, Input.Keys.L);
        PlayerCharacterObject playerObj4 = new PlayerCharacterObject(new Vector2((mapWidth - 1) * Constanze.SCALED_TILE, (mapHeight - 1) * Constanze.SCALED_TILE), this, controls4, 4);
        gameObjects.add(playerObj4);
        controlledObjects.add(playerObj4);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Constanze.SCALE);

        // layer 4 - collision
        // layer 5 - controlled objects
        Array<PlayerCharacterObject> players = getGameObjectByType(PlayerCharacterObject.class);


        this.cookieCount = mapHeight / 30 + players.size;
        goodCookieObject = new GoodCookieObject(new Vector2((int) Utils.calculateRandomX(mapWidth) * Constanze.TILESIZE,
                (int) Utils.calculateRandomY(mapWidth) * Constanze.TILESIZE), this);
        //goodCookieObject.setPosition(new Vector2(goodCookieObject.getTileX() * Constanze.TILESIZE, goodCookieObject.getTileY() * Constanze.TILESIZE));
        gameObjects.add(goodCookieObject);
        for (int i = 0; i < cookieCount; i++) {

            BadCookieObject badCookieObject = new BadCookieObject(new Vector2((int) Utils.calculateRandomX(mapWidth) * Constanze.TILESIZE,
                    (int) Utils.calculateRandomX(mapHeight) * Constanze.TILESIZE), this);
            badCookieObject.setPosition(new Vector2(badCookieObject.getTileX() * Constanze.TILESIZE, badCookieObject.getTileY() * Constanze.TILESIZE));
            gameObjects.add(badCookieObject);
        }


        createLevel();
    }


    public void createLevel() {

        Iterator<MapLayer> mapLayerIterator = this.map.getLayers().iterator();
        while (mapLayerIterator.hasNext()) {
            MapLayer mapLayer = mapLayerIterator.next();
            if (mapLayer instanceof GroundLayer || mapLayer instanceof WallLayer) {
                this.map.getLayers().remove(mapLayer);
            }
        }
        AssetManager assMann = this.gameplayScreen.parentGame.getAssetManager();
        Level level = LevelGenerator.generateLevel(mapHeight, controlledObjects, getGameObjectByType(AbstractCookieObject.class));
        // layer 0 - ground
        GroundLayer layerGround = new GroundLayer(mapWidth, mapHeight, Constanze.TILESIZE, Constanze.TILESIZE);
        WallLayer layerCollision = new WallLayer(mapWidth, mapHeight, Constanze.TILESIZE, Constanze.TILESIZE);
        Texture texture;
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Tiletype type = level.typemap[x][y];
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                texture = assMann.get(type.getAssetPath());
                StaticTiledMapTile tile = new StaticTiledMapTile(new TextureRegion(texture));
                cell.setTile(tile);
                layerGround.setCell(x, y, cell);
                if (type.isCollision()) {
                    gameObjects.add(new WallObject(new Vector2(x * Constanze.TILESIZE, y * Constanze.TILESIZE), this, Constanze.TILESIZE, Constanze.TILESIZE));
                }
            }
        }
        for (int i = -1; i <= mapWidth ; i++) {
            for (int j = -1; j <= mapHeight ; j++) {
                if(i==-1||i==mapWidth|| j==-1 || j == mapHeight){
                    gameObjects.add(new WallObject(new Vector2(i * Constanze.TILESIZE, j * Constanze.TILESIZE), this, Constanze.TILESIZE, Constanze.TILESIZE));
                }
            }
        }

        this.map.getLayers().add(layerGround);
        this.map.getLayers().add(layerCollision);
    }

    public void addFogLayer() {
        FogLayer fogLayer = new FogLayer(mapWidth, mapHeight, Constanze.TILESIZE, Constanze.TILESIZE);
        Texture fogTexture;
        String path;
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (y == 0) {
                    if (x == 0) {
                        path = Constanze.ASSET_CLOUD_SW.path;
                    } else if (x == mapWidth - 1) {
                        path = Constanze.ASSET_CLOUD_SE.path;
                    } else {
                        path = Constanze.ASSET_CLOUD_S.path;
                    }
                } else if (y == mapHeight - 1) {
                    if (x == 0) {
                        path = Constanze.ASSET_CLOUD_NW.path;
                    } else if (x == mapWidth - 1) {
                        path = Constanze.ASSET_CLOUD_NE.path;
                    } else {
                        path = Constanze.ASSET_CLOUD_N.path;
                    }
                } else if (x == 0) {
                    path = Constanze.ASSET_CLOUD_W.path;
                } else if (x == mapWidth - 1) {
                    path = Constanze.ASSET_CLOUD_E.path;
                } else {
                    path = Constanze.ASSET_CLOUD_CENTER.path;
                }
                fogTexture = this.gameplayScreen.parentGame.getAssetManager().get(path);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new TextureRegion(fogTexture)));
                fogLayer.setCell(x, y, cell);
            }
        }
        Array<WallObject> wallObjects = getGameObjectByType(WallObject.class);
        gameObjects.removeAll(wallObjects, false);
        this.map.getLayers().add(fogLayer);
    }

    public void removeFogLayer() {
        Array<FogLayer> mapLayer = this.map.getLayers().getByType(FogLayer.class);
        this.createLevel();
        for (FogLayer fogLayer : mapLayer) {
            this.map.getLayers().remove(fogLayer);
        }
    }

    public void addGlobalState(GlobalState state) {
        this.globalStates.add(state);
    }

    public void removeGlobalState(GlobalState state) {
        this.globalStates.removeValue(state, false);
    }


    public <T extends GameObject> Array<T> getGameObjectByType(Class<T> type) {
        Array<T> returnTypes = new Array<T>();
        for (GameObject gameObject : gameObjects) {
            if (ClassReflection.isInstance(type, gameObject)) {
                returnTypes.add((T) gameObject);
            }
        }
        return returnTypes;
    }

}
