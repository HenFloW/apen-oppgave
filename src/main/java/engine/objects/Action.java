package engine.objects;

import engine.game.GameState;
import engine.objects.moving.MovingEntity;

public abstract class Action {
    public abstract void update(GameState state, MovingEntity entity);
    public abstract boolean isDone();
    public abstract String getAnimationName();
}
