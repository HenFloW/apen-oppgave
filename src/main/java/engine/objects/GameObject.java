package engine.objects;

import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    protected Size size;
    protected Position position;
    protected Position objectPoint;
    protected Collider2D collider;

    protected List<GameObject> children;
    private ArrayList<GameObject> childRemoveQueue;
    private GameObject parent;

    public boolean destructable;
    public boolean collideable = true;

    public GameObject(){
        this.position = new Position(0,0);
        this.objectPoint = new Position(0,0);

        this.size = new Size(100,100);
        this.collider = new Collider2D(0,0,size.getWidth(),size.getHeight());

        this.children = new ArrayList<>();
        this.childRemoveQueue = new ArrayList<>();
    }
    
    public abstract void update(GameState state);

    public abstract void render(Graphics g);

    public abstract void action(GameState state, GameObject other);

    public abstract BufferedImage getSprite();

    public void drawSprite(Graphics g, BufferedImage image){
        if(image != null){
            g.drawImage(image,0,0,null);
        }
    }

    public Position getPosition() {
        return position;
    }

    public Collider2D getCollider() {
        return collider;
    }

    public Position getObjectPoint() {
        return objectPoint;
    }

    public Size getSize() {
        return this.size;
    }

    public boolean isColliding(GameObject other){
        return this.collider.intersects(other.getCollider());
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject getParent() {
        return parent;
    }

    public void addChild(GameObject child) {
        this.children.add(child);
    }

    public void removeChildren(ArrayList<GameObject> children){
        this.children.removeAll(children);
        this.childRemoveQueue = new ArrayList<>();
    }

    public void childRemoveQueue(GameObject child){
        this.getChildRemoveQueue().add(child);
    }

    public ArrayList<GameObject> getChildRemoveQueue(){
        return this.childRemoveQueue;
    }

    public List<GameObject> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "position=" + position +
                '}';
    }

}
