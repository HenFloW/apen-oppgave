package core;

import engine.core.math.Position;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    public void newPositionTest(){
        Position p1 = new Position(1,1);
        Position p2 = new Position(1,2);
        Position p3 = new Position(2,2);

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);

        assertEquals(1, p1.intX());
        assertEquals(1, p1.intY());

        assertEquals(1, p2.intX());
        assertEquals(2, p2.intY());

        assertEquals(2, p3.intX());
        assertEquals(2, p3.intY());
    }

    @Test
    public void positionSet(){
        Position p1 = new Position(1,1);
        Position p2 = new Position(1,2);
        Position p3 = new Position(2,2);

        assertEquals(1, p1.intX());
        p1.setX(2);                         //changing the x value to 2
        assertEquals(2, p1.intX());

        assertEquals(2, p2.intY());
        p2.setY(1);                         //changing the y value to 1
        assertEquals(1, p2.intY());
    }

    @Test
    public void positionToString(){
        Position p1 = new Position(1,1);
        Position p2 = new Position(5,5);

        assertEquals("Position{x=1, y=1}", p1.toString());
        assertEquals("Position{x=5, y=5}", p2.toString());
    }

    @Test
    public void setPositionAsOther(){
        Position p1 = new Position(1,1);
        Position p2 = p1.copyOf();
        Position p3 = new Position(5,5);

        assertNotSame(p1, p2);                        //Check if p1 is different from p2
        assertEquals(p1.toString(), p2.toString());     //Check if p1 and p2 has the same position

        assertNotSame(p1, p3);                        //Check if p1 is different from p3
        assertNotEquals(p1.toString(), p3.toString());  //p1 and p3 should not have the same position

        p3.setAsOther(p1);                              //set p3 as the same position as p1

        assertNotSame(p1, p3);                        //Check if p1 is not the same object
        assertEquals(p1.toString(), p3.toString());     //check if p1 and p3 has the same values after setAsOther


    }

    @Test
    public void lengthBetweenPoints(){
        DecimalFormat df = new DecimalFormat("###.######");

        Position p1 = new Position(1,1);
        Position p2 = new Position(3,3);
        Position p3 = new Position(5,5);


        assertEquals("2,828427", df.format(p1.length(p2)));
        assertEquals("2,828427", df.format(p2.length(p1)));

        assertEquals("2,828427", df.format(p2.length(p3)));
        assertEquals("2,828427", df.format(p3.length(p2)));

        assertEquals("5,656854", df.format(p1.length(p3)));
        assertEquals("5,656854", df.format(p3.length(p1)));
    }

}
