package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', false));
    }

    @Test
    public void testValidPlacement() {
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 7, 'G', false));
    }

    @Test
    public void testInvalidPlacement2() {
        Board board = new Board();
        board.placeShip(new Ship("m"),1,'A',true);
        board.placeShip(new Ship("d"),1,'B',true);
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 3, 'A', false));
    }
    @Test
    public void testInvalidPlacement3() {
        Board board = new Board();
        board.placeShip(new Ship("m"),1,'A',true);
        board.placeShip(new Ship("d"),1,'B',true);
        assertFalse(board.placeShip(new Ship("d"), 9, 'A', false));
    }

    @Test
    public void testInvalidAttack () {
        // set up a board with an attack already on it
        Board board = new Board();
        board.attack(8, 'C');
        // attack the square already attacked
        assertTrue(board.attack(8, 'C').getResult() == AttackStatus.INVALID);
        // attack out of bounds
        assertTrue(board.attack(11, 'C').getResult() == AttackStatus.INVALID);
        assertTrue(board.attack(8, 'K').getResult() == AttackStatus.INVALID);
    }

    @Test
    public void testMissedAttack () {
        Board board = new Board();
        assertTrue(board.attack(8, 'C').getResult() == AttackStatus.MISS);
    }

    @Test
    public void testHit (){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        assertTrue(board.attack(8, 'C').getResult() == AttackStatus.HIT);
    }

    @Test
    public void testSunk() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        board.placeShip(new Ship("BATTLESHIP"), 2, 'C', false);
        board.attack(8, 'C');
        assertTrue(board.attack(8, 'D').getResult() == AttackStatus.SUNK);
    }

    @Test
    public void testSurrender() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        board.attack(8, 'C');
        assertTrue(board.attack(8, 'D').getResult() == AttackStatus.SURRENDER);
    }
}
