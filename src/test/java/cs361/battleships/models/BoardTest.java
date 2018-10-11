package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }

    @Test
    public void testInvalidAttack () {
        // set up a board with an attack already on it
        Board board = new Board();
        board.attack(8, 'C');
        // attack the square already attacked
        assertTrue(board.attack(8, 'C').getResult() == AtackStatus.INVALID);
        // attack out of bounds
        assertTrue(board.attack(11, 'C').getResult() == AtackStatus.INVALID);
        assertTrue(board.attack(8, 'K').getResult() == AtackStatus.INVALID);
    }

    @Test
    public void testMissedAttack () {
        Board board = new Board();
        assertTrue(board.attack(8, 'C').getResult() == AtackStatus.MISS);
    }

    @Test
    public void testHit (){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        assertTrue(board.attack(8, 'C').getResult() == AtackStatus.HIT);
    }

    @Test
    public void testSunk() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        board.placeShip(new Ship("BATTLESHIP"), 2, 'C', false);
        board.attack(8, 'C');
        assertTrue(board.attack(8, 'D').getResult() == AtackStatus.SUNK);
    }

    @Test
    public void testSurrender() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 8, 'C', false);
        board.attack(8, 'C');
        assertTrue(board.attack(8, 'D').getResult() == AtackStatus.SURRENDER);
    }
}
