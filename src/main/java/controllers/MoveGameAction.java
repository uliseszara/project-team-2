package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class MoveGameAction {

    @JsonProperty private Game game;
    @JsonProperty String direction;

    public Game getGame() {
        return game;
    }

    public String getDirection() {
        return direction;
    }
}
