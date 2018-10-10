package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

public class ResultTest
{
    @Test
    public void testInvalidPlacement() {
        Square square = new Square(1,'A');
        Ship ship = new Ship("MINESWEEPER");
        Result result = new Result(AttackStatus.MISS, ship, square);
        assertTrue(result.getShip() == ship);
        assertTrue(result.getLocation() == square);
        assertTrue(result.getResult() == AttackStatus.MISS);

    }
}
