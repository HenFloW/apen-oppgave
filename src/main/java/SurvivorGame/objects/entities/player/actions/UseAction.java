package SurvivorGame.objects.entities.player.actions;

import engine.game.GameState;
import engine.objects.Action;
import engine.objects.moving.MovingEntity;

public class UseAction extends Action {
    private double lifespan = 0.4;

    @Override
    public void update(GameState state, MovingEntity entity) {
        lifespan -= 1d / 60d;
    }

    @Override
    public boolean isDone() {
        return lifespan <= 0;
    }

    @Override
    public String getAnimationName() {
        return "action";
    }
}
