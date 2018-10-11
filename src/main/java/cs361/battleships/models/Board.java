package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Result> attacks; // list of squares on this board that have been attacked
	private List<Ship> ships; // list of ships on this board

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		attacks = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		// first check to see if this attack is in bounds
		if (x < 1 || x > 10 || y < 'A' || y > 'J') {
			return new Result(AtackStatus.INVALID, null, new Square(x, y));
		}
		// then check the attacks already made; no duplicate attacks allowed
		for (Result r : attacks) {
			// if this attack is targeted at a square already attacked it is invalid
			if (r.getLocation().getRow() == x && r.getLocation().getColumn() == y) {
				return new Result(AtackStatus.INVALID, null, new Square(x, y));
			}
		}
		// the function is still running, so the attack is valid
		// next check to see if the attack hits a ship. Initialize a tool to help us later
		Ship shipHit = null;
		// now for each ship
		for (Ship ship : ships) {
			// look at the squares in that ship
			for (Square square : ship.getOccupiedSquares()) {
				// if this attack targets one of those squares it is a hit
				if (square.getRow() == x && square.getColumn() == y) {
					shipHit = ship;
					ship.incNumHits();
					break;
				}
			}
		}
		// if this was a hit
		if (shipHit != null) {
			// check if the game is over
			boolean surrender = true;
			for (Ship ship : ships) {
				if (ship.getNumHits() < ship.getLength()) {
					surrender = false;
				}
			}
			if (surrender) {
				return new Result(AtackStatus.SURRENDER, null, new Square(x, y));
			}
			// check for sunk
			if (shipHit.getNumHits() == shipHit.getLength()) {
				return new Result(AtackStatus.SUNK, shipHit, new Square(x, y));
			}
			// if the code is still running than it is only a hit
			return new Result(AtackStatus.HIT, shipHit, new Square(x, y));
		}
		// no ship was hit
		return new Result(AtackStatus.MISS, null, new Square(x, y));
	}

	public List<Ship> getShips() {
		//TODO implement
		return null;
	}

	public void setShips(List<Ship> ships) {
		//TODO implement
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}
