package SurvivorGame.objects.entities;

import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.objects.entities.player.actions.UseAction;
import SurvivorGame.objects.items.Axe;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.game.GameState;
import engine.gfx.Animation;
import engine.gfx.ImageUtils;
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
        this.objectPoint = position.getOffsetPosition(100, 160);
        this.size = new Size(200, 200);

        SpriteSet sprites = new SpriteSet(resourceLibrary);
        sprites.addSheet(resourceLibrary.getFilesFromTreeNode("sprites/entities/static/rockdeform"));
        this.animation = new Animation(sprites, this);
        this.animation.playAnimation("rock100");
        this.sprite = animation.getSprite();

        this.collider = new Collider2D(this);
        this.stage = 5;
        this.collider.setOffsets(40, 135);
        this.collider.setSize(135, 30);
        this.destructable = true;
    }

    @Override
    public void update(GameState state) {
        rockStageCheck(state);
    }

    @Override
    public void render(Graphics g) {
        drawSprite(g,animation.getSprite());

        if(health < 100){
            g.setColor(Color.red);
            g.drawRect(50,75,100, 4);
            g.setColor(Color.green);
            g.fillRect(50,75, Math.max(health, 0), 5);
        }
    }

    @Override
    public void action(GameState state, GameObject other) {
        if(other instanceof Player player){
            if(player.getAction().isPresent()){
                if(player.getAction().get() instanceof UseAction){
                    if(player.getChildren().stream().anyMatch(c -> c instanceof Axe)) {
                        health -= 10;
                    } else {
                        health -= 1;
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage getSprite() {
            return sprite;
        }

    private void rockStageCheck(GameState state){
        if(health <= 80 && stage == 5){
            stage = 4;
            animation.playAnimation("rock80");
        }
        if(health <= 60 && stage == 4){
            stage = 3;
            animation.playAnimation("rock60");
        }
        if(health <= 40 && stage == 3){
            stage = 2;
            collider.setSize(105,30);
            animation.playAnimation("rock40");
        }
        if(health <= 20 && stage == 2){
            stage = 1;
            collider.setOffsets(65,145);
            collider.setSize(80,20);
            animation.playAnimation("rock20");
        }
        if(health <= 0 && stage == 1){
            stage = 0;
            state.removeGameObject(this);
        }
    }
}
