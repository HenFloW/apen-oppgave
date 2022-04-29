package engine.objects;

import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Collider2D;
import engine.core.utils.Health;
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
    protected Health health;
    protected String objectName = "GameObject";

    private ArrayList<GameObject> childRemoveQueue;
    private GameObject parent;

    public boolean destructible;
    public boolean collideable = true;


    public GameObject(){
        this.position = new Position(0,0);
        this.objectPoint = new Position(0,0);

        this.size = new Size(100,100);
        this.collider = new Collider2D(0,0,size.getWidth(),size.getHeight());

        this.children = new ArrayList<>();
        this.childRemoveQueue = new ArrayList<>();

        this.health = new Health(100);
    }
    
    public void update(GameState state){
        health.update(state);
        collider.update(this);
        objectPoint.update(this);
    };

    public abstract void render(Graphics g);

    public abstract void action(GameState state, GameObject other);

    public void drawSprite(Graphics g, BufferedImage image){
        if(image != null){
            g.drawImage(image,0,0,null);
        }
    }

    public Size getSize() {
        return this.size;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
        this.collider.update(this);
        this.objectPoint.update(this);
    }

    public Collider2D getCollider() {
        return collider;
    }

    public boolean isColliding(GameObject other){
        return this.collider.intersects(other.getCollider());
    }

    public Position getObjectPoint() {
        return objectPoint;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject getParent() {
        return parent;
    }

    public List<GameObject> getChildren() {
        return children;
    }

    public void addChild(GameObject child) {
        this.children.add(child);
    }

    public void removeChildren(){
        this.children.removeAll(childRemoveQueue);
        this.childRemoveQueue = new ArrayList<>();
    }

    public void childRemove(GameObject child){
        this.childRemoveQueue.add(child);
    }
    public ArrayList<GameObject> childRemoveQueue() {
        return childRemoveQueue;
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", objectName, position);
    }

    public Health getHealth() {
        return health;
    }
}
