package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerResult;

public class TicTacToeGameTest {

	private TicTacToeGame tttg;
	private Connection c1;
	private Connection c2;
	private Player p1;
	private Player p2;

	@Before
	public void setUp() {
		tttg = new TicTacToeGame();
		c1 = mock(Connection.class);
		c2 = mock(Connection.class);

		tttg.addConnection(c1);
		tttg.addConnection(c2);

		p1 = new Player(1, "X", "J1");
		p2 = new Player(2, "O", "J2");
	}

	@Test
	public void testGanador() {
		reset(c1);
		tttg.addPlayer(p1);
		verify(c1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(p1)));

		reset(c1);
		tttg.addPlayer(p2);
		verify(c1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(p2)));
		
		// IGUAL PARA LA C2

		assertTrue(tttg.checkTurn(1));
		tttg.mark(0);
		
		assertTrue(tttg.checkTurn(2));
		tttg.mark(2);
		
		assertTrue(tttg.checkTurn(1));
		tttg.mark(3);
		
		assertTrue(tttg.checkTurn(2));
		tttg.mark(5);
		
		assertTrue(tttg.checkTurn(1));
		tttg.mark(6);
		
		WinnerResult checkWinner = tttg.checkWinner();
		assertTrue(checkWinner.win);
	}

	@Test
	public void testEmpate() {
		reset(c1);
		tttg.addPlayer(p1);
		verify(c1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(p1)));

		reset(c1);
		tttg.addPlayer(p2);
		verify(c1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItem(p2)));

		assertTrue(tttg.checkTurn(1));
		tttg.mark(0);
		
		assertTrue(tttg.checkTurn(2));
		tttg.mark(1);
		
		assertTrue(tttg.checkTurn(1));
		tttg.mark(2);
		
		assertTrue(tttg.checkTurn(2));
		tttg.mark(3);
		
		assertTrue(tttg.checkTurn(1));
		tttg.mark(4);
		
		assertTrue(tttg.checkTurn(2));
		tttg.mark(5);
		
		assertTrue(tttg.checkTurn(1));
		tttg.mark(7);
		
		assertTrue(tttg.checkTurn(2));
		tttg.mark(8);
		
		assertTrue(tttg.checkTurn(1));
		tttg.mark(6);
		
		boolean esEmpate = tttg.checkDraw();
		assertTrue(esEmpate);
	}

}
