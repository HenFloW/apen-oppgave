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
import engine.objects.Item;

import java.util.List;
import java.util.Optional;

public abstract class MovingEntity extends GameObject {
    protected Controllable controller;
    protected SpriteSet spriteSet;
    protected Optional<Action> action;
    protected Motion movement;
    protected Animation animation;
    protected Direction direction;

    protected String animationState;

    public MovingEntity(Controllable controller, ResourceLibrary resourceLibrary) {
        this.controller = controller;
        this.action = Optional.empty();
        this.spriteSet = new SpriteSet(resourceLibrary);
        this.movement = new Motion(1);
    }

    protected abstract void init(ResourceLibrary resourceLibrary);

    @Override
    public void update(GameState state) {
        super.update(state);
        handleAction(state);
        handleMovement(state);
    }

    private void handleMovement(GameState state) {
        if(action.isEmpty()){
            handleCollidingEntities(state.getGameObjects());

            if(controller != null){
                movement.update(controller, this);
            }

            if(collideable){
                CollidingUTIL collisions = checkForCollisions(state, movement.getVector());
                if(collisions.x()){
                    movement.setVX(0);
                }
                if(collisions.y()){
                    movement.setVY(0);
                }
            }
            if(movement.isMoving()) {
                objectPoint.apply(movement);
                position.apply(movement);
                collider.update(this);

                direction = Direction.fromVector(movement.getVector());
                animation.setDirectionIndex(direction.getAnimationRow());
            }
        }
    }

    private void handleCollidingEntities(List<GameObject> gameObjects) {
        for(Object obj : gameObjects.stream()
                .filter(gameObject -> !gameObject.equals(this))
                .filter(gameObject -> gameObject.getCollider().intersects(collider)).toArray()) {
            if (obj instanceof GameObject other && !(obj instanceof Item)) {
                this.position.applyVector(objectPoint.vectorBetween(other.getObjectPoint()).normalize().invert());
                this.collider.update(this);
                if(other instanceof MovingEntity){
                    other.getPosition().applyVector(other.getObjectPoint().vectorBetween(objectPoint).normalize().invert());
                    other.getCollider().update(other);
                }
            }
        }
    }

    private CollidingUTIL checkForCollisions(GameState state, Vector2D movement) {
        Camera cam = state.getCamera();
        CollidingUTIL colliding = new CollidingUTIL();
        for (Object obj :state.getGameObjects().stream()
                .filter(gameObject -> !gameObject.equals(this))
                .filter(cam::isInView)
                .filter(gameObject -> gameObject.collideable).toArray()) {
            if(obj instanceof GameObject gameObject){
                if (collider.getOffsetCollider(movement.intX(), 0d).intersects(gameObject.getCollider())) {
                    colliding.setX(true);
                }
                if (collider.getOffsetCollider(0d, movement.intY()).intersects(gameObject.getCollider())) {
                    colliding.setY(true);
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

    public void preform(GameState state, Action action){
        if(this.action.isEmpty()){
            this.action = Optional.of(action);
            this.animation.playAnimation(objectName+"_"+this.action.get().getAnimationName());
            this.animation.restartAnimation();
        }
    }
}
