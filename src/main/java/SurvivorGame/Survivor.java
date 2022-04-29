package SurvivorGame;

import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.objects.entities.zombie.Zombie;
import SurvivorGame.state.GameOver;
import SurvivorGame.state.PlayState;
import SurvivorGame.state.Victory;
import engine.controller.Input;
import engine.core.math.Size;
import engine.display.DisplayWindow;
import engine.game.GameState;
import engine.game.IGame;

public class Survivor implements IGame {

    private final DisplayWindow display;
    private GameState currentState, gameOverState;
    private Input input;

    public Survivor(int width, int height, String name){
        this.input = new Input();
        this.display = new DisplayWindow(new Size(width, height), name, input);
        this.currentState = new PlayState(input, display);
        this.gameOverState = new GameOver(input, display);
    }
    @Override
    public void update() {
        currentState.update(this);

        if(currentState instanceof PlayState && currentState.getGameObjects().stream().anyMatch(gameObject -> gameObject instanceof Player player && player.getHealth().hp() <= 0)) {
            currentState = new GameOver(input, display);
        }

        if(currentState instanceof PlayState && currentState.getGameObjects().stream().noneMatch(gameObject -> gameObject instanceof Zombie)) {
            currentState = new Victory(input, display);
        }
    }

    @Override
    public void render() {
        display.render(currentState);
    }

    @Override
    public void cleanup() {
        currentState.cleanup();
    }

    @Override
    public GameState getGameState() {
        return currentState;
    }

    @Override
    public void changeGameState(GameState state) {
        this.currentState = state;
    }
}
