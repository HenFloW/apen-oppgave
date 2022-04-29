package SurvivorGame.state;

import SurvivorGame.objects.entities.zombie.NPCcontroller;
import SurvivorGame.objects.entities.zombie.Zombie;
import engine.controller.Input;
import SurvivorGame.objects.entities.player.PlayerController;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.display.DisplayWindow;
import SurvivorGame.map.GameMap;
import SurvivorGame.objects.entities.nature.Rock;
import SurvivorGame.objects.entities.nature.TreeEntity;
import SurvivorGame.objects.entities.player.Player;
import engine.game.GameState;
import engine.game.IGame;
import engine.objects.GameObject;
import SurvivorGame.objects.items.Axe;

import java.awt.*;

public class PlayState extends GameState {
    public Player player;

    public PlayState(Input input, DisplayWindow window){
        super(input,window);
        initGameObjects();
    }

    public void update(IGame game){
        super.update(game);
        for (GameObject gameObject : gameObjects) {
            gameObject.update(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        int nrZombies = gameObjects.stream().filter(gameObject -> gameObject instanceof Zombie).toArray().length;
        graphics.setFont(graphics.getFont().deriveFont(35f));
        graphics.setColor(Color.white);
        graphics.drawString("There are "+nrZombies+" zombies left", 10, 40);
    }

    private void initGameObjects(){
        this.gameMap = new GameMap(new Size(40, 25), resourceLibrary);
        this.player = new Player(resourceLibrary, new PlayerController(input), this);
        player.setPosition(gameMap.getRandomPosition(player));

        this.camera.setFocus(player);

        gameObjects.add(player);
        for (int i = 0; i < 10; i++) {
            GameObject tree = new TreeEntity(resourceLibrary, new Position(0,0));
            tree.setPosition(gameMap.getRandomPosition(tree));
            gameObjects.add(tree);
        }

        for (int i = 0; i < 50; i++) {
            Zombie zombie = new Zombie(new NPCcontroller(), resourceLibrary);
            zombie.setPosition(gameMap.getRandomPosition(zombie));
            gameObjects.add(zombie);
        }

        for (int i = 0; i < 10; i++) {
            GameObject rock = new Rock(resourceLibrary, new Position(0,0));
            rock.setPosition(gameMap.getRandomPosition(rock));
            gameObjects.add(rock);
        }

        Axe axe = new Axe(this);
        axe.setPosition(gameMap.getRandomPosition(axe));
        gameObjects.add(axe);
    }

}
