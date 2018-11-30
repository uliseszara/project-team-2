package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Result
{
	@JsonProperty private List<Ship> ships;
	@JsonProperty private Square square;
	@JsonProperty private AttackStatus attackStatus;

	public AttackStatus getResult()
	{
		return this.attackStatus;
	}

	public Result() {
		ships = new ArrayList<Ship>();
	}

	public Result(AttackStatus result, List<Ship> ships, Square square)
	{
		this.ships = ships;
		this.square = square;
		this.attackStatus = result;
	}
	public void setResult(AttackStatus result) {
		this.attackStatus = result;
	}

	public List<Ship> getShips() {
		return this.ships;
	}
	public void setShip(List<Ship> ships) { this.ships = ships; }
	public void addShip(Ship ship) { ships.add(ship); }

	public Square getLocation() {
		return this.square;
	}

	public void setLocation(Square square) {
		this.square = square;
	}
}
