package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
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

	public Square(){ships = new ArrayList<Ship>();}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public boolean getOccupied() { return occupied; }

	public void setOccupied(boolean x) { this.occupied = x; }

	public void setShips(List<Ship> s1) { this.ships = s1; }

	public void addShip(Ship s1) { this.ships.add(s1); }

	public List<Ship> getShips() { return this.ships; }
}
