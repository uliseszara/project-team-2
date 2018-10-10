package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ship {
	private
		String type, orientation;
		int length;
		int xCord;
		int yCord;

	@JsonProperty private List<Square> occupiedSquares;

	public int getLength(){
		return length;
	}
	public int getxCord(){
		return xCord;
	}
	public int getyCord(){
		return yCord;
	}


	
	public Ship(String kind) {
		type = kind;
		if(kind == )
	}

	public List<Square> getOccupiedSquares() {
		//TODO implement
		return null;
	}
}
