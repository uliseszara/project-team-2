package cs361.battleships.models;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="@class")

public class Ship {
	@JsonProperty protected boolean sunk;
	@JsonProperty protected int captainsQuartersX;
	@JsonProperty protected char captainsQuartersY;
	@JsonProperty protected boolean captainHit;
	@JsonProperty protected int length;
	@JsonProperty protected String kind;
	@JsonProperty protected boolean vert;
	@JsonProperty protected boolean submerged;

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
		submerged = false;
	}

	public boolean hit(int x, char y) {
    	if (x == captainsQuartersX && y == captainsQuartersY) {
    		captainHit = !captainHit;
    		sunk = !captainHit;
    		return sunk;
		}
		return false;
	}

	public void setSubmerged(boolean x) { submerged = x; }

	public boolean getSubmerged() { return submerged; }
}
