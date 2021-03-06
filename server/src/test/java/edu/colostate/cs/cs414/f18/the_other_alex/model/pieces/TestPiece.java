package edu.colostate.cs.cs414.f18.the_other_alex.model.pieces;

import edu.colostate.cs.cs414.f18.the_other_alex.model.Cell;
import edu.colostate.cs.cs414.f18.the_other_alex.model.NullPiece;
import edu.colostate.cs.cs414.f18.the_other_alex.model.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class TestPiece {
	private  Soldier testSoldier;
	@Test
	public void testCanSoldierCaptureGeneral(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new NullPiece());
		cells[2][1] = new Cell(2, 1, new General(PieceColor.BLACK));
		cells[2][1].getPiece().flipPiece();
		cells[2][2] = new Cell(2, 2, new Soldier(PieceColor.RED));
		cells[2][2].getPiece().flipPiece();
		assertTrue(cells[2][2].getPiece().isMoveValid(cells[2][1], cells[2][2], cells));
	}

	@Test
	public void testCanCannonCaptureHorseValid(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new Horse(PieceColor.BLACK));
		cells[2][0].getPiece().flipPiece();
		cells[2][1] = new Cell(2, 1, new General(PieceColor.BLACK));
		cells[2][2] = new Cell(2, 2, new Cannon(PieceColor.RED));
		cells[2][2].getPiece().flipPiece();
		assertTrue(cells[2][2].getPiece().isMoveValid(cells[2][0], cells[2][2], cells));
	}

	@Test
	public void testCanCannonCaptureHorseInvalid(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new Horse(PieceColor.BLACK));
		cells[2][0].getPiece().flipPiece();
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new Cannon(PieceColor.RED));
		cells[2][2].getPiece().flipPiece();
		assertFalse(cells[2][2].getPiece().isMoveValid(cells[2][0], cells[2][2], cells));
	}

	@Test
	public void testCannotCaptureDiagonalElephantTakesChariot(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new Elephant(PieceColor.BLACK));
		cells[1][1].getPiece().flipPiece();
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new NullPiece());
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new Chariot(PieceColor.RED));
		cells[2][2].getPiece().flipPiece();
		assertFalse(cells[1][1].getPiece().isMoveValid(cells[2][2], cells[1][1], cells));
	}

	@Test
	public void testCannotTakePieceTwoMovesAwayChariotTakesHorse(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new Horse(PieceColor.BLACK));
		cells[2][0].getPiece().flipPiece();
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new Chariot(PieceColor.RED));
		cells[2][2].getPiece().flipPiece();
		assertFalse(cells[2][2].getPiece().isMoveValid(cells[2][0], cells[2][2], cells));
	}

	@Test
	public void testStandardMoveValidXAxis(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new NullPiece());
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new Advisor(PieceColor.BLACK));
		cells[2][2].getPiece().flipPiece();
		assertTrue(cells[2][2].getPiece().isMoveValid(cells[2][1], cells[2][2], cells));
	}

	@Test
	public void testStandardMoveValidYAxis(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new NullPiece());
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new Advisor(PieceColor.BLACK));
		cells[2][2].getPiece().flipPiece();
		assertTrue(cells[2][2].getPiece().isMoveValid(cells[1][2], cells[2][2], cells));
	}

	@Test
	public void canSoldierCaptureAdvisorInvalid(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new Soldier(PieceColor.BLACK));
		cells[0][0].getPiece().flipPiece();
		cells[0][1] = new Cell(0, 1, new Advisor(PieceColor.RED));
		cells[0][1].getPiece().flipPiece();
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new NullPiece());
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new NullPiece());
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new NullPiece());
		assertFalse(cells[0][0].getPiece().isMoveValid(cells[0][1], cells[0][0], cells));
	}

	@Test
	public void canHorseCaptureElephantInvalid(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new Horse(PieceColor.BLACK));
		cells[1][0].getPiece().flipPiece();
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new Elephant(PieceColor.RED));
		cells[2][0].getPiece().flipPiece();
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new NullPiece());
		assertFalse(cells[1][0].getPiece().isMoveValid(cells[2][0], cells[1][0], cells));
	}

	@Test
	public void canElephantCaptureHorseValid(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new Horse(PieceColor.BLACK));
		cells[1][0].getPiece().flipPiece();
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new Elephant(PieceColor.RED));
		cells[2][0].getPiece().flipPiece();
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new NullPiece());
		assertTrue(cells[2][0].getPiece().isMoveValid(cells[1][0], cells[2][0], cells));
	}

	@Test
	public void canGeneralCaptureGeneralValid(){
		Cell[][] cells = new Cell[3][3];
		cells[0][0] = new Cell(0, 0, new NullPiece());
		cells[0][1] = new Cell(0, 1, new NullPiece());
		cells[0][2] = new Cell(0, 2, new NullPiece());
		cells[1][0] = new Cell(1, 0, new General(PieceColor.BLACK));
		cells[1][0].getPiece().flipPiece();
		cells[1][1] = new Cell(1, 1, new NullPiece());
		cells[1][2] = new Cell(1, 2, new NullPiece());
		cells[2][0] = new Cell(2, 0, new General(PieceColor.RED));
		cells[2][0].getPiece().flipPiece();
		cells[2][1] = new Cell(2, 1, new NullPiece());
		cells[2][2] = new Cell(2, 2, new NullPiece());
		assertTrue(cells[2][0].getPiece().isMoveValid(cells[1][0], cells[2][0], cells));
	}

	@BeforeEach
	public void setUp(){
		testSoldier = new Soldier(PieceColor.BLACK);

	}
	@Test
	public void testSoldierConstructorisFlipped(){
		assertTrue(testSoldier.getIsFlipped() == false);
	}
	@Test
	public void testSoldierConstructorType(){
		assertTrue(testSoldier.getType().equals("Soldier"));
	}
	@Test
	public void testSoldierConstructorColor(){
		assertTrue(testSoldier.getColor() == PieceColor.BLACK);
	}

	@Test
	public void testSameTeamCapture(){
		General testGeneral = new General(PieceColor.BLACK);
		Cell[][] cells = new Cell[2][1];
		cells[0][0] = new Cell(0,0, testGeneral);
		cells[0][0].getPiece().flipPiece();
		cells[1][0] = new Cell(1,0, testSoldier);
		cells[1][0].getPiece().flipPiece();
		assertTrue(cells[1][0].getPiece().isMoveValid(cells[1][0],cells[0][0],cells) == false);
	}

	@Test
	public void testCanMoveNullPiece(){
		NullPiece np = new NullPiece();
		Cell[][] cells = new Cell[2][1];

		cells[0][0] = new Cell(0,0, np);
		cells[1][0] = new Cell(0,1, testSoldier);

		assertTrue(cells[0][0].getPiece().isMoveValid(cells[1][0],cells[0][0], cells) == false);
	}

	@Test
	public void testCanCannonMove(){
		Cannon cannon = new Cannon(PieceColor.RED);
		Cell[][] cells = new Cell[2][1];
		cells[0][0] = new Cell(0,0,cannon);
		cells[0][0].getPiece().flipPiece();
		cells[1][0] = new Cell(1,0,new NullPiece());
		assertTrue(cells[0][0].getPiece().isMoveValid(cells[1][0],cells[0][0],cells));
	}

}
