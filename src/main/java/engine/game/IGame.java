package engine.game;

public interface IGame {
    /**
     * Thia is the update function, this should call the updates the game do
     * this will also be called in the GameLoop
     */
    void update();

    /**
     * This is the games rendering function, this will be called from the GameLoop
     */
    void render();

    /**
     * This is to cleanup any functions in the game, this will be called
     * from the GameLoop
     */
    void cleanup();

    GameState getGameState();

    void changeGameState(GameState state);
}
