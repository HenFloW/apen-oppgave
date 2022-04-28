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

    /**
     *
     * @param keyCode each keycode has an index
     * @return true or false depending on if its pressed
     */
    public boolean isPressed(int keyCode){
        return currentlyPressed[keyCode];
    }

    /**
     * this function checks if the key is pressed once
     * if its being held down this function will return false until key is released then pressed again
     * this is very useful for toggling keys or actions
     * @param keyCode each keycode has an index
     * @return true or false depending on if its pressed
     */
    public boolean keyPress(int keyCode){
        if(!pressed[keyCode] && currentlyPressed[keyCode]){
            this.pressed[keyCode] = true;
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {} //Not in use

    /**
     * If a key is pressed it will set the index of that key to true
     * @param e the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        this.currentlyPressed[e.getKeyCode()] = true;
    }

    /**
     * If a key is release it will set the index of that key to false.
     * To be able to check for only one press I have two boolean[] so
     * both need to be set to false when key is released
     * @param e the key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        this.currentlyPressed[e.getKeyCode()] = false;
        this.pressed[e.getKeyCode()] = false;
    }

}
