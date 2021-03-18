package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;

public class GameTest {
	private Game _testGame;

	private void initGame(File trackFile) {
		File file = trackFile;
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
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);
		_testGame.doCarTurn(Direction.LEFT);

		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
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
}
