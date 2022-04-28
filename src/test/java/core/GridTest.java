package core;

import SurvivorGame.objects.tiles.Tile;
import engine.core.math.Size;
import engine.core.utils.Grid;
import engine.display.Camera;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    @Test
    public void createGrid(){
        Grid<Object> grid = new Grid<>(20,10);

        assertNotNull(grid);
        assertEquals(10, grid.getCol());
        assertEquals(20, grid.getRow());
    }

    @Test
    public void gridGetSet(){
        Grid<Object> grid = new Grid<>(20,10);

        assertNull(grid.get(1,1));
        assertNull(grid.get(0,0));

        Tile tile = new Tile();
        Camera camera = new Camera(new Size(10,10));

        grid.set(0,0, camera);
        grid.set(1,1, tile);

        assertNotNull(grid.get(1,1));
        assertTrue(grid.get(1,1) instanceof Tile);
        assertTrue(grid.get(0,0) instanceof Camera);
    }
}
