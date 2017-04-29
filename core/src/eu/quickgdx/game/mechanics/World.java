package eu.quickgdx.game.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import eu.quickgdx.game.Constants;
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
        tileHeight = Constants.TILESIZE;
        tileWidth = Constants.TILESIZE;
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
        this.hud.setDebugText(Gdx.graphics.getFramesPerSecond()+"");
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
        PlayerCharacterObject playerObj1 = new PlayerCharacterObject(new Vector2(1f * Constants.SCALED_TILE, 1f * Constants.SCALED_TILE), this, controls1, 1);
        gameObjects.add(playerObj1);
        controlledObjects.add(playerObj1);

        Controls controls2 = new Controls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D);
        PlayerCharacterObject playerObj2 = new PlayerCharacterObject(new Vector2((mapWidth - 1) * Constants.SCALED_TILE, 1f * Constants.SCALED_TILE), this, controls2, 2);
        gameObjects.add(playerObj2);
        controlledObjects.add(playerObj2);

        Controls controls3 = new Controls(Input.Keys.T, Input.Keys.G, Input.Keys.F, Input.Keys.H);
        PlayerCharacterObject playerObj3 = new PlayerCharacterObject(new Vector2(1 * Constants.SCALED_TILE, (mapHeight - 1) * Constants.SCALED_TILE), this, controls3, 3);
        gameObjects.add(playerObj3);
        controlledObjects.add(playerObj3);

        Controls controls4 = new Controls(Input.Keys.I, Input.Keys.K, Input.Keys.J, Input.Keys.L);
        PlayerCharacterObject  playerObj4 = new PlayerCharacterObject(new Vector2((mapWidth - 1) * Constants.SCALED_TILE, (mapHeight - 1) * Constants.SCALED_TILE), this, controls4, 4);
        gameObjects.add(playerObj4);
        controlledObjects.add(playerObj4);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Constants.SCALE);

        // layer 4 - collision
        // layer 5 - controlled objects
        Array<PlayerCharacterObject>  players = getGameObjectByType(PlayerCharacterObject.class);


        this.cookieCount = mapHeight / 30 + players.size;
        goodCookieObject = new GoodCookieObject(new Vector2((int) Utils.calculateRandomX(mapWidth) * Constants.TILESIZE,
                (int) Utils.calculateRandomY(mapWidth) * Constants.TILESIZE), this);
        //goodCookieObject.setPosition(new Vector2(goodCookieObject.getTileX() * Constants.TILESIZE, goodCookieObject.getTileY() * Constants.TILESIZE));
        gameObjects.add(goodCookieObject);
        for (int i = 0; i < cookieCount; i++) {

            BadCookieObject badCookieObject = new BadCookieObject(new Vector2((int) Utils.calculateRandomX(mapWidth) * Constants.TILESIZE,
                    (int) Utils.calculateRandomX(mapHeight) * Constants.TILESIZE), this);
            badCookieObject.setPosition(new Vector2(badCookieObject.getTileX() * Constants.TILESIZE, badCookieObject.getTileY() * Constants.TILESIZE));
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
        Texture groundTexture = assMann.get(Constants.ASSET_MAP_GROUND);
        Texture wallTexture = assMann.get(Constants.ASSET_MAP_FLOOR_WALL);
        Texture ceiling_e_empty = assMann.get(Constants.ASSET_MAP_CEILING_E_EMPTY);
        Texture ceiling_ne_empty = assMann.get(Constants.ASSET_MAP_CEILING_NE_EMPTY);
        Texture ceiling_nse_empty = assMann.get(Constants.ASSET_MAP_CEILING_NSE_EMPTY);
        Texture ceiling_nswe = assMann.get(Constants.ASSET_MAP_CEILING_NSWE);
        Texture ceiling_nsw_empty = assMann.get(Constants.ASSET_MAP_CEILING_NSW_EMPTY);
        Texture ceiling_ns_empty = assMann.get(Constants.ASSET_MAP_CEILING_NS_EMPTY);
        Texture ceiling_nwe_empty = assMann.get(Constants.ASSET_MAP_CEILING_NWE_EMPTY);
        Texture ceiling_nw_empty = assMann.get(Constants.ASSET_MAP_CEILING_NW_EMPTY);
        Texture ceiling_n_empty = assMann.get(Constants.ASSET_MAP_CEILING_N_EMPTY);
        Texture ceiling_se_empty = assMann.get(Constants.ASSET_MAP_CEILING_SE_EMPTY);
        Texture ceiling_swe_empty = assMann.get(Constants.ASSET_MAP_CEILING_SWE_EMPTY);
        Texture ceiling_sw_empty = assMann.get(Constants.ASSET_MAP_CEILING_SW_EMPTY);
        Texture ceiling_s_empty = assMann.get(Constants.ASSET_MAP_CEILING_S_EMPTY);
        Texture ceiling_we_empty = assMann.get(Constants.ASSET_MAP_CEILING_WE_EMPTY);
        Texture ceiling_w_empty = assMann.get(Constants.ASSET_MAP_CEILING_W_EMPTY);
        Texture floor_wall_ending_left = assMann.get(Constants.ASSET_MAP_FLOOR_WALL_ENDING_LEFT);
        Texture floor_wall_ending_right = assMann.get(Constants.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT);
        Texture floor_wall_ending_right_left = assMann.get(Constants.ASSET_MAP_FLOOR_WALL_ENDING_RIGHT_LEFT);
        Texture ceiling_borderless_empty= assMann.get(Constants.ASSET_MAP_CEILING_BORDERLESS_EMPTY);



        Level level = LevelGenerator.generateLevel(mapHeight, controlledObjects, getGameObjectByType(AbstractCookieObject.class));
        // layer 0 - ground
        GroundLayer layerGround = new GroundLayer(mapWidth, mapHeight, Constants.TILESIZE, Constants.TILESIZE);
        WallLayer layerCollision = new WallLayer(mapWidth, mapHeight, Constants.TILESIZE, Constants.TILESIZE);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Tiletype type = level.typemap[x][y];
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                boolean collision = true;
                StaticTiledMapTile tile = null;
                switch (type) {
                    case FLOOR:
                        tile = new StaticTiledMapTile(new TextureRegion(groundTexture));
                        collision = false;
                        break;
                    case NONWALKABLE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_borderless_empty));
                        break;
                    case WALL:
                        tile = new StaticTiledMapTile(new TextureRegion(wallTexture));
                        break;
                    case WALL_END_LEFT:
                        tile = new StaticTiledMapTile(new TextureRegion(floor_wall_ending_left));
                        break;
                    case WALL_END_RIGHT:
                        tile = new StaticTiledMapTile(new TextureRegion(floor_wall_ending_right));
                        break;
                    case WALL_END_BOTH:
                        tile = new StaticTiledMapTile(new TextureRegion(floor_wall_ending_right_left));
                        break;
                    case CEILING_NSEW:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_nswe));
                        break;
                    case CEILING_N:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_n_empty));
                        break;
                    case CEILING_E:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_e_empty));
                        break;
                    case CEILING_W:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_w_empty));
                        break;
                    case CEILING_S:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_s_empty));
                        break;
                    case CEILING_NE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_ne_empty));
                        break;
                    case CEILING_NS:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_ns_empty));
                        break;
                    case CEILING_NSE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_nse_empty));
                        break;
                    case CEILING_NSW:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_nsw_empty));
                        break;
                    case CEILING_NW:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_nw_empty));
                        break;
                    case CEILING_NWE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_nwe_empty));
                        break;
                    case CEILING_SE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_se_empty));
                        break;
                    case CEILING_SW:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_sw_empty));
                        break;
                    case CEILING_SWE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_swe_empty));
                        break;
                    case CEILING_WE:
                        tile = new StaticTiledMapTile(new TextureRegion(ceiling_we_empty));
                        break;
                    default: //the fallback
                        tile = new StaticTiledMapTile(new TextureRegion(groundTexture));
                        collision = false;
                }
                cell.setTile(tile);
                layerGround.setCell(x, y, cell);
                if (collision) {
                    gameObjects.add(new WallObject(new Vector2(x * Constants.TILESIZE, y * Constants.TILESIZE), this, Constants.TILESIZE, Constants.TILESIZE));
                }
            }
            this.map.getLayers().add(layerGround);
            this.map.getLayers().add(layerCollision);
        }
    }

    public void addFogLayer() {
        FogLayer fogLayer = new FogLayer(mapWidth, mapHeight, Constants.TILESIZE, Constants.TILESIZE);
        Texture fogTexture = this.gameplayScreen.parentGame.getAssetManager().get(Constants.ASSET_FOG);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
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
