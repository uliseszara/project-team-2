package cs361.battleships.models;

public class Minesweeper extends Ship {
    public Minesweeper() {
        super();
        length = 2;
    }

    @Override
    public boolean hit(int x, char y) {
        if (x == captainsQuartersX && y == captainsQuartersY) {
            sunk = true;
            return true;
        }
        return false;
    }
}