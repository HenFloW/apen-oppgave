package engine.objects;

import engine.game.GameState;
import engine.objects.moving.MovingEntity;

public abstract class Action {
    /**
     *
     * @param state
     * @param entity
     */
    public abstract void update(GameState state, MovingEntity entity);

    /**
     *
     * @return
     */
    public abstract boolean isDone();

    /**
     *
     * @return
     */
    public abstract String getAnimationName();
}
