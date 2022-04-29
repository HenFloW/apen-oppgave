package engine.core.utils;

import engine.objects.GameObject;

import java.awt.*;

public class Collider2D extends Rectangle {

    private double offsetX = 0, offsetY = 0;

    public Collider2D(GameObject object) {
        super(
                object.getPosition().intX(),
                object.getPosition().intY(),
                object.getSize().getWidth(),
                object.getSize().getHeight());
    }

    public Collider2D(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setOffsets(int x, int y){
        if(offsetX == 0){
            this.x += x;
        } else {
            this.x += -offsetX+x;
        }
        if(offsetY == 0){
            this.y += y;
        } else {
            this.y += -offsetY+y;
        }
        this.offsetX = x;
        this.offsetY = y;
    }

    public void update(GameObject object) {
        this.x += object.getPosition().intX()-this.x+this.offsetX;
        this.y += object.getPosition().intY()-this.y+this.offsetY;
    }

    public void setSize(int x, int y) {
        this.width = x;
        this.height = y;
    }

    public Collider2D copy() {
        return new Collider2D(this.x, this.y, this.width, this.height);
    }

    public Collider2D getOffsetCollider(double x, double y) {
        return new Collider2D((int)(this.x+x), (int)(this.y+y), this.width, this.height);
    }
}
