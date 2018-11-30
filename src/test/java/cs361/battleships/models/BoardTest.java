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
        assertFalse(board.placeShip(new Minesweeper(), 11, 'J', false));
    }

    @Test
    public void testInvalidPlacementOffBottom() {
        Board board = new Board();
        assertFalse(board.placeShip(new Minesweeper(), 10, 'J', true));
    }

    @Test
    public void testInvalidPlacementOffSide() {
        Board board = new Board();
        assertFalse(board.placeShip(new Minesweeper(), 10, 'J', false));
    }

    @Test
    public void testValidPlacementHorizontal() {
        Board board = new Board();
        assertTrue(board.placeShip(new Battleship(), 4, 'D', false));
        assertTrue(board.placeShip(new Submarine(), 6, 'D', false));
        assertTrue(board.getSquares()[4]['D'-'A'].getOccupied());
        assertSame(board.getSquares()[4]['D'-'A'].getShips().get(0), board.getShips().get(0));
        assertTrue(board.getSquares()[4]['E'-'A'].getOccupied());
        assertSame(board.getSquares()[4]['E'-'A'].getShips().get(0), board.getShips().get(0));
    }

    @Test
    public void testValidPlacementVertical() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 4, 'D', true));
        assertTrue(board.getSquares()[4]['D'-'A'].getOccupied());
        assertSame(board.getSquares()[4]['D'-'A'].getShips().get(0), board.getShips().get(0));
        assertTrue(board.getSquares()[5]['D'-'A'].getOccupied());
        assertSame(board.getSquares()[5]['D'-'A'].getShips().get(0), board.getShips().get(0));
    }

    @Test
    public void testSetGetShips() {
        Board board = new Board();
        List<Ship> ships = new ArrayList<Ship>();
        Ship s1 = new Minesweeper();
        Ship s2 = new Destroyer();
        ships.add(s1);
        ships.add(s2);
        board.setShips(ships);
        assertSame(ships, board.getShips());
    }

    @Test
    public void testInvalidPlacementShipOverShip() {
        Board board = new Board();
        board.placeShip(new Minesweeper(),1,'A',true);
        board.placeShip(new Destroyer(),1,'B',true);
        assertFalse(board.placeShip(new Battleship(), 3, 'A', false));
    }
    @Test
    public void testInvalidPlacementMultipleShipType() {
        Board board = new Board();
        board.placeShip(new Minesweeper(),1,'A',true);
        board.placeShip(new Destroyer(),1,'B',true);
        assertFalse(board.placeShip(new Destroyer(), 9, 'A', false));
    }
    @Test
    public void testDuplicateAttack () {
        Board board = new Board();
        board.attack(8, 'C');
        assertSame(board.attack(8, 'C').getResult(), AttackStatus.MISS);
    }

    @Test
    public void testOutOfBoundsAttack() {
        Board board = new Board();
        assertSame(board.attack(11, 'C').getResult(), AttackStatus.INVALID);
        assertSame(board.attack(8, 'K').getResult(), AttackStatus.INVALID);
    }

    @Test
    public void testMissedAttack () {
        Board board = new Board();
        board.attack(9, 'E');
        assertSame(board.attack(8, 'C').getResult(), AttackStatus.MISS);
    }

    @Test
    public void testHit (){
        Board board = new Board();
        board.placeShip(new Minesweeper(), 8, 'C', false);
        assertSame(board.attack(8, 'D').getResult(), AttackStatus.HIT);
    }

    @Test
    public void testInvalidDoubleHit (){
        Board board = new Board();
        board.placeShip(new Minesweeper(), 8, 'C', false);
        assertSame(board.attack(8, 'D').getResult(), AttackStatus.HIT);
        assertSame(board.attack(8,'D').getResult(), AttackStatus.INVALID);
    }

    @Test
    public void testSunkMinesweeper() {
        Board board = new Board();
        board.placeShip(new Minesweeper(), 8, 'C', false);
        board.placeShip(new Battleship(), 3, 'C', false);
        assertSame(board.attack(8, 'C').getResult(), AttackStatus.SUNK);
    }

    @Test
    public void testSunkBattleship() {
        Board board = new Board();
        board.placeShip(new Battleship(), 8, 'C', false);
        board.placeShip(new Minesweeper(), 3, 'C', false);
        assertSame(board.attack(8, 'E').getResult(), AttackStatus.MISS);
        assertSame(board.attack(8, 'E').getResult(), AttackStatus.SUNK);
        assertTrue(board.getShips().get(0).getSunk());
    }
    @Test
    public void testSurrender() {
        Board board = new Board();
        board.placeShip(new Minesweeper(), 8, 'C', false);
        assertSame(board. attack(8, 'C').getResult(), AttackStatus.SURRENDER);
    }

    @Test public void testSonars() {
        Board board = new Board();
        assertSame(board.getSonarsLeft(), 2);
        board.decSonarsLeft(5);
        board.setAttacks(board.getAttacks());
    }
}
