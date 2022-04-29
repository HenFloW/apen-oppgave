package SurvivorGame.objects.entities.nature;

import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.objects.entities.player.actions.UseAction;
import SurvivorGame.objects.items.Axe;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.core.utils.Health;
import engine.game.GameState;
import engine.gfx.Animation;
import engine.gfx.ResourceLibrary;
import engine.gfx.SpriteSet;
import engine.objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rock extends GameObject{
    private BufferedImage sprite;
    private int stage;
    private Animation animation;

    public Rock(ResourceLibrary resourceLibrary, Position position) {
        super();
        this.position = position;
        this.objectPoint.setOffset(100, 160);
        this.size = new Size(200, 200);

        SpriteSet sprites = new SpriteSet(resourceLibrary);
        sprites.addSheet(resourceLibrary.getFilesFromTreeNode("sprites/entities/static/rockdeform"));
        this.animation = new Animation(sprites, this);
        this.animation.playAnimation("rock100");
        this.sprite = animation.getSprite();
        this.stage = 5;

        this.collider = new Collider2D(this);
        this.collider.setOffsets(40, 135);
        this.collider.setSize(135, 30);
        this.destructible = true;

        this.health = new Health(250);
        this.health.setOffsets(new Size(50,75));
        this.health.setRegenSpeed(10);
    }

    @Override
    public void update(GameState state) {
        rockStageCheck(state);
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
                        health.damage(25);
                    } else {
                        health.damage(5);
                    }
                }
            }
        }
    }

    private void rockStageCheck(GameState state){
        int hp = health.hp();
        int maxHp = health.getMaxHealth();

        if(hp <=  maxHp * .8 && stage == 5){
            stage = 4;
            animation.playAnimation("rock80");
        }
        if(hp <=  maxHp * .6 && stage == 4){
            stage = 3;
            animation.playAnimation("rock60");
        }
        if(hp <=  maxHp * .4 && stage == 3){
            stage = 2;
            collider.setSize(105,30);
            animation.playAnimation("rock40");
        }
        if(hp <=  maxHp * .2 && stage == 2){
            stage = 1;
            collider.setOffsets(65,145);
            collider.setSize(80,20);
            animation.playAnimation("rock20");
        }
        if(hp <=  0 && stage == 1){
            stage = 0;
            state.removeGameObject(this);
        }
    }
}
