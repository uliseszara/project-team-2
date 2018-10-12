package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class ShipTest {

    @Test
    public void testShipConstructor() {
        Ship ship = new Ship("MinESwEEpeR");
        assertTrue(ship.getKind().equals("minesweeper"));
        assertTrue(ship.getLength() == 2);
        assertTrue(ship.getNumHits() == 0);
        assertTrue(ship.getOccupiedSquares() == null);
    }
}
