package cs361.battleships.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty private List<Result> attacks; // list of squares on this board that have been attacked
	@JsonProperty private List<Ship> ships; // list of ships on this board (cannot be more than 3)

	@JsonProperty private Square[][] squares; //2d array of squares to represent the board

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		attacks = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
		squares = new Square[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				squares[i][j] = new Square(i,(char)(j+'A'));
			}
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		y = Character.toUpperCase(y);

		//perform bounds check
		if(x < 0 || x > 9 || y < 'A' || y > 'J'){
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
				if (squares[x+i][y-'A'].getOccupied())
					return false;
			}
		}

		// occupy squares and set captain's quarters
		if(!isVertical){
			ship.setVert(false);
			for(int i=0; i<shipLength; i++){
				squares[x][y+i-'A'].addShip(ship);
				ship.addSquare(new Square(squares[x][y+i-'A'].getRow(), squares[x][y+i-'A'].getColumn()));
				if (i == shipLength - 2) {
					ship.setCaptainsQuartersX(x);
					ship.setCaptainsQuartersY((char)(y+i));
				}
			}
		}
		else{
			for(int i=0; i<shipLength; i++){
				squares[x+i][y-'A'].addShip(ship);
				ship.addSquare(new Square(squares[x+i][y-'A'].getRow(), squares[x+i][y-'A'].getColumn()));
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
		if (x < 0 || x > 9 || y < 'A' || y > 'J')
		{
			return new Result(AttackStatus.INVALID, null, new Square(x, y));
		}
		// then to see if it's a duplicate attack (illegal)
		for (Result attack : attacks) {
			if (attack.getResult() == AttackStatus.HIT && x == attack.getLocation().getRow() && y == attack.getLocation().getColumn())
				return new Result(AttackStatus.INVALID, null, new Square(x, y));
		}
		// now handle the attack
		Result res;
		if(squares[x][y - 'A'].getOccupied() == false)
		{
			res = new Result(AttackStatus.MISS, null, new Square(x, y));
		}
		else
		{
			Ship ship = squares[x][y - 'A'].getShips().get(0);
			boolean hitRes = ship.hit(x,y);

			boolean surrender = true;

			if (!hitRes)
			{
				if (ship.getCaptainsQuartersX() == x && ship.getCaptainsQuartersY() == y)
				{
					res = new Result(AttackStatus.MISS, ship, squares[x][y - 'A']);
				}
				else
				{
					res = new Result(AttackStatus.HIT, ship, squares[x][y - 'A']);
				}
			}
			else
			{
				for (Ship s : ships) {
					if (s.getLength() == ship.getLength()) {
						s.setSunk(true);
					}
				}
				for (int i = 0; i < ship.getLength(); i++) {
					if (i != 1) {
						if (ship.getVert())
							attack(x + 1 - i, y);
						else
							attack(x, (char) (y + 1 - i));
					}
				}
				for (Ship s : ships)
				{
					if (!s.getSunk())
					{
						surrender = false;
					}
				}
				if (surrender)
				{
					res = new Result(AttackStatus.SURRENDER, ship, squares[x][y - 'A']);
				}
				else
				{
					res = new Result(AttackStatus.SUNK, ship, squares[x][y - 'A']);
				}
			}
		}
		attacks.add(res);
		return res;
	}

	public void moveFleet(String direction) {
		System.out.println("Successfully called moveFleet with direction " + direction);

		List<Ship> cantMove = new ArrayList<>();

		for (Ship ship : ships) {
			if (ship.getSunk()) {
				cantMove.add(ship);
			}
		}

		int rowModifier = 0;
		int colModifier = 0;
		if (direction.equals("up")) {
			for (int i = 0; i < 10; i++) {
				if (squares[0][i].getOccupied()) {
					for (Ship ship : squares[0][i].getShips()) {
						boolean dupe = false;
						for (Ship ship2 : cantMove) {
							if (ship.isEqual(ship2))
								dupe = true;
						}
						if (!dupe)
							cantMove.add(ship);
					}
				}
			}
			rowModifier = 1;
		}
		else if (direction.equals("right")) {
			for (int i = 0; i < 10; i++) {
				if (squares[i][9].getOccupied()) {
					for (Ship ship : squares[i][9].getShips()) {
						boolean dupe = false;
						for (Ship ship2 : cantMove) {
							if (ship.isEqual(ship2))
								dupe = true;
						}
						if (!dupe)
							cantMove.add(ship);
					}
				}
			}
			colModifier = -1;
		}
		else if (direction.equals("down")) {
			for (int i = 0; i < 10; i++) {
				if (squares[9][i].getOccupied()) {
					for (Ship ship : squares[9][i].getShips()) {
						boolean dupe = false;
						for (Ship ship2 : cantMove) {
							if (ship.isEqual(ship2))
								dupe = true;
						}
						if (!dupe)
							cantMove.add(ship);
					}
				}
			}
			rowModifier = -1;
		}
		else {
			for (int i = 0; i < 10; i++) {
				if (squares[i][0].getOccupied()) {
					for (Ship ship : squares[i][0].getShips()) {
						boolean dupe = false;
						for (Ship ship2 : cantMove) {
							if (ship.isEqual(ship2))
								dupe = true;
						}
						if (!dupe)
							cantMove.add(ship);
					}
				}
			}
			colModifier = 1;
		}

		System.out.println(cantMove);

		for (int i = 0; i < cantMove.size(); i++) {
			for (Square square : cantMove.get(i).getOccupiedSquares()) {
				if (squares[square.getRow() + rowModifier][square.getColumn()-'A'+colModifier].getOccupied()) {
					for (Ship ship : squares[square.getRow() + rowModifier][square.getColumn() - 'A'+ colModifier].getShips()) {
						boolean dupe = false;
						for (Ship ship2 : cantMove) {
							if (ship.isEqual(ship2))
								dupe = true;
						}
						if (!dupe)
							cantMove.add(ship);
					}
				}
			}
		}
		for (Ship ship : ships) {
			boolean canMove = true;
			for (Ship ship2 : cantMove) {
				if (ship.isEqual(ship2)) {
					canMove = false;
				}
			}
			if (canMove) {
				for (int i = 0; i < ship.getOccupiedSquares().size(); i++) {
					squares[ship.getOccupiedSquares().get(i).getRow()][ship.getOccupiedSquares().get(i).getColumn()-'A'].removeShip(ship);
					ship.getOccupiedSquares().get(i).setRow(ship.getOccupiedSquares().get(i).getRow() - rowModifier);
					ship.getOccupiedSquares().get(i).setColumn((char)(ship.getOccupiedSquares().get(i).getColumn() - colModifier));
					squares[ship.getOccupiedSquares().get(i).getRow()][ship.getOccupiedSquares().get(i).getColumn()-'A'].addShip(ship);

				}
			}
		}
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

	public Square[][] getSquares() { return squares; }
}

