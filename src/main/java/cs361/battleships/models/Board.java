package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	//variable to hold the current ships on the board, cannot be more than 3
	private List<Ship> ships;
	private List<Result> attacks;
	//private List<Result> boardResults;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
		attacks = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
		//initialize ships to be 0

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
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {

		if(ships.size() <= 3) {
			for (int i = 0; i < ships.size() - 1; i++) {
				for (int j = i + 1; j < ships.size(); j++) {
					if (ships.get(i).getLength() == ships.get(j).getLength()) {
						return;
					}
				}
			}
		}

		this.ships = ships;
		
		//function to setShip variable
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}
