import SurvivorGame.Survivor;
import engine.game.GameLoop;
import engine.game.IGame;

public class GameLauncher {
    public static void main(String[] args) {
        IGame survivor = new Survivor(1200, 700, "Survivor");
        new Thread(new GameLoop(survivor)).start();
    }
}