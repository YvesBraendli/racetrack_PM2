package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
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
	
	@Test
	public void GetWinner_GameIsInProgressFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		// Act
		int result = _testGame.getWinner();

		// Assert
		assertTrue(Game.NO_WINNER == result);
	}

	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyFinishLineRight_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
	}

	@Test
	public void GetWinner_CarCrossesFinishLineCorrectlyAndCrashesAfterwardsFinishLineRight_ReturnsWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		assertFalse(true);
	}

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

	@Test
	public void GetWinner_TwoCarsFromTwoAreAliveFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		// Act
		int currentWinner = _testGame.getWinner();

		// Assert
		assertTrue(currentWinner == Game.NO_WINNER);
	}

	@Test
	public void GetWinner_TwoCarsFromFourAreAliveFinishLineRight_ReturnsNoWinner() {
		// Arrange
		initGame(new File("tracks\\challenge.txt"));
		assertFalse(true);
	}
}
