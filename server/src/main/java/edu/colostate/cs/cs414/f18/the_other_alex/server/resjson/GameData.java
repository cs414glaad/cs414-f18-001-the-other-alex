package edu.colostate.cs.cs414.f18.the_other_alex.server.resjson;

import edu.colostate.cs.cs414.f18.the_other_alex.model.Game;
import edu.colostate.cs.cs414.f18.the_other_alex.server.DataType;

public class GameData extends DataType {
  public GameData(Game game) {

  }
  public String blackUser;
  public String redUser;
  public String turn;
  public String gameState;
}
