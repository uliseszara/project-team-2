package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Square {

	@JsonProperty private int row;
	@JsonProperty private char column;
	@JsonProperty private boolean occupied;
	@JsonProperty private List<Ship> ships;

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.occupied = false;
		ships = new ArrayList<Ship>();
	}
	public Square(){ ships = new ArrayList<Ship>(); }

	public char getColumn() { return column; }
	public void setColumn(char column) { this.column = column; }
	
	public int getRow() { return row; }
	public void setRow(int row) { this.row = row; }

	public boolean getOccupied() { return occupied; }
	public void setOccupied(boolean x) { this.occupied = x; }

	public List<Ship> getShips(){ return ships; }
	public void setShips(List<Ship> s1) { ships = s1; }
	public void addShip(Ship s1) {
		ships.add(s1);
		occupied = true;
	}
	public void removeShip(Ship s1) {
		for (int i = ships.size() - 1; i >= 0; i--) {
			if (ships.get(i).isEqual(s1)) {
                ships.remove(i);
                break;
            }
		}
		if (ships.size() < 1)
			occupied = false;
	}
}
