package engine.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private boolean[] currentlyPressed;
    private boolean[] pressed;

    public Input(){
        currentlyPressed = new boolean[255];
        pressed = new boolean[255];
    }

    public boolean isPressed(int keyCode){
        return currentlyPressed[keyCode];
    }

    public boolean keyPress(int keyCode){
        if(!pressed[keyCode] && currentlyPressed[keyCode]){
            this.pressed[keyCode] = true;
            return true;
        }
        return false;
    }

    public void releaseKey(int keyCode) {
        this.currentlyPressed[keyCode] = false;
        this.pressed[keyCode] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        this.currentlyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.currentlyPressed[e.getKeyCode()] = false;
        this.pressed[e.getKeyCode()] = false;
    }

}
