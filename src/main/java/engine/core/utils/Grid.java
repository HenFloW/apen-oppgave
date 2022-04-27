package engine.core.utils;

import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Grid<T> {
    protected List<T> grid;
    protected final int row;
    protected final int col;
    protected final T objectType;

    public Grid(int rows, int cols, T type){
        this.grid = new ArrayList<>();
        this.row = rows;
        this.col = cols;
        this.objectType = type;
        init(type);
    }

    private void init(T type) {
        for (int i = 0; i < col*row; i++) {
            grid.add(type);
        }
    }


    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public T get(int x, int y){
        return grid.get(x+y*col);
    }

    public abstract boolean isOnGrid(GameObject object);
}
