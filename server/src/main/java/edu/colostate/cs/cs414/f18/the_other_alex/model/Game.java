package edu.colostate.cs.cs414.f18.the_other_alex.model;

import edu.colostate.cs.cs414.f18.the_other_alex.model.controllers.GameObserver;

public class Game {
  private User blackUser;
  private User reduser;
  private Turn turn;
  private Board board;
  private GameRecord gameRecord;

  public void gameOver() {

  }

  public Board getBoard() {
    return null;
  }

  public void endTurn() {

  }

  public GameState makeMove(Cell fromCell, Cell toCell, User user) {
    return null;
  }

  public boolean isUsersTurn(User user) {
    return false;
  }

  public void attach(GameObserver o) {

  }

  public void detach(GameObserver o) {

  }

  public void notifyObservers() {

  }

  public Board getBorad() {
    return board;
  }

  public GameRecord getGameRecord() {
    return gameRecord;
  }
}
