package cs361.battleships.models;

import java.util.List;
import java.util.ArrayList;

public class Ship {
	protected int captainsQuartersX;
	protected char captainsQuartersY;
	protected boolean captainHit;
	protected boolean sunk;
	protected int length;

	public int getCaptainsQuartersX() { return captainsQuartersX; }
	public void setCaptainsQuartersX(int captainsQuartersX) { this.captainsQuartersX = captainsQuartersX; }

	public char getCaptainsQuartersY() { return captainsQuartersY; }
	public void setCaptainsQuartersY(char captainsQuartersY) {this.captainsQuartersY = captainsQuartersY; }

	public boolean getSunk() { return sunk; }

	public int getLength() { return length; }

	public Ship() {
		captainHit = false;
		sunk = false;
	}

	public boolean hit(int x, char y) {
    	if (x == captainsQuartersX && y == captainsQuartersY) {
    		captainHit = !captainHit;
    		sunk = !captainHit;
    		return !captainHit;
		}
		return false;
	}
}
