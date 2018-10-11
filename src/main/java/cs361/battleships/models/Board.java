package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Result> attacks; // list of squares on this board that have been attacked
	private List<Ship> ships; // list of ships on this board (cannot be more than 3)

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
		y = Character.toLowerCase(y);
		//check if there less than three ships
		if(ships.size() == 3)
		{
			return false;
		}

		//perform bounds check to see if x is between 1-10
		if(x < 1 || x > 10){
			return false;
		}

		//perform bounds check to see if y is between A-J
		if(y < 'a' || y > 'j'){
			return false;
		}

		int shipLength = ship.getLength();

		for(Ship ship2 : this.ships){
			if(ship2.getLength() == shipLength){
				return false;
			}
		}

		if(!isVertical)
		{
			if(shipLength <= 4){
				char rightBound = (char)(y+shipLength-1);
				rightBound = Character.toLowerCase(rightBound);
				if(rightBound > 'j'){
					return false;
				}

				for(Ship ship1 : this.ships)
				{
					for(Square s1 : ship1.getOccupiedSquares())
					{
						for(int i = 0; i < shipLength; i++)
						{
							if(Character.toLowerCase(s1.getColumn()) == (char)(y+i) && s1.getRow() == x){
								return false;
							}
						}
					}
				}
				
			}
			//else the length is something other than 2,3,4
			else{
				return false;
			}

		}
		else
		{
			if(shipLength <= 4){
				int lowerBound = x+shipLength-1;
				if(lowerBound > 10){
					return false;
				}
				
				for(Ship ship1 : this.ships)
				{
					for(Square s1 : ship1.getOccupiedSquares())
					{
						for(int i = 0; i < shipLength; i++)
						{
							if(Character.toLowerCase(s1.getColumn()) == y && s1.getRow() == x+i){
								return false;
							}
						}
					}
				}
			
			}
			//else the length is something other than 2,3,4
			else{
				return false;
			}


		}

		//if bounds are okay then

		//lastly check if the squares that will be occupied are in any of
		// ships list of occupied squares

		//all checks have passed, add this ship to ships

		//add implementation for adding occupied squares to field in ship class
		List<Square> shipList = new ArrayList<Square>();

		if(!isVertical){
			for(int i=0; i<shipLength; i++){
				Square s1 = new Square(x, (char)(y+i));
				shipList.add(s1);
			}
		}
		else{
			for(int i=0; i<shipLength; i++){
				Square s1 = new Square(x+i, y);
				shipList.add(s1);
			}
		}
		ship.setOccupiedSquares(shipList);
		ships.add(ship);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		// first check to see if this attack is in bounds
		if (x < 1 || x > 10 || y < 'A' || y > 'J') {
			return new Result(AttackStatus.INVALID, null, new Square(x, y));
		}
		// then check the attacks already made; no duplicate attacks allowed
		for (Result r : attacks) {
			// if this attack is targeted at a square already attacked it is invalid
			if (r.getLocation().getRow() == x && r.getLocation().getColumn() == y) {
				return new Result(AttackStatus.INVALID, null, new Square(x, y));
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
				return new Result(AttackStatus.SURRENDER, null, new Square(x, y));
			}
			// check for sunk
			if (shipHit.getNumHits() == shipHit.getLength()) {
				return new Result(AttackStatus.SUNK, shipHit, new Square(x, y));
			}
			// if the code is still running than it is only a hit
			return new Result(AttackStatus.HIT, shipHit, new Square(x, y));
		}
		// no ship was hit
		return new Result(AttackStatus.MISS, null, new Square(x, y));
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {

		if(ships.size() <= 3) {
			this.ships = ships;
		}
		//function to setShip variable
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}
