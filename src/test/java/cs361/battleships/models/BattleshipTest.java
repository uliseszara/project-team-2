package cs361.battleships.models;

import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class BattleshipTest {

    @Test
    public void testConstructor() {
        Ship ship = new Battleship();
        assertEquals(ship.getSunk(), false);
        assertEquals(ship.getLength(), 4);
    }

    @Test
    public void testGetSetCaptainsQuarters() {
        Ship ship = new Ship();
        ship.setCaptainsQuartersX(2);
        ship.setCaptainsQuartersY('C');
        assertEquals(ship.getCaptainsQuartersX(), 2);
        assertEquals(ship.getCaptainsQuartersY(), 'C');
    }
}