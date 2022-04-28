package core;

import engine.core.math.Vector2D;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
    @Test
    public void vector(){
        Vector2D v1 = new Vector2D(1.5,1.5);
        Vector2D v2 = new Vector2D(2.5,2.5);

        assertEquals(1.5, v1.getX());
        assertEquals(1.5, v1.getY());

        assertEquals(2.5, v2.getX());
        assertEquals(2.5, v2.getY());

        assertEquals(1, v1.intX());
        assertEquals(1, v1.intX());

        assertEquals(2, v2.intX());
        assertEquals(2, v2.intY());

    }

    @Test
    public void length(){
        DecimalFormat df = new DecimalFormat("0.0###");

        Vector2D v1 = new Vector2D(1.5,1.5);
        Vector2D v2 = new Vector2D(2.5,2.5);

        assertEquals("2,1213", df.format(v1.length()));
        assertEquals("3,5355", df.format(v2.length()));
    }

    @Test
    public void multiplied(){
        Vector2D v1 = new Vector2D(1.5,1.5);
        Vector2D v2 = new Vector2D(2.5,2.5);

        assertEquals("Vector2D{x=1.5, y=1.5}", v1.toString());
        assertEquals("Vector2D{x=4.5, y=4.5}", v1.multiplied(3).toString());
        assertEquals("Vector2D{x=2.5, y=2.5}", v2.toString());
        assertEquals("Vector2D{x=5.0, y=5.0}", v2.multiplied(2).toString());
    }

    @Test
    public void normalize(){
        Vector2D v1 = new Vector2D(1.5,2.5);
        Vector2D v2 = new Vector2D(1,3);

        assertEquals("Vector2D{x=0.5145, y=0.8575}", v1.normalize().toString());
        assertEquals("Vector2D{x=0.3162, y=0.9487}", v2.normalize().toString());
    }
}
