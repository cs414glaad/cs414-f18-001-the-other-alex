package edu.colostate.cs.cs414.f18.the_other_alex.server.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece {
	protected static final String GENERAL = "General";
	protected static final String ADVISOR = "Advisor";
	protected static final String ELEPHANT = "Elephant";
	protected static final String CHARIOT = "Chariot";
	protected static final String HORSE = "Horse";
	protected static final String CANNON = "Cannon";
	protected static final String SOLDIER = "Soldier";
	// may be used to help with movement, might decide it's a bad idea
	protected final String NULL = "Null";

	protected static final List<String> generalCanCapture = Arrays.asList(
			GENERAL, ADVISOR, ELEPHANT, CHARIOT, HORSE, CANNON, SOLDIER);
	protected static final List<String> advisorCanCapture = Arrays.asList(
			ADVISOR, ELEPHANT, CHARIOT, HORSE, CANNON, SOLDIER);
	protected static final List<String> elephantCanCapture = Arrays.asList(
			ELEPHANT, CHARIOT, HORSE, CANNON, SOLDIER);
	protected static final List<String> chariotCanCapture = Arrays.asList(
			CHARIOT, HORSE, CANNON, SOLDIER);
	protected static final List<String> horseCanCapture = Arrays.asList(HORSE,
			CANNON, SOLDIER);
	protected static final List<String> soldierCanCapture = Arrays.asList(
			GENERAL, SOLDIER);
	protected static final List<String> cannonCanCapture = Arrays.asList(
			ADVISOR, ELEPHANT, CHARIOT, HORSE, CANNON, SOLDIER);

	protected ArrayList<String> capturableTypes;
	protected boolean isFlipped;
	protected String type;

	public boolean canCapture(Piece piece) {
		if (capturableTypes.contains(piece.getType())) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public boolean isMoveValid() {
		return false;
	}

	public String getType() {
		return type;
	}
}
