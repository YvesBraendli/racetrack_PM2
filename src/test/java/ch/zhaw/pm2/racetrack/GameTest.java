package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
	private Game _testGame;
	
	@BeforeEach
	private void setup() {
		//_testGame = new Game();
	}
	
	//
	// todo winner testing
	//	. kein gewinner (nie �ber zielliie) und . alle cars leben
	//											. 2/4 cars leben
	//											. 2 cars leben noch
	//											. r�ck w�rts �ber ziellinie
	//											. r�ckw�rts�ber ziellinie und anschliessend wieder vorw�rts
	//  . gewinner gibt es : 
	//											. �ber ziellinie ein car und leben alle
	//											. �ber ziellinie und crashed gleich darauf hin
		
	@Test
	public void GetWinner_GameIsInProgress_ReturnsNoWinner() {
		// Arrange
				
		// Act
		int result = _testGame.getWinner();
		
		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

}
