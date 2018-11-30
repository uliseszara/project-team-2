package cs361.battleships.models;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;


public class SquareTest {
    @Test
    public void testSetAndGetShips() {
        List<Ship> s1 = new ArrayList<Ship>();
        Square sq1 = new Square(5, 'A');
        sq1.setShips(s1);
        assertSame(s1, sq1.getShips());
    }

    @Test
    public void testAddShip() {
        Ship ship1 = new Battleship();
        Square sq1 = new Square(5, 'B');
        sq1.addShip(ship1);
        assertSame(ship1, sq1.getShips().get(0));
    }

    @Test
    public void testRemoveShip() {
        List<Ship> s1 = new ArrayList<Ship>();
        Ship mine = new Minesweeper();
        Ship battle = new Battleship();
        s1.add(mine);
        s1.add(battle);
        Square sq1 = new Square(5, 'A');
        sq1.setShips(s1);
        assertSame(sq1.getShips().get(0), mine);
        assertSame(sq1.getShips().get(1), battle);
        sq1.removeShip(mine);
        assertSame(sq1.getShips().get(0), battle);
    }
}
