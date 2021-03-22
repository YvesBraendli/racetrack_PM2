package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.Config.SpaceType;
import ch.zhaw.pm2.racetrack.strategy.*;

public class PathFollowerMoveStrategyTest {
	private Game _testGame;
	private MoveStrategy _testStrategy;
	private Scanner followerReader;
	private File followerPointList;
	private File trackFile;
	private List<PositionVector> followerPoints;
	
	@BeforeEach
	private void Setup() {
		trackFile = new File("tracks/challenge.txt");
		followerPointList = new File("follower/challenge_handout_points.txt");
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
		setupFollowerCompareList();
	}
	
	private void setupFollowerCompareList(){
		followerPoints = new ArrayList<PositionVector>();
		try {
			followerReader = new Scanner(followerPointList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(followerReader.hasNextLine()) {
			String nextLine = followerReader.nextLine();
			String[] parts = nextLine.split("");
			int x1 = 0;
			int x2 = 0;
			int y1 = 0;
			int y2 = 0;
			
			try {
				x1 = Integer.parseInt(parts[3])*10;
				x2 = Integer.parseInt(parts[4]);
			}catch (NumberFormatException e) {
				x1 = x1/10;
				x2 = 0;
			}
			
			try {
				y1 = Integer.parseInt(parts[9])*10;
				y2 = Integer.parseInt(parts[10]);
			}catch (NumberFormatException e) {
				y1 = y1 /10;
				y2 = 0;
			}
			int x = x1 + x2;
		    int y = y1 + y2;
			
			followerPoints.add(new PositionVector(x,y));
		}
		followerReader.close();
	}
	
	@Test
	public void testPositionEquivalenceFollowerPointsActualCarMovement() {
		// Arrange
		List<PositionVector> followerPointsUntilCrash = new ArrayList<PositionVector>();
		List<PositionVector> carPositions = new ArrayList<PositionVector>();
		for(PositionVector point: followerPoints) {
			if(_testGame.getTrack().getSpaceType(point).equals(SpaceType.WALL)) break;
			followerPointsUntilCrash.add(point);
		}
		
		while(_testGame.getTrack().getCar(0).isCrashed() == false || _testGame.getWinner() == _testGame.NO_WINNER) {
			Direction nextAcceleration = _testStrategy.nextMove();
			_testGame.doCarTurn(nextAcceleration);
			carPositions.add(_testGame.getCarPosition(0));
		}
		
		// Act
		boolean isPointFoundInCarPositions = true;
		for(PositionVector followerpoint: followerPointsUntilCrash) {
			if(!carPositions.contains(followerpoint)) {
				isPointFoundInCarPositions = false;
				break;
			}
		}
		
		// Assert
		assertTrue(isPointFoundInCarPositions);
		
	}
		
}
