package engine.core.math;

import engine.core.utils.Motion;
import engine.objects.GameObject;

public class Position {
    private double x;
    private double y;
    private double offsetX;
    private double offsetY;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int intX() {
        return (int) getX();
    }

    public int intY() {
        return (int) getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setOffset(int x, int y) {
        this.x += x;
        this.y += y;
        this.offsetX = x;
        this.offsetY = y;
    }

    public Position copyOf() {
        return new Position(this.x, this.y);
    }

    public void update(GameObject object) {
        this.x += object.getPosition().intX()-this.x+offsetX;
        this.y += object.getPosition().intY()-this.y+offsetY;
    }

    public void setAsOther(Position objectPoint) {
        this.x = objectPoint.getX();
        this.y = objectPoint.getY();
    }

    public void apply(Motion movement) {
        Vector2D addVector =  movement.getVector();
        this.x += addVector.getX();
        this.y += addVector.getY();
    }
    public void applyVector(Vector2D vector2D) {
        this.x += vector2D.getX();
        this.y += vector2D.getY();
    }

    public void revert(Motion movement) {
        Vector2D addVector =  movement.getVector();
        this.x -= addVector.intX();
        this.y -= addVector.intY();
    }

    public double length(Position position) {
        return Math.sqrt(Math.pow((position.getX()-x), 2)+Math.pow((position.getY()-y), 2));
    }

    public Vector2D vectorBetween(Position position) {
        return new Vector2D(position.getX()-getX(), position.getY()-getY());
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + intX() +
                ", y=" + intY() +
                '}';
    }

}
