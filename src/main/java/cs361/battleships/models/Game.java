package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        Ship opponentsShip;
        if (ship.getLength() == 2)
            opponentsShip = new Minesweeper();
        else if(ship.getLength() == 3)
            opponentsShip = new Destroyer();
        else if (ship.getLength() == 4)
            opponentsShip = new Battleship();
        else {
            //create random bool and pass it in
            opponentsShip = new Submarine();
            opponentsShip.setSubmerged(randVertical());
        }
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(opponentsShip, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == AttackStatus.INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == AttackStatus.INVALID);

        return true;
    }

    public void move(String direction) {
        playersBoard.moveFleet(direction);
    }

    private char randCol() {
        Random r = new Random();
        char c = (char)(r.nextInt(10) + 'A');
        return c;
    }

    private int randRow() {
        Random rand = new Random();
        int  n = rand.nextInt(10);
        return n;
    }

    private boolean randVertical() {
        Random rand2 = new Random();
        return rand2.nextBoolean();
    }
}

