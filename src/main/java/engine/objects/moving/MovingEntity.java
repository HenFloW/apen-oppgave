package engine.objects.moving;

import engine.controller.Controllable;
import engine.core.math.Direction;
import engine.core.math.Vector2D;
import engine.core.utils.CollidingUTIL;
import engine.core.utils.Motion;
import engine.display.Camera;
import engine.game.GameState;
import engine.gfx.Animation;
import engine.gfx.ResourceLibrary;
import engine.gfx.SpriteSet;
import engine.objects.Action;
import engine.objects.GameObject;

import java.util.Optional;

public abstract class MovingEntity extends GameObject {
    protected Controllable controller;
    protected SpriteSet spriteSet;
    protected Optional<Action> action;
    protected Motion movement;
    protected Animation animation;
    protected Direction direction;

    protected String animationState;
    protected String objectName;

    public MovingEntity(Controllable controller, ResourceLibrary resourceLibrary) {
        this.controller = controller;
        this.action = Optional.empty();
        this.spriteSet = new SpriteSet(resourceLibrary);
        this.movement = new Motion(1);
    }

    protected abstract void init(ResourceLibrary resourceLibrary);

    @Override
    public void update(GameState state) {
        handleAction(state);
        handleMovement(state);
    }

    private void handleChildren(GameState state) {
        if(!children.isEmpty()){
            children.forEach(gameObject -> gameObject.update(state));
        }
    }

    private void handleMovement(GameState state) {
        if(action.isEmpty()){
            movement.update(controller);

            if(collideable){
                CollidingUTIL collisions = checkForCollisions(state, movement.getVector());
                if(collisions.x()){
                    movement.setVX(0);
                }
                if(collisions.y()){
                    movement.setVY(0);
                }
            }
        }

        if(movement.isMoving()) {
            objectPoint.apply(movement);
            position.apply(movement);
            collider.update(this);

            direction = Direction.fromVector(movement.getVector());
            animation.update(direction);
        } else {
            animation.update();
        }

    }

    private CollidingUTIL checkForCollisions(GameState state, Vector2D movement) {
        Camera cam = state.getCamera();
        CollidingUTIL colliding = new CollidingUTIL();
        for (Object obj : state.getGameObjects().stream().filter(cam::isInView).filter(gameObject -> gameObject.collideable).toArray()) {
            if(obj != this){
                if(obj instanceof GameObject gameObject){
                    if(collider.getOffsetCollider(movement.intX(), 0d).intersects(gameObject.getCollider())){
                        colliding.setX(true);
                    }
                    if(collider.getOffsetCollider(0d, movement.intY()).intersects(gameObject.getCollider())){
                        colliding.setY(true);
                    }
                }
            }
        }
        return colliding;
    }

    public void handleAction(GameState state){
        action.ifPresent(value -> {
            value.update(state, this);
            if (value.isDone()){
                action = Optional.empty();
            }
        });
    }

    public Optional<Action> getAction(){
        return action;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void preform(Action action){
        this.action = Optional.of(action);
        this.animation.playAnimation(objectName+"_"+this.action.get().getAnimationName());
        this.animation.restartAnimation();
    }
}