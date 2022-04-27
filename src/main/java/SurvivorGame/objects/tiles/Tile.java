package SurvivorGame.objects.tiles;

import engine.gfx.ImageUtils;
import engine.gfx.ResourceLibrary;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage sprite;

    public Tile(ResourceLibrary resourceLibrary){
        this.sprite = ImageUtils.toCompatibleImage(
                            ImageUtils.loadImage(resourceLibrary.getFileFromKey("sprites/tiles/island", "grass")));
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
