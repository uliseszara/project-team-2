package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Result> attacks; // list of squares on this board that have been attacked
	private List<Ship> ships; // list of ships on this board (cannot be more than 3)
	private Square[][] squares; //2d array of squares to represent the board

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		attacks = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
		squares = new Square[10][10];
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		y = Character.toUpperCase(y);
		//check number of ships already on board
		if(ships.size() == 3)
		{
			return false;
		}

		//perform bounds check to see if x is between 1-10
		if(x < 1 || x > 10){
			return false;
		}

		//perform bounds check to see if y is between A-J
		if(y < 'A' || y > 'J'){
			return false;
		}

		//hold the ship arg. length
		int shipLength = ship.getLength();

		//check if the ship arg. is the same size as any of the ships already on the board
		for(Ship ship2 : this.ships){
			//if it is, do not place the ship
			if(ship2.getLength() == shipLength){
				return false;
			}
		}

		//horizontal ship placement
		if(!isVertical)
		{
			//check to make sure ship is of correct length
			if(shipLength <= 4){
				//identify right bound of ship
				char rightBound = (char)(y+shipLength-1);
				rightBound = Character.toUpperCase(rightBound);
				//check if right bound is on board
				if(rightBound > 'J'){
					//if not, do not place ship
					return false;
				}

				//check if the ship will be placed over sqaures that are already occupied
				//loop through each ship already on board
				for(Ship ship1 : this.ships)
				{
					//loop through every square that a ship occupies
					for(Square s1 : ship1.getOccupiedSquares())
					{
						//loop through the square that new ship will occupy
						for(int i = 0; i < shipLength; i++)
						{
							//if any of the square that new ship will occupy are already occupied
							if(Character.toUpperCase(s1.getColumn()) == (char)(y+i) && s1.getRow() == x){
								//do not place ship on board
								return false;
							}
						}
					}
				}
				
			}
			//else the shipLength is something other than 2,3,4
			else{
				return false;
			}

		}

		//vertical case
		else
		{
			//check to make sure ship is of correct length
			if(shipLength <= 4){
				//identify lower bound of ship
				int lowerBound = x+shipLength-1;
				//check if lower bound is on board
				if(lowerBound > 10){
					return false;
				}

				//check if the ship will be placed over sqaures that are already occupied
				//loop through each ship already on board
				for(Ship ship1 : this.ships)
				{
					//loop through every square that a ship occupies
					for(Square s1 : ship1.getOccupiedSquares())
					{
						//loop through squares that new ship will occupy
						for(int i = 0; i < shipLength; i++)
						{
							//do not place ship on board
							if(Character.toUpperCase(s1.getColumn()) == y && s1.getRow() == x+i){
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

		//bounds have been checked and are okay

		//add the squares that the new ship will occupy to the ship
		List<Square> shipList = new ArrayList<Square>();

		if(!isVertical){
			for(int i=0; i<shipLength; i++){
				Square s1 = new Square(x, Character.toUpperCase((char)(y+i)));
				shipList.add(s1);
			}
		}
		else{
			for(int i=0; i<shipLength; i++){
				Square s1 = new Square(x+i, y);
				shipList.add(s1);
			}
		}

		//add the squares to the ship object
		ship.setOccupiedSquares(shipList);
		//add the ship to the board class
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
		// next check to see if the attack hits a ship. Initialize tools to help us later
		Result thisResult;
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
				thisResult = new Result(AttackStatus.SURRENDER, null, new Square(x, y));
			}
			// check for sunk
			else if (shipHit.getNumHits() == shipHit.getLength()) {
				thisResult =  new Result(AttackStatus.SUNK, shipHit, new Square(x, y));
			}
			// if the code is still running than it is only a hit
			else {
				thisResult = new Result(AttackStatus.HIT, shipHit, new Square(x, y));
			}
		}
		// no ship was hit
		else {
			thisResult = new Result(AttackStatus.MISS, null, new Square(x, y));
		}
		// now that the result is complete, add it to the previous attacks and return it
		attacks.add(thisResult);
		return thisResult;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {


		//check if the number of ships passed will fit on board
		if(ships.size() <= 3) {
			//loop through all ships except last
			for (int i = 0; i < ships.size() - 1; i++) {
				//loop through the next ship including the last
				for (int j = i + 1; j < ships.size(); j++) {
					//if any of the ships are the same size, do not set ships var to arg.
					if (ships.get(i).getLength() == ships.get(j).getLength()) {
						return;
					}
				}
			}
		}

		//checks have been made, set ships var to arg.
		this.ships = ships;
		
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}
