package SurvivorGame.state;

import engine.controller.Input;
import SurvivorGame.objects.entities.player.PlayerController;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.display.DisplayWindow;
import SurvivorGame.map.GameMap;
import SurvivorGame.objects.entities.Rock;
import SurvivorGame.objects.entities.TreeEntity;
import SurvivorGame.objects.entities.player.Player;
import engine.game.GameState;
import engine.objects.GameObject;
import SurvivorGame.objects.items.Axe;

public class PlayState extends GameState {
    public Player player;

    public PlayState(Input input, DisplayWindow window){
        super(input,window);

        this.gameMap = new GameMap(new Size(40, 25), resourceLibrary);
        this.player = new Player(resourceLibrary, new PlayerController(input), this);
        this.camera.setFocus(player);


        gameObjects.add(player);


        for (int i = 0; i < 10; i++) {
            Position pos = new Position(Math.random()*gameMap.getWidth(),Math.random()*gameMap.getHeight());
            GameObject tree = new TreeEntity(resourceLibrary, pos);
            while(!gameMap.isOnGrid(tree)){
                pos = new Position(Math.random()*gameMap.getWidth(),Math.random()*gameMap.getHeight());
                tree = new TreeEntity(resourceLibrary, pos);
            }
            gameObjects.add(new TreeEntity(resourceLibrary, pos));
        }
        for (int i = 0; i < 10; i++) {
            Position pos = new Position(Math.random()*gameMap.getWidth(),Math.random()*gameMap.getHeight());
            GameObject rock = new Rock(resourceLibrary, pos);
            while(!gameMap.isOnGrid(rock)){
                pos = new Position(Math.random()*gameMap.getWidth(),Math.random()*gameMap.getHeight());
                rock = new TreeEntity(resourceLibrary, pos);
            }
            gameObjects.add(new Rock(resourceLibrary, pos));
        }


        Axe axe = new Axe(this);
        gameObjects.add(axe);
    }

    public void update(){
        for (GameObject gameObject : gameObjects) {
            gameObject.update(this);
        }
        super.update();
    }

}
