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

	private void initGame(File trackFile) {
		Track track = null;
		try {
			track = new Track(trackFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidTrackFormatException e) {
			e.printStackTrace();
		}
		int carCount = track.getCarCount();
		_testGame = new Game(carCount, track);
	}
	
	@BeforeEach
	private void setup() {
		File file = new File("tracks/challenge.txt");
		Track track = null;
		try {
			track = new Track(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidTrackFormatException e) {
			e.printStackTrace();
		}
		int carCount = track.getCarCount();
		_testGame = new Game(carCount, track);
	}

	/**
	 * Tests GetWinner() when Game is still in Progress and no car has won. Test
	 * track: FinishLine Right
	 */
	@Test
	public void GetWinner_GameIsInProgressFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

	/**
	 * Tests GetWinner() when Game is still in Progress and no car has won. Test
	 * track: FinishLine Up
	 */
	@Test
	public void GetWinner_GameIsInProgressFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}
	
	/**
	 * Tests GetWinner() when Game is still in Progress and no car has won. Test
	 * track: FinishLine Left
	 */
	@Test
	public void GetWinner_GameIsInProgressFinishLineLeft_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

	/**
	 * Tests GetWinner() when car has crossed finish line correctly and the car has
	 * won the game. Test track: FinishLine Right
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyFinishLineRight_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		assertFalse(true);
	}
	
	/**
	 * Tests GetWinner() when car has crossed finish line correctly and the car has
	 * won the game. Test track: FinishLine Up
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyFinishLineUp_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		assertFalse(true);
	}

	/**
	 * Tests GetWinner() when car has crossed finish line correctly and the car has
	 * won the game. Test track: FinishLine Left
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyFinishLineLeft_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		// _testGame.doCarTurn(Direction.LEFT); use this line to check for one other bug

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Tests GetWinner() when car has crossed finish line correctly and crashes
	 * right after. Position outside wall and track. Car has won the game. Test track: FinishLine Right
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyAndCrashesAfterwardsFinishLineRight_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		assertFalse(true);
	}
	
	/**
	 * Tests GetWinner() when car has crossed finish line correctly and crashes
	 * right after. Position outside wall and track. Car has won the game. Test track: FinishLine Up
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyAndCrashesAfterwardsFinishLineUp_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		assertFalse(true);
	}

	/**
	 * Tests GetWinner() when car has crossed finish line incorrectly (from wrong
	 * side) and no car has won. Test track: FinishLine Right
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineBackwardsFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
	/**
	 * Tests GetWinner() when car has crossed finish line incorrectly (from wrong
	 * side) and no car has won. Test track: FinishLine Up
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineBackwardsFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		_testGame.doCarTurn(Direction.DOWN);
		_testGame.doCarTurn(Direction.DOWN);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Tests GetWinner() when car has crossed finish line correctly after crossing
	 * backwards. Car has still one lap to go and car does not win the game. Test
	 * track: FinishLine Right
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineBackwardsAndForwardAfterwardsFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);

		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Tests GetWinner() when car has crossed finish line correctly after crossing
	 * backwards. Car has still one lap to go and car does not win the game. Test
	 * track: FinishLine Up
	 */
	@Test
	public void GetWinner_CarCrossesFinishLineBackwardsAndForwardAfterwardsFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		_testGame.doCarTurn(Direction.DOWN);
		_testGame.doCarTurn(Direction.DOWN);

		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.UP);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
	/**
	 * Tests GetWinner() when all cars are still alive. No car has won.
	 * Test track: FinishLine Right
	 */
	@Test
	public void GetWinner_AllCarsAreAliveFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Tests GetWinner() when all cars are still alive. No car has won.
	 * Test track: FinishLine Up
	 */
	@Test
	public void GetWinner_AllCarsAreAliveFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
	/**
	 * Tests GetWinner() when all cars are still alive. No car has won.
	 * Test track: FinishLine Left
	 */
	@Test
	public void GetWinner_AllCarsAreAliveFinishLinLeft_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
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
	
	@Test
	public void doCarTurn_ValidPathOneStep() {
		// Arrange
		PositionVector playerOneStartPosition = new PositionVector(24,22);
		PositionVector expectedEndPosition = new PositionVector(25,22);
		Direction acceleration = Direction.RIGHT;
		
		// Act
		_testGame.doCarTurn(acceleration);
		
		// Assert
		assertAll(
				() -> assertEquals(expectedEndPosition ,_testGame.getCarPosition(0)),
				() -> assertFalse(_testGame.getTrack().getCar(0).isCrashed())
		);
	}
	
	@Test
	public void doCarTurn_ValidPathMultipleStep() {
		// Arrange
		PositionVector expectedEndPosition = new PositionVector(27,22);
		Direction acceleration = Direction.RIGHT;
		
		// Act
		_testGame.doCarTurn(acceleration);
		_testGame.doCarTurn(acceleration);
		
		// Assert
		assertAll(
				() -> assertEquals(expectedEndPosition ,_testGame.getCarPosition(0)),
				() -> assertFalse(_testGame.getTrack().getCar(0).isCrashed())
		);
	}
	
	@Test
	public void doCarTurn_CrashWithOtherPlayer() {
		// Arrange
		_testGame.getTrack().getCar(1).setPosition(new PositionVector(26,22));
		PositionVector expectedEndPosition = new PositionVector(26,22);
		Direction acceleration = Direction.RIGHT;
		
		// Act
		_testGame.doCarTurn(acceleration);
		_testGame.doCarTurn(acceleration);
		
		// Assert
		assertAll(
				() -> assertEquals(expectedEndPosition ,_testGame.getCarPosition(0)),
				() -> assertTrue(_testGame.getTrack().getCar(0).isCrashed()),
				() -> assertFalse(_testGame.getTrack().getCar(1).isCrashed())
		);
	}
	
	@Test
	public void doCarTurn_CrashWithWall() {
		// Arrange
		PositionVector expectedEndPosition = new PositionVector(24,21);
		Direction acceleration = Direction.UP;
		
		// Act
		_testGame.doCarTurn(acceleration);
		
		// Assert
		assertAll(
				() -> assertEquals(expectedEndPosition ,_testGame.getCarPosition(0)),
				() -> assertTrue(_testGame.getTrack().getCar(0).isCrashed())
		);
	}
	
	@Test
	public void doCarTurn_WinnerFoundReturnMethod() {
		// Arrange
		_testGame.getTrack().getCar(0).setPosition(new PositionVector(20,22));
		PositionVector expectedEndPosition = new PositionVector(22,22);
		Direction acceleration = Direction.RIGHT;
		
		// Act
		_testGame.doCarTurn(acceleration);
		_testGame.doCarTurn(acceleration);
		
		// Assert
		assertAll(
				() -> assertEquals(expectedEndPosition ,_testGame.getCarPosition(0)),
				() -> assertTrue(_testGame.getWinner() == 0)
		);
		
	}
	

	

}