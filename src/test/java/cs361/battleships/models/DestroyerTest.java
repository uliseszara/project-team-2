package cs361.battleships.models;

import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class DestroyerTest {

    @Test
    public void testConstructor() {
        Ship ship = new Destroyer();
        assertEquals(ship.getSunk(), false);
        assertEquals(ship.getLength(), 3);
    }
}