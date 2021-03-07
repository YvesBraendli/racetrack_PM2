package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
	private Game _testGame;
	
	@BeforeEach
	private void setup() {
		_testGame = new Game();
	}
	
	@Test
	public void GetWinner_GameIsInProgress_ReturnsNoWinner() {
		// Arrange
				
		// Act
		int result = _testGame.getWinner();
		
		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

}
