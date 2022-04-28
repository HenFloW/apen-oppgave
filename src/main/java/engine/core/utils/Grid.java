package engine.core.utils;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> {
    private List<T> grid;
    private final int row;
    private final int col;
    protected final T objectType;

    public Grid(int rows, int cols, T type){
        this.grid = new ArrayList<>();
        this.row = rows;
        this.col = cols;
        this.objectType = type;
        init(type);
    }

    public Grid(int x, int y){
        this(x,y,null);
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

    public void set(int x, int y, T object){
        grid.set(x+col*y, object);
    }

    public T get(int x, int y){
        return grid.get(x+y*col);
    }
}
