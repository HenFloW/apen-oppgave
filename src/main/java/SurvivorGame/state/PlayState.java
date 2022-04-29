package SurvivorGame.state;

import SurvivorGame.objects.entities.zombie.NPCcontroller;
import SurvivorGame.objects.entities.zombie.Zombie;
import engine.controller.Input;
import SurvivorGame.objects.entities.player.PlayerController;
import engine.core.math.Size;
import engine.display.DisplayWindow;
import SurvivorGame.map.GameMap;
import SurvivorGame.objects.entities.nature.Rock;
import SurvivorGame.objects.entities.nature.TreeEntity;
import SurvivorGame.objects.entities.player.Player;
import engine.game.GameState;
import engine.objects.GameObject;
import SurvivorGame.objects.items.Axe;

public class PlayState extends GameState {
    public Player player;

    public PlayState(Input input, DisplayWindow window){
        super(input,window);
        initGameObjects();
    }

    public void update(){
        for (GameObject gameObject : gameObjects) {
            gameObject.update(this);
        }
        super.update();
    }

    private void initGameObjects(){
        this.gameMap = new GameMap(new Size(40, 25), resourceLibrary);
        this.player = new Player(resourceLibrary, new PlayerController(input), this);
        player.setPosition(gameMap.getRandomPosition());

        this.camera.setFocus(player);

        gameObjects.add(player);
        for (int i = 0; i < 10; i++) {
            GameObject tree = new TreeEntity(resourceLibrary, gameMap.getRandomPosition());
            gameObjects.add(tree);
        }

        for (int i = 0; i < 15; i++) {
            Zombie zombie = new Zombie(new NPCcontroller(), resourceLibrary);
            zombie.setPosition(gameMap.getRandomPosition());
            gameObjects.add(zombie);
        }

        for (int i = 0; i < 10; i++) {
            GameObject rock = new Rock(resourceLibrary, gameMap.getRandomPosition());
            gameObjects.add(rock);
        }

        Axe axe = new Axe(this);
        axe.setPosition(gameMap.getRandomPosition());
        gameObjects.add(axe);
    }

}
