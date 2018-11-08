package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Result> attacks; // list of squares on this board that have been attacked
	private List<Ship> ships; // list of ships on this board (cannot be more than 3)

	private int sonarsLeft;
	private Square[][] squares; //2d array of squares to represent the board

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		attacks = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
		squares = new Square[10][10];
		this.sonarsLeft = 2;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				squares[i][j] = new Square();
			}
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		y = Character.toUpperCase(y);

		//perform bounds check
		if(x < 1 || x > 10 || y < 'A' || y > 'J'){
			return false;
		}

		// make sure this isn't a duplicate
		int shipLength = ship.getLength();
		for(Ship ship2 : this.ships){
			if(ship2.getLength() == shipLength){
				return false;
			}
		}

		// horizontal ship placement
		if(!isVertical)
		{
			// check if entire ship is on board
			char rightBound = (char)(y+shipLength-1);
			if(rightBound > 'J'){
				return false;
			}

			//check if the ship will be placed over squares that are already occupied
			for (int i = 0; i < shipLength; i++) {
				if (squares[x][y+i-'A'].getOccupied())
					return false;
			}
		}

		// vertical ship placement
		else
		{
			// check if entire ship is on board
			int lowerBound = x + shipLength - 1;
			if(lowerBound > 10){
				return false;
			}

			//check if the ship will be placed over squares that are already occupied
			for (int i = 0; i < shipLength; i++) {
				if (squares[x+i][y+0-'A'].getOccupied())
					return false;
			}
		}

		// occupy squares and set captain's quarters
		if(!isVertical){
			for(int i=0; i<shipLength; i++){
				squares[x][y+i-'A'].setOccupied(true);
				squares[x][y+i-'A'].setShip(ship);
				if (i == shipLength - 2) {
					ship.setCaptainsQuartersX(x);
					ship.setCaptainsQuartersY((char)(y+i));
				}
			}
		}
		else{
			for(int i=0; i<shipLength; i++){
				squares[x+i][y+0-'A'].setOccupied(true);
				squares[x+i][y+0-'A'].setShip(ship);
				if (i == shipLength - 2) {
					ship.setCaptainsQuartersX(x+i);
					ship.setCaptainsQuartersY(y);
				}
			}
		}

		//add the ship to the board
		ships.add(ship);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y)
	{
		// first check to see if this attack is in bounds
		if (x < 1 || x > 10 || y < 'A' || y > 'J')
		{
			return new Result(AttackStatus.INVALID, null, new Square(x, y));
		}
		if(squares[x][y - 'A'].getOccupied() == false)
		{
			return new Result(AttackStatus.MISS, null, new Square(x, y));
		}
		else
		{
			boolean hitRes = squares[x][y - 'A'].getShip().hit(x,y);
			boolean surrender = true;

			if (!hitRes)
			{
				if (squares[x][y - 'A'].getShip().getCaptainsQuartersX() == x && squares[x][y - 'A'].getShip().getCaptainsQuartersY() == y)
				{
					return new Result(AttackStatus.MISS, null, new Square(x, y));
				}
				else
				{
					return new Result(AttackStatus.HIT, null, new Square(x, y));
				}
			}
			else
			{
				if (squares[x][y - 'A'].getShip().getSunk() == true)
				{
					for (Ship ships : ships)
					{
						if (ships.getSunk() == false)
						{
							surrender = false;
						}
					}
					if (surrender)
					{
						return new Result(AttackStatus.SURRENDER, null, new Square(x, y));
					}
					else
					{
						return new Result(AttackStatus.SUNK, null, new Square(x, y));
					}
				}
			}
		}
//		// then check the attacks already made; no duplicate attacks allowed
//		for (Result r : attacks) {
//			// if this attack is targeted at a square already attacked it is invalid
//			if (r.getLocation().getRow() == x && r.getLocation().getColumn() == y) {
//				return new Result(AttackStatus.INVALID, null, new Square(x, y));
//			}
//		}
		/*
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
		return thisResult;*/
		return null;
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

	public int getSonarsLeft(){ return sonarsLeft; }

	public void decSonarsLeft(int num){ this.sonarsLeft--; }

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}

