package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.lang.System.exit;

public class Ship {
	private String kind;
	private	int length;
	private	int numHits;
	@JsonProperty private List<Square> occupiedSquares;

	public String getKind(){ return this.kind; }
	public int getLength(){ return this.length; }
	public int getNumHits(){return this.numHits;}
    public List<Square> getOccupiedSquares() { return this.occupiedSquares; }

    public void setKind(String k){ this.kind = k; }
    public void setNumHits(int n){ this.numHits = n;}
	public void setOccupiedSquares(List<Square> list1){ this.occupiedSquares = list1; }

	public void incNumHits(){ this.numHits++; }

	
	public Ship(String k) {
		k = k.toLowerCase();

		this.kind = k;
		this.numHits = 0;
		this.occupiedSquares = null;

		if((k.equals("minesweeper")) || (k.equals("m"))){ this.length = 2; }
		else if((k.equals("destroyer")) || (k.equals("d"))){ this.length = 3; }
        else if((k.equals("battleship"))|| (k.equals("b"))){ this.length = 4; }
        else{
            System.out.print("Invalid ship argument");
            exit(0);
        }

	}
}
