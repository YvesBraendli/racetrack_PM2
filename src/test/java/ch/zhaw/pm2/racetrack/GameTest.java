package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.Config.StrategyType;
import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.strategy.InvalidMoveFormatException;
import ch.zhaw.pm2.racetrack.strategy.MoveListStrategy;

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
	 * Equivalence Partitioning: W1 - game is in progress
	 * Tests GetWinner() when Game is still in Progress and no car has won. Test
	 * track: FinishLine Right
	 */
	@Test
	public void getWinner_GameIsInProgressFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

	/**
	 * Equivalence Partitioning: W1 - game is in progress
	 * Tests GetWinner() when Game is still in Progress and no car has won. Test
	 * track: FinishLine Up
	 */
	@Test
	public void getWinner_GameIsInProgressFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}
	
	/**
	 * Equivalence Partitioning: W1 - game is in progress
	 * Tests GetWinner() when Game is still in Progress and no car has won. Test
	 * track: FinishLine Left
	 */
	@Test
	public void getWinner_GameIsInProgressFinishLineLeft_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

	/**
	 * Equivalence Partitioning: W2 - cross finish line correctly
	 * Tests GetWinner() when car has crossed finish line correctly and the car has
	 * won the game. Test track: FinishLine Right
	 */
	@Test
	public void getWinner_CarCrossesFinishLineCorrectlyFinishLineRight_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		makeMoveToWinChallengeTrack();

		// Act
		int currentWinner = _testGame.getWinner();
		
		// Assert
		assertTrue(currentWinner == 0);
	}

	/**
	 * Equivalence Partitioning: W2 - cross finish line correctly
	 * Tests GetWinner() when car has crossed finish line correctly and the car has
	 * won the game. Test track: FinishLine Left
	 */
	@Test
	public void getWinner_CarCrossesFinishLineCorrectlyFinishLineLeft_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Equivalence Partitioning: W3 - cross finish line incorrectly
	 * Tests GetWinner() when car has crossed finish line incorrectly (from wrong
	 * side) and no car has won. Test track: FinishLine Right
	 */
	@Test
	public void getWinner_CarCrossesFinishLineBackwardsFinishLineRight_ReturnsNoWinner() {
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
	 * Equivalence Partitioning: W3 - cross finish line incorrectly
	 * Tests GetWinner() when car has crossed finish line incorrectly (from wrong
	 * side) and no car has won. Test track: FinishLine Up
	 */
	@Test
	public void getWinner_CarCrossesFinishLineBackwardsFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		_testGame.doCarTurn(Direction.DOWN);
		_testGame.doCarTurn(Direction.DOWN);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Equivalence Partitioning: W4 - crosses finish line and crashes
	 * Tests GetWinner() when car has crossed finish line correctly and crashes right after
	 */
	@Test
	public void getWinner_CarCrossesFinishLineCorrectlyAndCrashes_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == 0);
	}
	
	/**
	 * Equivalence Partitioning: W5 - crosses finish line correctly, but has one lap to go
	 * Tests GetWinner() when car has crossed finish line correctly after crossing
	 * backwards. Car has still one lap to go and car does not win the game. Test
	 * track: FinishLine Right
	 */
	@Test
	public void getWinner_CarCrossesFinishLineBackwardsAndForwardAfterwardsFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);

		_testGame.doCarTurn(Direction.RIGHT);
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
	 * Equivalence Partitioning: W5 - crosses finish line correctly, but has one lap to go
	 * Tests GetWinner() when car has crossed finish line correctly after crossing
	 * backwards. Car has still one lap to go and car does not win the game. Test
	 * track: FinishLine Up
	 */
	@Test
	public void getWinner_CarCrossesFinishLineBackwardsAndForwardAfterwardsFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		_testGame.doCarTurn(Direction.DOWN);
		_testGame.doCarTurn(Direction.DOWN);

		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.UP);
		_testGame.doCarTurn(Direction.DOWN);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
	/**
	 * Equivalence Partitioning: W1 - game is in progress
	 * Tests GetWinner() when all cars are still alive. No car has won.
	 * Test track: FinishLine Right
	 */
	@Test
	public void getWinner_AllCarsAreAliveFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	/**
	 * Equivalence Partitioning: W1 - game is in progress
	 * Tests GetWinner() when all cars are still alive. No car has won.
	 * Test track: FinishLine Up
	 */
	@Test
	public void getWinner_AllCarsAreAliveFinishLineUp_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\oval-clock-up.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
	/**
	 * Equivalence Partitioning: W1 - game is in progress
	 * Tests GetWinner() when all cars are still alive. No car has won.
	 * Test track: FinishLine Left
	 */
	@Test
	public void getWinner_AllCarsAreAliveFinishLinLeft_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}
	
	/**
	 * Equivalence Partitioning: W6 - all car crashes except one
	 * Tests GetWinner() when all cars are crashed except one. 
	 * Test track: FinishLine Left
	 */
	@Test
	public void getWinner_AllCarsCrashesExceptOne_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\quarter-mile.txt"));
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		_testGame.doCarTurn(Direction.RIGHT);
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == 1);
	}
	
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path from the bottom left of the map to the top right
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path a in vertical line upwards
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path in a vertical line downwards
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path from the bottom right of the map to the top left
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path from the top right of the map to the bottom left
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path from the top left of the map to the bottom right
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path in a horizontal line left to right
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests calculatePath() for a path in a horizontal line right to left
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests doCarTurn() for a valid position on the track in one step
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests doCarTurn() for a valid position on the track in two steps
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests doCarTurn() for a crashed position with another car
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests doCarTurn() for a crashed position with the wall
	 */
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
	
	/**
	 * Equivalence Partitioning: 
	 * Tests doCarTurn() for driving over the finish line
	 */
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
	
	private void makeMoveToWinChallengeTrack() {
		MoveListStrategy strategy = null;
		try {
			strategy = new MoveListStrategy(new File("moves\\challenge-car-a.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidMoveFormatException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 39; i++) {
			Direction acceleration = strategy.nextMove();
			_testGame.doCarTurn(acceleration);
		}
		
	}
}