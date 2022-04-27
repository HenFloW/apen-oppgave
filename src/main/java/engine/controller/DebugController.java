package engine.controller;

import java.awt.event.KeyEvent;

public class DebugController {
    public boolean
    renderLines,
    renderColliders,
    renderObjectPoint;

    public Input input;

    public DebugController(Input input){
        this.input = input;
    }

    public void update(){
        if(input.keyPress(KeyEvent.VK_1)){
            this.renderColliders = !renderColliders;
        }
        if(input.keyPress(KeyEvent.VK_2)){
            this.renderObjectPoint = !renderObjectPoint;
        }
        if(input.keyPress(KeyEvent.VK_3)){
            this.renderLines = !renderLines;
        }
    }
}
