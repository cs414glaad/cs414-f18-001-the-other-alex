package edu.colostate.cs.cs414.f18.the_other_alex.server.Model;

import java.util.ArrayList;

public class Elephant extends Piece {
	
	public Elephant()
	{
		type = ELEPHANT;
		capturableTypes = new ArrayList<String>(elephantCanCapture);
	}
}
