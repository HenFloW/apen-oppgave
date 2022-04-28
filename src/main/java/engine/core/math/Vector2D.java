package engine.core.math;

import java.text.DecimalFormat;

public class Vector2D {
    private double x, y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void addX(double x) {
        this.x += x;
    }

    public void addY(double y) {
        this.y += y;
    }

    public int intX() {
        return (int) x;
    }
    public int intY() {
        return (int) y;
    }

    public double length(){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public Vector2D multiplied(double multiplier){
        return new Vector2D(x*multiplier,y*multiplier);
    }

    public Vector2D normalize(){
        double length = length();
        double nx = x == 0 ? 0 : x/length;
        double ny = y == 0 ? 0 : y/length;

        return new Vector2D(nx, ny);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0###");
        return "Vector2D{" +
                "x=" + df.format(x).replace(',', '.') +
                ", y=" + df.format(y).replace(',', '.') +
                '}';
    }
}
