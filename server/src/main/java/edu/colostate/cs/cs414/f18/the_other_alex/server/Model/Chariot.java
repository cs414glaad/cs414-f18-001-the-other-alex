package edu.colostate.cs.cs414.f18.the_other_alex.server.Model;

import java.util.ArrayList;

public class Chariot extends Piece {
	
	public Chariot()
	{
		type = CHARIOT;
		capturableTypes = new ArrayList<String>(chariotCanCapture);
	}
}
