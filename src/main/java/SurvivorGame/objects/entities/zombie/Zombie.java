package SurvivorGame.objects.entities.zombie;

import SurvivorGame.objects.entities.player.actions.UseAction;
import SurvivorGame.state.PlayState;
import engine.controller.Controllable;
import engine.core.math.Direction;
import engine.core.math.Position;
import engine.core.math.Vector2D;
import engine.game.GameState;
import engine.gfx.Animation;
import engine.gfx.ResourceLibrary;
import engine.objects.Action;
import engine.objects.GameObject;
import engine.objects.moving.MovingEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends MovingEntity {
    private boolean triggered = false;
    private final int detectionLength = 400;
    private final int runningLenght = 60;
    private final int attackRange = 30;

    public Zombie(Controllable controller, ResourceLibrary resourceLibrary) {
        super(controller, resourceLibrary);

        objectName = "zombie";

        spriteSet.addSheet(
                resourceLibrary
                        .getFilesFromTreeNode("sprites/entities/zombie"));

        spriteSet.get(objectName+"_walking").setAnimationSpeed(15);
        spriteSet.get(objectName+"_running").setAnimationSpeed(10);
        spriteSet.get(objectName+"_idle").setAnimationSpeed(15);
        spriteSet.get(objectName+"_action").setAnimationSpeed(10);

        this.animation = new Animation(spriteSet, this);
        this.animation.playAnimation(objectName+"_idle");

        this.objectPoint.setOffset(50, 75);

        this.collider.setOffsets(40,55);
        this.collider.setSize(20, 25);
    }

    @Override
    public void render(Graphics g) {
        drawSprite(g, animation.getSprite());
    }

    @Override
    public void action(GameState state, GameObject other) {

    }

    @Override
    public void update(GameState state) {
        super.update(state);

        if(action.isEmpty()){
            if(state instanceof PlayState playState){
                Position playerPos = playState.player.getObjectPoint();
                Vector2D vector2D = objectPoint.vectorBetween(playerPos).normalize();
                if(triggered){
                    this.direction = Direction.fromVector(objectPoint.vectorBetween(playerPos));
                    if(playerPos.length(objectPoint) >= detectionLength){
                        triggered = false;
                    }
                    if(playerPos.length(objectPoint) < attackRange){
                        preform(state, new UseAction());
                    } else {
                        if(state.getGameObjects().stream()
                                .filter(gameObject -> !gameObject.equals(this))
                                .anyMatch(gameObject -> gameObject.getCollider().intersects(collider))){
                            movement.stop();
                        } else {
                            if(playerPos.length(objectPoint) >= runningLenght){
                                movement.setSpeed(3);
                                animation.playAnimation(objectName+"_running");

                            } else {
                                movement.setSpeed(1);
                                animation.playAnimation(objectName+"_walking");
                            }
                            animation.update(direction);
                            vector2D = vector2D.multiplied(movement.getSpeed());

                            this.movement.setVX(vector2D.getX());
                            this.movement.setVY(vector2D.getY());

                            objectPoint.apply(movement);
                            position.apply(movement);
                            collider.update(this);
                        }
                    }
                } else {
                    if(playerPos.length(objectPoint) <= detectionLength){
                        triggered = true;
                    }
                    animation.playAnimation("zombie_idle");
                }
            }
        }
    }

    @Override
    public BufferedImage getSprite() {
        return animation.getSprite();
    }

    @Override
    protected void init(ResourceLibrary resourceLibrary) {

    }

    @Override
    public void preform(GameState state, Action action) {
        super.preform(state, action);
        if(state instanceof PlayState playState){
            playState.player.action(state, this);
        }
    }
}
