package cs361.battleships.models;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;


public class BoardTest {

    @Test
    public void testInvalidPlacementOffBoard() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'J', false));
    }

    @Test
    public void testInvalidPlacementOffBottom() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', true));
    }

    @Test
    public void testInvalidPlacementOffSide() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', false));
    }

    @Test
    public void testValidPlacement() {
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 7, 'G', false));
    }

    @Test
    public void testSetGetShips() {
        Board board = new Board();
        List<Ship> ships = new ArrayList<Ship>();
        Ship s1 = new Ship("m");
        Ship s2 = new Ship("d");
        ships.add(s1);
        ships.add(s2);
        board.setShips(ships);
        assertSame(ships, board.getShips());
    }

    @Test
    public void testInvalidPlacementShipOverShip() {
        Board board = new Board();
        board.placeShip(new Ship("m"),1,'A',true);
        board.placeShip(new Ship("d"),1,'B',true);
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 3, 'A', false));
    }
    @Test
    public void testInvalidPlacementMultipleShipType() {
        Board board = new Board();
        board.placeShip(new Ship("m"),1,'A',true);
        board.placeShip(new Ship("d"),1,'B',true);
        assertFalse(board.placeShip(new Ship("d"), 9, 'A', false));
    }

    @Test
    public void testDuplicateAttack () {
        // set up a board with an attack already on it
        Board board = new Board();
        board.attack(8, 'C');
        // attack the square already attacked
        assertSame(board.attack(8, 'C').getResult(), AttackStatus.INVALID);
    }

    @Test
    public void testOutOfBoundsAttack() {
        Board board = new Board();
        // attack out of bounds
        assertSame(board.attack(11, 'C').getResult(), AttackStatus.INVALID);
        assertSame(board.attack(8, 'K').getResult(), AttackStatus.INVALID);
    }

    @Test
    public void testMissedAttack () {
        Board board = new Board();
        assertSame(board.attack(8, 'C').getResult(), AttackStatus.MISS);
    }

    @Test
    public void testGetAttacks () {
        Board board = new Board();
        board.attack(8, 'C');
        assertTrue(board.getAttacks().get(0).getLocation().getRow() == 8 && board.getAttacks().get(0).getLocation().getColumn() == 'C');
    }

    @Test
    public void testHit (){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        assertSame(board.attack(8, 'C').getResult(), AttackStatus.HIT);
}

    @Test
    public void testSunk() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        board.placeShip(new Ship("BATTLESHIP"), 2, 'C', false);
        board.attack(8, 'C');
        assertSame(board.attack(8, 'D').getResult(), AttackStatus.SUNK);
    }

    @Test
    public void testSurrender() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        board.attack(8, 'C');
        assertSame(board.attack(8, 'D').getResult(), AttackStatus.SURRENDER);
    }
}
