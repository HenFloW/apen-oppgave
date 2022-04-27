package SurvivorGame.objects.items;

import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.objects.entities.player.actions.Dropping;
import SurvivorGame.objects.entities.player.actions.UseAction;
import SurvivorGame.state.PlayState;
import engine.core.math.Position;
import engine.game.GameState;
import engine.gfx.Animation;
import engine.gfx.ImageUtils;
import engine.gfx.SpriteSet;
import engine.objects.GameObject;
import engine.objects.Item;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Axe extends Item {
    private final Animation animation;

    public Axe(GameState state) {
        this.description = "This can fucking destroy trees";
        this.stats.put("Power", 1);

        SpriteSet s = new SpriteSet(state.getResourceLibrary());
        s.addSheet(state.getResourceLibrary().getFilesFromTreeNode("sprites/items/axe/holdAnimation"));

        this.sprite = ImageUtils.loadImage(
                state.getResourceLibrary()
                        .getFileFromKey("sprites/items/axe", "item"));

        this.sprite = Scalr.resize(sprite, Scalr.Method.ULTRA_QUALITY, 50);
        this.animation = new Animation(s);
        this.animation.playAnimation("axe_idle");

        this.position = new Position(350, 350);

        this.objectPoint = position.copyOf();
        this.objectPoint.setOffset(25,30);

        this.collider.update(this);
        this.collider.setOffsets(7, 25);
        this.collider.setSize(35,15);
        this.collideable = false;
    }

    @Override
    public void update(GameState state) {
        if(getParent() instanceof Player localParent){
            this.position = localParent.getPosition().copyOf();
            this.objectPoint.setAsOther(localParent.getObjectPoint());
            this.collider.update(this);

            if(localParent.getAction().isPresent()){
                if(localParent.getAction().get() instanceof Dropping){
                    if(getParent() != null){
                        getParent().childRemoveQueue(this);
                        setParent(null);
                        this.objectPoint.update(this);
                        state.addGameObject(this);
                    }
                }
            }

        }
    }

    @Override
    public void render(Graphics g) {
        GameObject parent = getParent();
        if(parent instanceof Player localParent) {
            if(localParent.getAction().isPresent()){
                animation.playAnimation("axe_"+localParent.getAction().get().getAnimationName());
            } else {
                this.animation.playAnimation("axe_"+localParent.getAnimationState());
            }
            this.animation.setIndex(localParent.getAnimation().getFrameIndex());
            this.animation.setDirectionIndex(localParent.getAnimation().getDirectionIndex());

            drawSprite(g,animation.getSprite());
        } else {
            drawSprite(g, sprite);
        }
    }

    @Override
    public void action(GameState state, GameObject other) {
        if(other instanceof Player player){
            if(player.getAction().isPresent()){
                if(player.getAction().get() instanceof UseAction){
                    setParent(player);
                    player.addChild(this);
                    state.removeGameObject(this);
                }
            }
        }
    }

    @Override
    public BufferedImage getSprite() {
        return sprite;
    }
}
