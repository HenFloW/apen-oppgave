package SurvivorGame.objects.entities;

import engine.gfx.Animation;

public enum MoveState {
    RUNNING("running"),
    WALKING("walking"),
    IDLE("idle");

    private Animation animation;

    MoveState(String s){

    }

    public void animation(MoveState m){
        animation.playAnimation(m.name());
    }

    public int getSpeed(){
        return switch (this) {
            case IDLE -> 0;
            case RUNNING -> 4;
            case WALKING -> 2;
        };
    }

    public void setAnimation(Animation animation){
        this.animation = animation;
    }
}

