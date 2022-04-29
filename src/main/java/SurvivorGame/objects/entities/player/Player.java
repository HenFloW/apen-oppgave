package SurvivorGame.objects.entities.player;

import SurvivorGame.objects.entities.player.actions.Dropping;
import SurvivorGame.objects.entities.player.actions.UseAction;
import SurvivorGame.objects.entities.zombie.Zombie;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.core.utils.Health;
import engine.game.GameState;
import engine.gfx.ResourceLibrary;
import engine.gfx.Animation;
import engine.objects.Item;
import engine.objects.moving.MovingEntity;
import engine.objects.GameObject;

import javax.swing.*;
import java.awt.*;
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

        spriteSet.get(objectName+"_walking").setAnimationSpeed(7);
        spriteSet.get(objectName+"_running").setAnimationSpeed(5);
        spriteSet.get(objectName+"_idle").setAnimationSpeed(13);
        spriteSet.get(objectName+"_action").setAnimationSpeed(5);

        this.animation = new Animation(spriteSet, this);
        animation.update();

        this.objectPoint.setOffset(50,75);

        this.collider.setOffsets(40,55);
        this.collider.setSize(20, 25);

        this.actionCollider = new Collider2D(this);
        this.actionCollider.setOffsets(20, 30);
        this.actionCollider.setSize(60,70);

        this.health = new Health(300);
        this.health.setOffsets(new Size(25, 5));
        this.health.setSize(new Size(50,5));
        this.health.setRegenSpeed(10);
        this.health.setRegenAmount(50);

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
        animation.update();
        drawSprite(g,animation.getSprite());
        health.render(g);
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
        if(other instanceof Zombie zombie && zombie.getAction().isPresent()){
            health.damage(10);
        }
    }

    public String getAnimationState(){
        return animationState;
    }
}
