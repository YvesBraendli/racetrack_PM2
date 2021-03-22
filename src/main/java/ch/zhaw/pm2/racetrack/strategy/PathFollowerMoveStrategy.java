package ch.zhaw.pm2.racetrack.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.PositionVector.Direction;

/**
 * The PathFollowerMoveStrategy class determines the next move based on a file
 * containing points on a path.
 */
public class PathFollowerMoveStrategy implements MoveStrategy {

	private ArrayList<PositionVector> followerPoints;
	private int turnCount;
	private Scanner followerReader;
	private PositionVector currentPosition;
	private Direction acceleration;

	/**
	 * Constructor of the PathFollowerMoveStrategy class. Loads the follower list
	 * file into an ArrayList if they follow the correct syntax.
	 * 
	 * @param moveListFile the file containing the moves
	 * @throws FileNotFoundException      if the file does note exist
	 * @throws InvalidMoveFormatException if the content does not comply with the
	 *                                    Direction Enum values
	 */
	public PathFollowerMoveStrategy(File followerListFile, PositionVector startPosition)
			throws FileNotFoundException, InvalidMoveFormatException {
		turnCount = 0;
		acceleration = Direction.NONE;
		currentPosition = startPosition;
		if (!followerListFile.exists())
			throw new FileNotFoundException("File does not exist in the given directory");
		followerReader = new Scanner(followerListFile);
		followerPoints = new ArrayList<PositionVector>();

		while (followerReader.hasNextLine()) {
			String nextLine = followerReader.nextLine();
			String[] parts = nextLine.split("");
			int x1 = 0;
			int x2 = 0;
			int y1 = 0;
			int y2 = 0;

			try {
				x1 = Integer.parseInt(parts[3]) * 10;
				x2 = Integer.parseInt(parts[4]);
			} catch (NumberFormatException e) {
				x1 = x1 / 10;
				x2 = 0;
			}

			try {
				y1 = Integer.parseInt(parts[9]) * 10;
				y2 = Integer.parseInt(parts[10]);
			} catch (NumberFormatException e) {
				y1 = y1 / 10;
				y2 = 0;
			}
			int x = x1 + x2;
			int y = y1 + y2;

			followerPoints.add(new PositionVector(x, y));
		}
		followerReader.close();
	}

	@Override
	public Direction nextMove() {
		if (turnCount >= followerPoints.size())
			return Direction.NONE;

		if (acceleration != Direction.NONE) {
			Direction newDirection = Direction.NONE;
			switch (acceleration) {
			case RIGHT:
				newDirection = Direction.LEFT;
				break;
			case DOWN_RIGHT:
				newDirection = Direction.UP_LEFT;
				break;
			case DOWN:
				newDirection = Direction.UP;
				break;
			case DOWN_LEFT:
				newDirection = Direction.UP_RIGHT;
				break;
			case LEFT:
				newDirection = Direction.RIGHT;
				break;
			case UP_LEFT:
				newDirection = Direction.DOWN_RIGHT;
				break;
			case UP:
				newDirection = Direction.DOWN;
				break;
			case UP_RIGHT:
				newDirection = Direction.DOWN_LEFT;
				break;
			}
			acceleration = Direction.NONE;
			return newDirection;
		}

		PositionVector nextPosition = followerPoints.get(turnCount);
		PositionVector difference = PositionVector.subtract(nextPosition, currentPosition);

		int dx = difference.getX();
		int dy = difference.getY();

		int xAbs = 0;
		int yAbs = 0;

		if (dx != 0) {
			xAbs = (int) Math.abs((double) dx);
		}
		if (dy != 0) {
			yAbs = (int) Math.abs((double) dy);
		}

		int x = 0;
		int y = 0;

		if (xAbs > yAbs && xAbs != 0) {
			x = dx / xAbs;
		} else if (yAbs != 0) {
			y = dy / (int) Math.abs((double) yAbs);
		}

		PositionVector accelerationVector = new PositionVector(x, y);
		currentPosition = PositionVector.add(currentPosition, accelerationVector);

		if (nextPosition.getX() == currentPosition.getX() 
				&& nextPosition.getY() == currentPosition.getY()) {
			turnCount++;
		}

		for (Direction direction : Direction.values()) {
			if (direction.vector.getX() == accelerationVector.getX()
					&& direction.vector.getY() == accelerationVector.getY()) {
				acceleration = direction;
			}
		}
		return acceleration;
	}
}
