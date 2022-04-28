package engine.controller;

public interface Controllable {
    /**
     * For all this is the fundemental movement a moveable object should have
     *
     * @return when the game requests this it will get a boolean telling
     * the game the object is requesting any direction
     */
    boolean requestingUp();
    boolean requestingDown();
    boolean requestingLeft();
    boolean requestingRight();
}
