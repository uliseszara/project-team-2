package cs361.battleships.models;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ship {
	@JsonProperty protected boolean sunk;
	protected int captainsQuartersX;
	protected char captainsQuartersY;
	protected boolean captainHit;
	protected int length;
	protected String kind;
	protected boolean vert;

	public int getCaptainsQuartersX() { return captainsQuartersX; }
	public void setCaptainsQuartersX(int captainsQuartersX) { this.captainsQuartersX = captainsQuartersX; }

	public char getCaptainsQuartersY() { return captainsQuartersY; }
	public void setCaptainsQuartersY(char captainsQuartersY) {this.captainsQuartersY = captainsQuartersY; }

	public boolean getSunk() { return sunk; }
	public void setSunk(boolean sunk) { this.sunk = sunk; }

	public boolean getCaptainHit() { return captainHit; }

	public String getKind() { return kind; }

	public int getLength() { return length; }

	public boolean getVert() { return vert; }
	public void setVert(boolean vert) { this.vert = vert; }

	public Ship() {
		captainHit = false;
		sunk = false;
		vert = true;
	}

	public boolean hit(int x, char y) {
    	if (x == captainsQuartersX && y == captainsQuartersY) {
    		captainHit = !captainHit;
    		sunk = !captainHit;
    		return sunk;
		}
		return false;
	}
}
