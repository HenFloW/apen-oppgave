package core;

import engine.core.utils.Dict;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictTest {
    @Test
    void getValue(){
        Dict<String, Integer> test = new Dict<>();
        test.put("hei", 10);
        test.put("sup", 20);
        test.put("henrik", 30);

        assertEquals(10, test.key("hei"));
        assertEquals(20, test.key("sup"));
        assertEquals(30, test.key("henrik"));


    }

    @Test
    void unknownKey(){
        Dict<String, Integer> test = new Dict<>();

        test.put("henrik", 20);

        assertEquals(20, test.key("henrik"));
        assertEquals(null, test.key("what"));
    }

    @Test
    void valueChanger(){
        Dict<String, Integer> test = new Dict<>();

        test.put("henrik", 20);
        assertEquals(20, test.key("henrik"));

        test.put("henrik", 30);
        assertFalse(20 == test.key("henrik"));
        assertEquals(30, test.key("henrik"));

    }

    @Test
    void getKeys(){
        Dict<String, Integer> test = new Dict<>();
        test.put("1", 1);
        test.put("2", 2);
        test.put("3", 3);

        String[] actualKeys = new String[] {"1","2","3"};

        test.keys().forEach(
                (String k) -> assertEquals(actualKeys[test.keys().indexOf(k)], k));
    }

    @Test
    void getValues() {
        Dict<String, Integer> test = new Dict<>();
        test.put("1", 1);
        test.put("2", 2);
        test.put("3", 3);

        int[] actualValues = new int[]{1, 2, 3};

        test.values().forEach(
                (Integer v) -> assertEquals(actualValues[test.values().indexOf(v)], v));
    }
}
