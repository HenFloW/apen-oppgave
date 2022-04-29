package engine.gfx;

import engine.core.math.Direction;
import engine.core.math.Size;
import engine.objects.GameObject;

import java.awt.image.BufferedImage;

public class Animation {
    private SpriteSet spriteSet;
    private ImageSheet currentSheet;

    private int animationHoldFrames;
    private int currentFrameTime;

    private Size gridSize;
    private int directionIndex;
    private int frameIndex;
    private String currentAnimationName;

    public Animation(SpriteSet spriteSet, GameObject object){
        this.spriteSet = spriteSet;
        this.currentSheet = spriteSet.get("placeholder");
        this.currentAnimationName = "placeholder";
        this.gridSize = object.getSize();
    }

    public BufferedImage getSprite(){
            if(currentSheet != null){
                return (currentSheet.getSheet()).getSubimage(
                        frameIndex * gridSize.getWidth(),
                        directionIndex * gridSize.getHeight(),
                        gridSize.getWidth(),
                        gridSize.getHeight());
            }
        return null;
    }

    public void update(){
        currentFrameTime++;

        if (currentSheet != null){
            if(currentFrameTime >= animationHoldFrames){
                currentFrameTime = 0;
                frameIndex = frameIndex++ < currentSheet.getFrameCount() ? frameIndex : 0;
            }
        }
    }

    public void update(Direction direction){
        directionIndex = direction != null ? direction.getAnimationRow() : 0;
        update();
    }

    public void playAnimation(String name){
        if(!(currentAnimationName.equals(name))){
            this.currentSheet = spriteSet.get(name);
            this.currentAnimationName = name;
            if(currentSheet != null){
                this.animationHoldFrames = currentSheet.getAnimationSpeed();
            }
            this.frameIndex = 0;
        }
    }

    public void setIndex(int f) {
        this.frameIndex = f;
    }

    public void setDirectionIndex(int directionIndex) {
        this.directionIndex = directionIndex;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public int getDirectionIndex() {
        return directionIndex;
    }

    public void restartAnimation() {
        this.frameIndex = 0;
    }
}
