package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;

public class GameTest {
	private Game _testGame;
	
	@BeforeEach
	private void setup() {
		File file = new File("tracks\\challenge.txt");
		Track track = null;
		try {
			track = new Track(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTrackFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_testGame  = new Game(2, track);
	}
	
	//
	// todo winner testing
	//	. kein gewinner (nie über zielliie) und . alle cars leben
	//											. 2/4 cars leben
	//											. 2 cars leben noch
	//											. rück wärts über ziellinie
	//											. rückwärtsüber ziellinie und anschliessend wieder vorwärts
	//  . gewinner gibt es : 
	//											. über ziellinie ein car und leben alle
	//											. über ziellinie und crashed gleich darauf hin
		
	@Test
	public void GetWinner_GameIsInProgress_ReturnsNoWinner() {
		// Arrange
		
		// Act
		int result = _testGame.getWinner();
		
		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

	@Test
	public void GetWinner_CarCrossesFinishLineCorrectly_ReturnsWinner() {
		// Arrange
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);		
		
		int nowinner = _testGame.getWinner();
		
		assertTrue(nowinner == Game.NO_WINNER);
		
		_testGame.doCarTurn(Direction.RIGHT);
		int currentWinner = _testGame.getWinner();
		
		assertTrue(currentWinner == 0);
		
	}
}
