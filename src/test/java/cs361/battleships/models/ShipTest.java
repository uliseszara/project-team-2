package cs361.battleships.models;

import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class ShipTest {

    @Test
    public void testShipConstructor() {
        Ship ship = new Ship();
        assertEquals(ship.getSunk(), false);
    }

    @Test
    public void testGetSetCaptainsQuarters() {
        Ship ship = new Ship();
        ship.setCaptainsQuartersX(2);
        ship.setCaptainsQuartersY('C');
        assertEquals(ship.getCaptainsQuartersX(), 2);
        assertEquals(ship.getCaptainsQuartersY(), 'C');
    }

    @Test
    public void testHit() {
        Ship ship = new Destroyer();
        ship.setCaptainsQuartersX(2);
        ship.setCaptainsQuartersY('C');
        assertEquals(ship.hit(2,'D'), false);
        assertEquals(ship.getSunk(), false);
        assertEquals(ship.hit(2,'C'), false);
        assertEquals(ship.hit(2,'C'), true);
        assertEquals(ship.getSunk(), true);
        assertEquals(ship.getCaptainHit(), false);
        assertEquals(ship.getKind(), "destroyer");
    }
}