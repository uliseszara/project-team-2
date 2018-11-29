package cs361.battleships.models;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;


public class SquareTest {
    @Test
    public void testSetAndGetShip() {
        List<Ship> s1 = new ArrayList<Ship>();
        Square sq1 = new Square(5, 'A');
        sq1.setShip(s1);
        assertSame(s1, sq1.getShips());
    }

    @Test
    public void testAddShip() {
        Ship ship1 = new Battleship();
        Square sq1 = new Square(5, 'B');
        sq1.addShip(ship1);
        assertSame(ship1, sq1.getShips().get(0));
    }
}
