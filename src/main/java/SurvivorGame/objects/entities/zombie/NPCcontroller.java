package SurvivorGame.objects.entities.zombie;

import engine.controller.Controllable;

public class NPCcontroller implements Controllable {
    @Override
    public boolean requestingUp() {
        return false;
    }

    @Override
    public boolean requestingDown() {
        return false;
    }

    @Override
    public boolean requestingLeft() {
        return false;
    }

    @Override
    public boolean requestingRight() {
        return false;
    }
}
