package SurvivorGame.objects.entities.nature;

import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.objects.entities.player.actions.UseAction;
import SurvivorGame.objects.items.Axe;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.game.GameState;
import engine.gfx.Animation;
import engine.gfx.ResourceLibrary;
import engine.gfx.SpriteSet;
import engine.objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TreeEntity extends GameObject {
    private BufferedImage sprite;
    private Animation animation;
    private int stage;

    public TreeEntity(ResourceLibrary resourceLibrary, Position position) {
        super();
        this.position = position;
        this.objectPoint.setOffset(150, 240);
        this.size = new Size(300, 300);

        SpriteSet sprites = new SpriteSet(resourceLibrary);
        sprites.addSheet(resourceLibrary.getFilesFromTreeNode("sprites/entities/static/treedeform"));
        this.animation = new Animation(sprites, this);
        this.animation.playAnimation("tree100");
        this.sprite = animation.getSprite();
        this.stage = 5;

        this.collider = new Collider2D(this);
        this.collider.setOffsets(140, 225);
        this.collider.setSize(20, 15);
        this.destructible = true;

        this.health.setOffsets(new Size(100,150));
    }

    @Override
    public void update(GameState state) {
        treeStageCheck(state);
        health.update(state);
    }

    @Override
    public void render(Graphics g) {
        drawSprite(g,animation.getSprite());
        health.render(g);
    }

    @Override
    public void action(GameState state, GameObject other) {
        if(other instanceof Player player){
            if(player.getAction().isPresent()){
                if(player.getAction().get() instanceof UseAction){
                    if(player.getChildren().stream().anyMatch(c -> c instanceof Axe)) {
                        health.damage(20);
                    } else {
                        health.damage(5);
                    }
                }
            }
        }
    }

    private void treeStageCheck(GameState state){
        int hp = health.hp();
        int maxHp = health.getMaxHealth();

        if(hp <=  maxHp * .8 && stage == 5){
            stage = 4;
            animation.playAnimation("tree80");
        }
        if(hp <=  maxHp * .6 && stage == 4){
            stage = 3;
            animation.playAnimation("tree60");
        }
        if(hp <=  maxHp * .4 && stage == 3){
            stage = 2;
            animation.playAnimation("tree40");
        }
        if(hp <=  maxHp * .2 && stage == 2){
            stage = 1;
            animation.playAnimation("tree20");
        }
        if(hp <=  0 && stage == 1){
            state.removeGameObject(this);
        }
    }
}
