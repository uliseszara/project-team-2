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
}
