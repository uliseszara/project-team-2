package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

public class ResultTest
{
    @Test
    public void testInvalidPlacement() {
        Square square = new Square(1,'A');
        Square square2 = new Square();
        square2.setColumn('E');
        square2.setRow(5);
        Ship ship = new Minesweeper();
        Result result = new Result(AttackStatus.MISS, ship, square);
        Result result2 = new Result();
        result.setLocation(result.getLocation());
        result.setResult(result.getResult());
        result.setShip(result.getShip());
        assertTrue(result.getShip() == ship);
        assertTrue(result.getLocation() == square);
        assertTrue(result.getResult() == AttackStatus.MISS);

    }
}
