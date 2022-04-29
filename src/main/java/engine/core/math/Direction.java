package engine.core.math;

public enum Direction {
    S(0),
    SW(1),
    W(2),
    NW(3),
    N(4),
    NE(5),
    E(6),
    SE(7);

    private int animationRow;

    Direction(int animationRow){
        this.animationRow = animationRow;
    }

    public static Direction fromVector(Vector2D vector){
        double x = vector.normalize().getX();
        double y = vector.normalize().getY();
        double angle = 180 + Math.atan2(x, y)*180/Math.PI;

        if(angle < 22.5 && angle >= 0 || angle <= 360 && angle >= 337.5) return N;
        else if(angle >= 22.5 && angle < 67.5) return NW;
        else if(angle >= 67.5 && angle < 112.5) return W;
        else if(angle >= 112.5 && angle < 157.5) return SW;
        else if(angle >= 157.5 && angle < 202.5) return S;
        else if(angle >= 202.5 && angle < 247.5) return SE;
        else if(angle >= 247.5 && angle < 292.5) return E;
        else if(angle >= 292.5 && angle < 337.5) return NE;

        return S;
    }

    public int getAnimationRow(){
        return animationRow;
    }
}
