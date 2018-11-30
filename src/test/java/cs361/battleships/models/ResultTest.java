package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ResultTest
{
    @Test
    public void testInvalidPlacement() {
        Square square = new Square(1,'A');
        List<Ship> ships = new ArrayList<>();
        Ship ship = new Minesweeper();
        ships.add(ship);
        Result result = new Result(AttackStatus.MISS, ships, square);
        assertTrue(result.getShips().get(0) == ship);
        assertTrue(result.getLocation() == square);
        assertTrue(result.getResult() == AttackStatus.MISS);

    }
}
