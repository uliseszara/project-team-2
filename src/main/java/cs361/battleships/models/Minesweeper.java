package cs361.battleships.models;

public class Minesweeper extends Ship {
    public Minesweeper() {
        super();
        length = 2;
        kind = "minesweeper";
    }

    @Override
    public boolean hit(int x, char y) {
        if (x == captainsQuartersX && y == captainsQuartersY) {
            sunk = true;
            return sunk;
        }
        return false;
    }
}