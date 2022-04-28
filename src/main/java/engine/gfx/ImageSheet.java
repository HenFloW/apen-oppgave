package engine.gfx;

import java.awt.image.BufferedImage;

public class ImageSheet {
    private BufferedImage image;
    private int subImagePixels,animationSpeed, frameCount;

    public ImageSheet(BufferedImage image, int animationSpeed, int subImagePixels){
        this.image = image;
        this.animationSpeed = animationSpeed;
        this.subImagePixels = subImagePixels;
        this.frameCount = ((BufferedImage)this.image).getWidth()/this.subImagePixels - 1;
    }

    public BufferedImage getSheet() {
        return image;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public int getSubImagePixels() {
        return subImagePixels;
    }

    public int getFrameCount() {
        return frameCount;
    }
}
