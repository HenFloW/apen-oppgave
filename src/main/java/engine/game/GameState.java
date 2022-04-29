package engine.game;

import engine.controller.DebugController;
import engine.controller.Input;
import engine.core.math.Size;
import engine.display.Camera;
import engine.display.DisplayWindow;
import SurvivorGame.map.GameMap;
import engine.gfx.ResourceLibrary;
import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class GameState {
    public boolean debug = true;
    protected GameMap gameMap;

    protected ArrayList<GameObject> gameObjects;
    protected ArrayList<GameObject> objRemoveQueue;
    protected ArrayList<GameObject> objAddQueue;

    protected Input input;
    protected ResourceLibrary resourceLibrary;
    protected Camera camera;
    protected DisplayWindow display;

    public DebugController debugController;
    private double currentTime;

    public GameState(Input input, DisplayWindow display){
        this.input = input;
        this.debugController = new DebugController(input);
        this.gameObjects = new ArrayList<>();
        this.objAddQueue = new ArrayList<>();
        this.objRemoveQueue = new ArrayList<>();

        this.resourceLibrary = new ResourceLibrary();
        this.camera = new Camera(new Size(display.getWindowSize().getWidth(), display.getWindowSize().getHeight()));
        this.display = display;
    }

    public void update(){
        camera.update(this);
        debugController.update();
        currentTime = System.currentTimeMillis();
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Camera getCamera() {
        return camera;
    }

    public ResourceLibrary getResourceLibrary() {
        return resourceLibrary;
    }

    public void removeGameObject(GameObject gameObject) {
        objRemoveQueue.add(gameObject);
    }

    public void addGameObject(GameObject gameObject) {
        objAddQueue.add(gameObject);
    }

    public void cleanup() {
        gameObjects.forEach(gameObject -> {
            ArrayList<GameObject> local = gameObject.getChildRemoveQueue();
            if(!local.isEmpty()){
                gameObject.removeChildren(local);
            }
        });
        if(!objAddQueue.isEmpty()){
            this.gameObjects.addAll(objAddQueue);
            this.objAddQueue = new ArrayList<>();
        }
        if(!objRemoveQueue.isEmpty()){
            this.gameObjects.removeAll(objRemoveQueue);
            this.objRemoveQueue = new ArrayList<>();
        }
    }

    public double currentTime() {
        return currentTime;
    }
}
