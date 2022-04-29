package SurvivorGame.objects.entities.player;

import SurvivorGame.objects.entities.player.actions.Dropping;
import SurvivorGame.objects.entities.player.actions.UseAction;
import engine.core.math.Position;
import engine.core.utils.Collider2D;
import engine.game.GameState;
import engine.gfx.ResourceLibrary;
import engine.gfx.Animation;
import engine.objects.Item;
import engine.objects.moving.MovingEntity;
import engine.objects.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class Player extends MovingEntity {
    private GameState state;
    private Collider2D actionCollider;

    public Player(ResourceLibrary resourceLibrary, PlayerController controller, GameState state){
        super(controller, resourceLibrary);
        this.state = state;
        this.action = Optional.empty();
        init(resourceLibrary);
    }

    @Override
    protected void init(ResourceLibrary resourceLibrary) {

        objectName = "man";

        spriteSet.addSheet(
                resourceLibrary
                        .getFilesFromTreeNode("sprites/entities/main_character"));

        spriteSet.get(objectName+"_walking").setAnimationSpeed(6);
        spriteSet.get(objectName+"_running").setAnimationSpeed(5);
        spriteSet.get(objectName+"_idle").setAnimationSpeed(10);
        spriteSet.get(objectName+"_action").setAnimationSpeed(4);

        this.animation = new Animation(spriteSet, this);
        this.animation.playAnimation(objectName+"_idle");
        this.animation.update(direction);

        this.position = new Position(400, 400);
        this.objectPoint.setOffset(50,75);

        this.collider.setOffsets(40,55);
        this.collider.setSize(20, 25);

        this.actionCollider = new Collider2D(this);
        this.actionCollider.setOffsets(20, 30);
        this.actionCollider.setSize(60,70);

    }

    @Override
    public void update(GameState state) {
        super.update(state);
        actionCollider.update(this);
        var playerController = (PlayerController) controller;

        if(action.isEmpty()){
            if (movement.isMoving()){
                if (playerController.requestingShift()){
                    animation.playAnimation(objectName+"_running");
                    animationState = "running";
                    movement.setSpeed(5f);
                } else {
                    animation.playAnimation(objectName+"_walking");
                    animationState = "walking";
                    movement.setSpeed(2f);
                }
            } else {
                animation.playAnimation(objectName+"_idle");
                animationState = "idle";
            }

            if(playerController.requestingK_F()){
                preform(state, new Dropping());
            }

            if(playerController.requestingK_E()){
                preform(state, new UseAction());
                state.getGameObjects().stream()
                        .filter(gameObject -> !gameObject.equals(this))
                        .filter(gameObject -> !children.contains(gameObject))
                        .forEach(gameObject -> {
                    if(gameObject.getCollider().intersects(actionCollider)) {
                        gameObject.action(state, this);
                    }
                } );
            }
        } else {
            movement.stop();
        }

        if(state.debug){
            if(playerController.requestingK_ENTER()){
                JFrame f = new JFrame();
                String s = JOptionPane.showInputDialog(f, "Enter speed: ");
                this.movement.effectSpeed(Integer.parseInt(s!=null && !s.equals("") ? s : "0"));
            }
            if(playerController.requestingK_C()) {
                this.collideable = !this.collideable;
            }
        }

        children.forEach(gameObject -> gameObject.update(state));
    }

    @Override
    public void render(Graphics g) {
        drawSprite(g,animation.getSprite());
        for (GameObject child: this.children) {
            child.render(g);
        }
        if (state.getGameObjects().stream()
                .filter(gameObject -> gameObject instanceof Item)
                .anyMatch(
                        gameObject
                                ->
                                gameObject.getObjectPoint().length(objectPoint) <= 40)){
            g.setColor(Color.white);
            g.drawString("Press 'E' to", 20, 10);
            g.drawString("pick up", 30, 20);
        }
    }

    @Override
    public void action(GameState state, GameObject other) {

    }

    @Override
    public BufferedImage getSprite() {
        return animation.getSprite();
    }

    public Animation getAnimation() {
        return animation;
    }

    public String getAnimationState(){
        return animationState;
    }
}
