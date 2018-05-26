package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Cell;

public class BoardTest {
	
	private static Board board;
	
	private final String X = "X";
	private final String O = "O";
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
	}
	
	@Test
	public void testGanadorJugadorPrimerTurno() {
		board.enableAll();
		
		cambiarCelda(0, X);
		cambiarCelda(1, O);
		cambiarCelda(3, X);
		cambiarCelda(4, O);
		cambiarCelda(6, X);
		
		int[] cellsIfWinner = board.getCellsIfWinner(X);
		
		assertNotNull(cellsIfWinner);
	}
	
	@Test
	public void testGanadorJugadorSegundoTurno() {
		board.enableAll();
		
		cambiarCelda(0, X);
		cambiarCelda(1, O);
		cambiarCelda(2, X);
		cambiarCelda(4, O);
		cambiarCelda(3, X);
		cambiarCelda(7, O);
		
		int[] cellsIfWinner = board.getCellsIfWinner(O);
		
		assertNotNull(cellsIfWinner);
	}
	
	@Test
	public void testEmpate() {
		board.enableAll();
		
		cambiarCelda(0, X);
		cambiarCelda(1, O);
		cambiarCelda(2, X);
		cambiarCelda(3, O);
		cambiarCelda(4, X);
		cambiarCelda(6, O);
		cambiarCelda(5, X);
		cambiarCelda(8, O);
		cambiarCelda(7, X);
		
		boolean isDraw = board.checkDraw();
		
		assertTrue(isDraw);
	}
	
	private void cambiarCelda(int cellId, String value) {
		Cell cell = board.getCell(cellId);
		cell.value = value;
	}
	
}
