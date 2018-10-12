package cs361.battleships.models;

import org.junit.Test;


import static org.junit.Assert.assertTrue;


public class ShipTest {

    @Test
    public void testShipConstructor() {
        Ship ship1 = new Ship("MinESwEEpeR");
        assertTrue(ship1.getKind().equals("minesweeper"));
        assertTrue(ship1.getLength()==2);
        assertTrue( ship1.getNumHits()==0);
        assertTrue(ship1.getOccupiedSquares()== null);

        Ship ship2 = new Ship("DESTROYER");
        assertTrue(ship2.getKind().equals("destroyer"));
        assertTrue(ship2.getLength()==3);
        assertTrue( ship2.getNumHits()==0);
        assertTrue(ship2.getOccupiedSquares()== null);

        Ship ship3 = new Ship("BATTLESHIP");
        assertTrue(ship3.getKind().equals("battleship"));
        assertTrue(ship3.getLength()==4);
        assertTrue( ship3.getNumHits()==0);
        assertTrue(ship3.getOccupiedSquares()== null);

        Ship ship4 = new Ship("loihjdf");
        assertTrue(ship4.getKind().equals(""));
        assertTrue(ship4.getLength()==0);
        assertTrue(ship4.getNumHits()==0);
        assertTrue(ship4.getOccupiedSquares()==null);
    }
}
