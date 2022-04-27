package SurvivorGame.objects.entities;

import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.objects.entities.player.actions.UseAction;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.game.GameState;
import engine.gfx.ImageUtils;
import engine.gfx.ResourceLibrary;
import engine.objects.GameObject;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rock extends GameObject{
        private BufferedImage sprite;

        public Rock(ResourceLibrary resourceLibrary, Position position) {
            super();
            this.position = position;
            this.objectPoint = position.getOffsetPosition(187, 200);
            this.sprite = ImageUtils.loadImage(resourceLibrary.getFileFromKey("sprites/entities/static", "rock"));
            this.sprite = Scalr.resize(sprite, Scalr.Method.ULTRA_QUALITY, 350);
            this.size = new Size(sprite.getWidth(), sprite.getHeight());
            this.collider = new Collider2D(this);
            collider.setOffsets(86, 182);
            collider.setSize(215, 30);
            destructable = true;
        }

    @Override
    public void update(GameState state) {

    }

    @Override
    public void render(Graphics g) {
        drawSprite(g,sprite);
    }

    @Override
    public void action(GameState state, GameObject other) {
        if(other instanceof Player player){
            if(player.getAction().isPresent()){
                if(player.getAction().get() instanceof UseAction){
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
