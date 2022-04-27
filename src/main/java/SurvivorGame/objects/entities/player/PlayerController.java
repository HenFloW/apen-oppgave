package SurvivorGame.objects.entities.player;

import engine.controller.Controllable;
import engine.controller.Input;

import java.awt.event.KeyEvent;

public class PlayerController implements Controllable {

    private Input input;

    public PlayerController(Input input){
        this.input = input;
    }
    @Override
    public boolean requestingUp() {
        return input.isPressed(KeyEvent.VK_W);
    }

    @Override
    public boolean requestingDown() {
        return input.isPressed(KeyEvent.VK_S);
    }

    @Override
    public boolean requestingLeft() {
        return input.isPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean requestingRight() {
        return input.isPressed(KeyEvent.VK_D);
    }

    public boolean requestingShift() {
        return input.isPressed(KeyEvent.VK_SHIFT);
    }

    public boolean requestingK_F() {
        return input.isPressed(KeyEvent.VK_F);
    }

    public boolean requestingK_E() {
        return input.isPressed(KeyEvent.VK_E);
    }

    public boolean requestingK_ENTER() {
        return input.keyPress(KeyEvent.VK_ENTER);
    }

    public boolean requestingK_C() {
        return input.keyPress(KeyEvent.VK_C);
    }
}
