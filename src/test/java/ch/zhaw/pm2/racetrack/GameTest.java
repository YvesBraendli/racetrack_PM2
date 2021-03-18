package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;

public class GameTest {
	private Game _testGame;
	
	@BeforeEach
	private void setup() {
		File file = new File("tracks/challenge.txt");
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
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(2,4);
		PositionVector endPosition = new PositionVector(4,1);
		expectedList.add(new PositionVector(2,4));
		expectedList.add(new PositionVector(3,3));
		expectedList.add(new PositionVector(3,2));
		expectedList.add(new PositionVector(4,1));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
	@Test
	public void calculatePath_VerticalLineUpPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(2,4);
		PositionVector endPosition = new PositionVector(2,1);
		expectedList.add(new PositionVector(2,4));
		expectedList.add(new PositionVector(2,3));
		expectedList.add(new PositionVector(2,2));
		expectedList.add(new PositionVector(2,1));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
	@Test
	public void calculatePath_VerticalLineDownPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(2,1);
		PositionVector endPosition = new PositionVector(2,4);
		expectedList.add(new PositionVector(2,1));
		expectedList.add(new PositionVector(2,2));
		expectedList.add(new PositionVector(2,3));
		expectedList.add(new PositionVector(2,4));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);		
	}
	
	@Test
	public void calculatePath_BottomRightToTopLeftPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(6,4);
		PositionVector endPosition = new PositionVector(1,1);
		expectedList.add(new PositionVector(6,4));
		expectedList.add(new PositionVector(5,3));
		expectedList.add(new PositionVector(4,3));
		expectedList.add(new PositionVector(3,2));
		expectedList.add(new PositionVector(2,2));
		expectedList.add(new PositionVector(1,1));
				
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
	@Test
	public void calculatePath_TopRightToBottomLeftPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(4,1);
		PositionVector endPosition = new PositionVector(2,4);
		expectedList.add(new PositionVector(4,1));
		expectedList.add(new PositionVector(3,2));
		expectedList.add(new PositionVector(3,3));
		expectedList.add(new PositionVector(2,4));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
	@Test
	public void calculatePath_TopLeftToBottomRightPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(1,1);
		PositionVector endPosition = new PositionVector(6,4);
		expectedList.add(new PositionVector(1,1));
		expectedList.add(new PositionVector(2,2));
		expectedList.add(new PositionVector(3,2));
		expectedList.add(new PositionVector(4,3));
		expectedList.add(new PositionVector(5,3));
		expectedList.add(new PositionVector(6,4));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
	@Test
	public void calculatePath_HorizontalLineLeftToRightPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(1,0);
		PositionVector endPosition = new PositionVector(4,0);
		expectedList.add(new PositionVector(1,0));
		expectedList.add(new PositionVector(2,0));
		expectedList.add(new PositionVector(3,0));
		expectedList.add(new PositionVector(4,0));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
	@Test
	public void calculatePath_HorizontalLineRightToLeftPath() {
		// Arrange
		List<PositionVector> resultList = new ArrayList<>();
		List<PositionVector> expectedList = new ArrayList<>();
		PositionVector startPosition = new PositionVector(4,0);
		PositionVector endPosition = new PositionVector(1,0);
		expectedList.add(new PositionVector(4,0));
		expectedList.add(new PositionVector(3,0));
		expectedList.add(new PositionVector(2,0));
		expectedList.add(new PositionVector(1,0));
		
		// Act
		resultList= _testGame.calculatePath(startPosition, endPosition);
		
		// Assert
		assertEquals(resultList, expectedList);
	}
	
}
