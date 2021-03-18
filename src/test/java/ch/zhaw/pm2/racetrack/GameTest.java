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

	@Test
	public void GetWinner_CarCrossesFinishLineCorrectly_ReturnsWinner() {
		// Arrange
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);		
			
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == 0);		
	}

	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyAndCrashesAfterwards_ReturnsWinner() {}
	
	@Test
	public void GetWinner_CarCrossesFinishLineBackwards_ReturnsNoWinner() {}
	
	@Test
	public void GetWinner_CarCrossesFinishLineBackwardsAndForwardAfterwards_ReturnsNoWinner() {}
	
	@Test
	public void GetWinner_TwoCarsFromTwoAreAlive_ReturnsNoWinner() {}
	
	@Test
	public void GetWinner_TwoCarsFromFourAreAlive_ReturnsNoWinner() {}
	
	
	
	@Test
	public void calculatePath_BottomLeftToTopRightPath() {
		// Arrange
		
		// Act
		
		
		// Assert
		
	}
	
	@Test
	public void calculatePath_VerticalLineUpPath() {}
	
	@Test
	public void calculatePath_VerticalLineDownPath() {}
	
	@Test
	public void calculatePath_BottomRightToTopLeftPath() {}
	
	@Test
	public void calculatePath_TopRightToBottomLeftPath() {}
	
	@Test
	public void calculatePath_TopLeftToBottomRightPath() {}
	
	@Test
	public void calculatePath_HorizontalLineLeftToRightPath() {}
	
	@Test
	public void calculatePath_HorizontalLineRightToLeftPath() {}
	
}
