package edu.colostate.cs.cs414.f18.the_other_alex.model;

import edu.colostate.cs.cs414.f18.the_other_alex.model.exceptions.InvalidMoveException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.io.Serializable;

public class Game extends Observable implements Observer, Serializable {
  private String gameId;
  private User user1;
  private User user2;
  private User turn;
  private Board board;
  private GameRecord gameRecord;
  private PieceColor user1Color;
  private PieceColor user2Color;
  private Boolean firstMove;
  private static final long serialVersionUID = 7L;
  private GameState gameState;

  //game record start time is set when Game instantiated. Player1 is first to move.
  public Game(User player1, User player2, String id) {
    gameId = id;
    user1 = player1;
    user2 = player2;
    board = new Board();
    gameRecord = new GameRecord(new Date());
    user1Color = PieceColor.NONE;
    user2Color = PieceColor.NONE;
    turn = user1;
    firstMove = true;
    gameState = GameState.IN_PROGRESS;

    user1.addGame(id);
    user2.addGame(id);
  }

  private void writeObject(ObjectOutputStream oos)
          throws IOException {
    oos.defaultWriteObject();
  }

  private void readObject(ObjectInputStream ois)
          throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

  public void gameOver() {
    gameRecord.setGameEndTime(new Date());
    if(isUsersTurn(user1)) {
      gameRecord.setWinnerName(user1.getUsername());
      gameRecord.setLoserName(user2.getUsername());
    }
    else {
      gameRecord.setWinnerName(user2.getUsername());
      gameRecord.setLoserName(user1.getUsername());
    }
    gameState = GameState.OVER;
  }

  public void endTurn() {
    if (isUsersTurn(user1)) {
      turn = user2;
    }
    else {
      turn = user1;
    }
  }

  public GameState makeMove(Cell fromCell, Cell toCell, User user) throws InvalidMoveException {
    if(turn.equals(user)) {
//check for firstMove. If true, set userColors according to flipped piece color. (assumes user1 plays first)
      if (firstMove) {
        board.move(fromCell, toCell);
        setUser1Color(fromCell.getPiece().getColor());
        if (user1Color == PieceColor.BLACK) {
          setUser2Color(PieceColor.RED);
        } else {
          setUser2Color(PieceColor.BLACK);
        }
        firstMove = false;
        endTurn();
        return gameState;
      }
//subsequent moves.
      if (fromCell.getPiece().getColor() == getTurnColor(user)|| fromCell.getPiece().getIsFlipped() == false) { //make sure piece is right color
        board.move(fromCell, toCell);
        if (board.isGameOver(getOpponentColor(user))) {
          gameOver();
          return gameState;
        } else {
          endTurn();
          return gameState;
        }
      }
      else {
        throw new InvalidMoveException("Invalid move: Select a piece of your own color");
      }
    }
    else{
      throw new InvalidMoveException("It is not your turn to move");
    }
  }

  public boolean isUsersTurn(User user) {
    return (turn == user);
  }

  public PieceColor getTurnColor(User user) {
    if(user == user1) {
      return user1Color;
    }
    else {
      return user2Color;
    }
  }

  public PieceColor getOpponentColor(User user) {
    if(user == user1) {
      return user2Color;
    }
    else {
      return user1Color;
    }
  }

  public GameRecord getGameRecord() {
    return gameRecord;
  }

  @Override
  public void update(Observable o, Object arg) {

  }
  public String getGameId() {
    return gameId;
  }

  public PieceColor getUser1Color() {
    return user1Color;
  }

  public PieceColor getUser2Color() {
    return user2Color;
  }

  public User getTurn() {
    return turn;
  }

  public GameState getGameState() {
    return gameState;
  }

  public User getUser1() {
    return user1;
  }

  public User getUser2() {
    return user2;
  }

  public Board getBoard() {
    return board;
  }

  public void setUser1Color(PieceColor color) {
    user1Color = color;
  }

  public void setUser2Color(PieceColor color) {
    user2Color = color;
  }
}
