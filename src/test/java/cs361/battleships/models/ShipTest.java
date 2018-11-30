package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ShipTest {

    @Test
    public void testShipConstructor() {
        Ship ship = new Ship();
        assertFalse(ship.getSunk());
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
        assertFalse(ship.hit(2,'D'));
        assertFalse(ship.getSunk());
        assertFalse(ship.hit(2,'C'));
        assertTrue(ship.hit(2,'C'));
        assertTrue(ship.getSunk());
        assertFalse(ship.getCaptainHit());
        assertEquals(ship.getKind(), "destroyer");
    }

    @Test
    public void testGetSetOccupiedSquares() {
        Ship ship = new Destroyer();
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(2,'A'));
        squares.add(new Square(2, 'B'));
        squares.add(new Square(2, 'C'));
        squares.add(new Square(2, 'D'));
        ship.setOccupiedSquares(squares);
        assertSame(ship.getOccupiedSquares(), squares);
    }

    @Test
    public void testAddSquare() {
        Ship ship = new Destroyer();
        Square square = new Square();
        ship.addSquare(square);
        assertSame(ship.getOccupiedSquares().get(0), square);
    }

    @Test
    public void testisEqual() {
        Board board = new Board();
        Ship ship = new Minesweeper();
        board.placeShip(ship, 2, 'C', false);
        Ship test1 = board.getSquares()[2][2].getShips().get(0);
        Ship test2 = board.getSquares()[2][3].getShips().get(0);
        System.out.println(test1);
        System.out.println(test2);
        assertTrue(test1.isEqual(test2));
    }
}