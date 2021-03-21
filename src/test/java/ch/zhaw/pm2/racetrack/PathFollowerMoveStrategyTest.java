package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.strategy.*;

public class PathFollowerMoveStrategyTest {
	private Game _testGame;
	private MoveStrategy _testStrategy;
	
	@BeforeEach
	private void Setup() {
		File trackFile = new File("tracks/challenge.txt");
		File followerPointList = new File("follower/challenge_handout_points.txt");
		PositionVector startPosition = new PositionVector(24,22);
		
		Track track = null;
		try {
			track = new Track(trackFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidTrackFormatException e) {
			e.printStackTrace();
		}
		
		try {
			_testStrategy = new PathFollowerMoveStrategy(followerPointList, startPosition);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidMoveFormatException e) {
			e.printStackTrace();
		}
		
		int carCount = track.getCarCount();
		_testGame = new Game(carCount, track);
	}
	
	@Test
	public void testPositionEquivalence() {
		
	}

}
