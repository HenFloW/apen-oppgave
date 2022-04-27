package engine.controller;

public interface Controllable {
    boolean requestingUp();
    boolean requestingDown();
    boolean requestingLeft();
    boolean requestingRight();
}
