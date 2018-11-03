package cs361.battleships.models;

import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class MinesweeperTest {

    @Test
    public void testConstructor() {
        Ship ship = new Minesweeper();
        assertEquals(ship.getSunk(), false);
        assertEquals(ship.getLength(), 2);
    }

    @Test
    public void testHit() {
        Ship ship = new Minesweeper();
        ship.setCaptainsQuartersX(2);
        ship.setCaptainsQuartersY('C');
        assertEquals(ship.hit(2,'D'), false);
        assertEquals(ship.getSunk(), false);
        assertEquals(ship.hit(2,'C'), true);
        assertEquals(ship.getSunk(), true);
    }
}