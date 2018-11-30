package cs361.battleships.models;

import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class GameTest
{
    @Test
    public void testGamePlaceShip() {
        Ship ship = new Minesweeper();
        Game game = new Game();
        assertTrue(game.placeShip(ship, 0, 'A', true));
    }

    @Test
    public void testGameAttack() {
        Game game = new Game();
        assertFalse(game.attack(-5,'A'));
        assertTrue(game.attack(1,'A'));
    }

    @Test
    public void testBattleshipandSubmarine() {
        Game game = new Game();
        assertTrue(game.placeShip(new Submarine(), 4, 'D', false));
        assertTrue(game.placeShip(new Battleship(), 6, 'D', false));
    }
}
