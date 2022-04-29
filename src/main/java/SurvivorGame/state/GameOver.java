package SurvivorGame.state;

import engine.controller.Input;
import engine.display.DisplayWindow;
import engine.game.GameState;
import engine.game.IGame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOver extends GameState {
    boolean loadedNewGame = false;
    boolean renderedFrame = false;
    GameState newPlayState;

    public GameOver(Input input, DisplayWindow display) {
        super(input, display);
    }

    @Override
    public void update(IGame game) {
        if(renderedFrame && !loadedNewGame){
            newPlayState = new PlayState(input, display);
            loadedNewGame = true;
        }
        if(loadedNewGame){
            if(input.keyPress(KeyEvent.VK_R)){
                game.changeGameState(newPlayState);
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0,0, display.getWindowSize().getWidth(), display.getWindowSize().getHeight());
        graphics.setFont(graphics.getFont().deriveFont(70f));
        graphics.setColor(Color.red);
        graphics.drawString(
                "You are dead!",
                display.getWindowSize().getWidth()/2-(int)6.5*35,
                display.getWindowSize().getHeight()/2-(int)35/2);
        if(loadedNewGame){
            graphics.setFont(graphics.getFont().deriveFont(30f));
            graphics.setColor(Color.white);
            graphics.drawString(
                    "Press 'R' to restart",
                    display.getWindowSize().getWidth()/2-125,
                    display.getWindowSize().getHeight()/2+20);
        }
        renderedFrame = true;
    }
}
