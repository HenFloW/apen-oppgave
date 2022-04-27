package SurvivorGame;

import engine.controller.Input;
import engine.core.math.Size;
import engine.display.DisplayWindow;
import engine.game.IGame;
import engine.game.GameState;
import SurvivorGame.state.PlayState;

public class Survivor implements IGame {

    private final DisplayWindow display;
    private GameState currentState;
    private Input input;

    public Survivor(int width, int height, String name){
        this.input = new Input();
        this.display = new DisplayWindow(new Size(width, height), name, input);
        this.currentState = new PlayState(input, display);
    }
    @Override
    public void update() {
        currentState.update();
    }

    @Override
    public void render() {
        display.render(currentState);
    }

    @Override
    public void cleanup() {
        currentState.cleanup();
    }

}
