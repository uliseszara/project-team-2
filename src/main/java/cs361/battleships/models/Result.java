package cs361.battleships.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Result
{
	@JsonProperty private List<Ship> ships;
	@JsonProperty private Ship ship;
	@JsonProperty private Square square;
	@JsonProperty private AttackStatus attackStatus;

	public AttackStatus getResult()
	{
		return this.attackStatus;
	}

	public Result() {
		ships = new ArrayList<Ship>();
	}

	public Result(AttackStatus result, Ship ship, Square square)
	{
		this.ship = ship;
		this.square = square;
		this.attackStatus = result;
	}
	public void setResult(AttackStatus result) {
		this.attackStatus = result;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return this.square;
	}

	public void setLocation(Square square) {
		this.square = square;
	}
}
