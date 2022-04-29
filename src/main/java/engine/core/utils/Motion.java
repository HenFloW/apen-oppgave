package engine.core.utils;

import engine.controller.Controllable;
import engine.core.math.Vector2D;
import engine.objects.GameObject;
import engine.objects.moving.MovingEntity;


public class Motion {
    private Vector2D vector;
    private double speed;
    private double effectSpeed;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0,0);
    }

    public void update(Controllable controller, MovingEntity entity){
        Vector2D localVector = new Vector2D(0,0);

        if(controller.requestingUp()){
            localVector.addY(-1);
        }
        if(controller.requestingDown()){
            localVector.addY(1);
        }
        if(controller.requestingLeft()){
            localVector.addX(-1);
        }
        if(controller.requestingRight()){
            localVector.addX(1);
        }

        vector = localVector.normalize().multiplied(speed);
    }

    public Vector2D getVector() {
        return vector;
    }

    public void setSpeed(double speed) {
        this.speed = effectSpeed != 0 ? effectSpeed : speed;
    }

    public boolean isMoving(){
        return vector.getX() != 0 || vector.getY() != 0;
    }

    public void stop() {
        vector = new Vector2D(0,0);
    }

    public void setVX(double i) {
        vector = new Vector2D(i, vector.getY());
    }

    public void setVY(double i) {
        vector = new Vector2D(vector.getX(), i);
    }

    public void effectSpeed(int parseInt) {
        effectSpeed = parseInt;
    }

    public double getSpeed() {
        return speed;
    }
}
