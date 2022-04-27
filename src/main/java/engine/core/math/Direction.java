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
        double x = vector.getX();
        double y = vector.getY();

        if(x == 0 && y > 0) return S;
        else if(x == 0 && y < 0) return N;
        else if(x < 0 && y == 0) return W;
        else if(x > 0 && y == 0) return E;
        else if(x > 0 && y > 0) return SE;
        else if(x < 0 && y < 0) return NW;
        else if(x < 0 && y > 0) return SW;
        else if(x > 0 && y < 0) return NE;

        return S;
    }

    public int getAnimationRow(){
        return animationRow;
    }
}
