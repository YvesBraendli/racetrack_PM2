package ch.zhaw.pm2.racetrack;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.pm2.racetrack.Config.SpaceType;

import static ch.zhaw.pm2.racetrack.PositionVector.Direction;

/**
 * Game controller class, performing all actions to modify the game state. It
 * contains the logic to move the cars, detect if they are crashed and if we
 * have a winner.
 */
public class Game {

	private ArrayList<Car> players = new ArrayList<>();
	private ArrayList<Car> playersToGoAround = new ArrayList<>();
	private Car currentCar;
	private Track track;

	public static final int NO_WINNER = -1;

	public Game(int numberOfPlayers, Track track) {
		this.track = track;

		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(track.getCar(i));
		}
		currentCar = players.get(0);
	}

	/**
	 * Return the index of the current active car. Car indexes are zero-based, so
	 * the first car is 0, and the last car is getCarCount() - 1.
	 * 
	 * @return The zero-based number of the current car
	 */
	public int getCurrentCarIndex() {
		return players.indexOf(currentCar);
	}

	/**
	 * Get the id of the specified car.
	 * 
	 * @param carIndex The zero-based carIndex number
	 * @return A char containing the id of the car
	 */
	public char getCarId(int carIndex) {
		if(carIndex >= players.size() || carIndex < 0) {
			return Character.MIN_VALUE;
		}
		return players.get(carIndex).getID();
	}

	/**
	 * Get the position of the specified car.
	 * 
	 * @param carIndex The zero-based carIndex number
	 * @return A PositionVector containing the car's current position
	 */
	public PositionVector getCarPosition(int carIndex) {
		if(carIndex >= players.size() || carIndex < 0) {
			return null;
		}
		return players.get(carIndex).getPosition();
	}

	/**
	 * Get the velocity of the specified car.
	 * 
	 * @param carIndex The zero-based carIndex number
	 * @return A PositionVector containing the car's current velocity
	 */
	public PositionVector getCarVelocity(int carIndex) {
		if(carIndex >= players.size() || carIndex < 0) {
			return null;
		}
		return players.get(carIndex).getVelocity();
	}

	/**
	 * Return the winner of the game. If the game is still in progress, returns
	 * NO_WINNER.
	 * 
	 * @return The winning car's index (zero-based, see getCurrentCar()), or
	 *         NO_WINNER if the game is still in progress
	 */
	public int getWinner() {
		// if there is no winner yet and game is still running.
		if (isGameInProgress()) {
			return NO_WINNER;
		}

		if (getUncrashedCars().size() == 1) {
			return players.indexOf(getUncrashedCars().get(0));
		}
		return getCurrentCarIndex();
	}

	/**
	 * Switches to the next car who is still in the game. Skips crashed cars.
	 */
	public void switchToNextActiveCar() {
		int indexOfActiveCar = players.indexOf(currentCar);
		do {		
		if (indexOfActiveCar == players.size() - 1) {
			indexOfActiveCar = 0;
		} else {
			indexOfActiveCar++;
		}
		currentCar = players.get(indexOfActiveCar);
		}while(currentCar.isCrashed());
	}

	/**
	 * Does indicate if a car would have a crash with a WALL space or another car at
	 * the given position.
	 * 
	 * @param carIndex The zero-based carIndex number
	 * @param position A PositionVector of the possible crash position
	 * @return A boolean indicator if the car would crash with a WALL or another
	 *         car.
	 */
	public boolean willCarCrash(int carIndex, PositionVector position) {
		// TODO implement
		throw new UnsupportedOperationException();
	}

	private boolean isGameInProgress() {
		if (hasCurrentCarFinished()) {
			return false;
		}
		boolean hasOnlyOneCarLeft = getUncrashedCars().size() == 1;
		if (hasOnlyOneCarLeft) {
			return false;
		}
		return true;
	}

	private boolean hasCurrentCarFinished() {
		int startX = currentCar.getPosition().getX() - currentCar.getVelocity().getX();
		int startY = currentCar.getPosition().getY() - currentCar.getVelocity().getY();
		PositionVector startPosition = new PositionVector(startX, startY);
		List<PositionVector> positions = calculatePath(startPosition, currentCar.getPosition());
		for (PositionVector position : positions) {
			Config.SpaceType spaceType = track.getSpaceType(position);
			if (spaceType == SpaceType.FINISH_DOWN) {
				if (startY < position.getY()) {
					boolean hasToGoOneLap = playersToGoAround.contains(currentCar);
					playersToGoAround.remove(currentCar);
					return !hasToGoOneLap;
				} else {
					playersToGoAround.add(currentCar);
				}
			}
			if (spaceType == SpaceType.FINISH_UP) {
				if (startY > position.getY()) {
					boolean hasToGoOneLap = playersToGoAround.contains(currentCar);
					playersToGoAround.remove(currentCar);
					return !hasToGoOneLap;
				} else {
					playersToGoAround.add(currentCar);
				}
			}
			if (spaceType == SpaceType.FINISH_LEFT) {
				if (startX > position.getX()) {
					boolean hasToGoOneLap = playersToGoAround.contains(currentCar);
					playersToGoAround.remove(currentCar);
					return !hasToGoOneLap;
				} else {
					playersToGoAround.add(currentCar);
				}
			}
			if (spaceType == SpaceType.FINISH_RIGHT) {
				if (startX < position.getX()) {
					boolean hasToGoOneLap = playersToGoAround.contains(currentCar);
					playersToGoAround.remove(currentCar);
					return !hasToGoOneLap;

				} else {
					playersToGoAround.add(currentCar);
				}
			}

		}

		return false;

	}

	private ArrayList<Car> getUncrashedCars() {
		ArrayList<Car> uncrashedCars = new ArrayList<>();
		for (Car car : players) {
			if (!car.isCrashed()) {
				uncrashedCars.add(car);
			}
		}
		return uncrashedCars;
	}

	/**
	 * Execute the next turn for the current active car.
	 * <p>
	 * This method changes the current car's velocity and checks on the path to the
	 * next position, if it crashes (car state to crashed) or passes the finish line
	 * in the right direction (set winner state).
	 * </p>
	 * <p>
	 * The steps are as follows
	 * </p>
	 * <ol>
	 * <li>Accelerate the current car</li>
	 * <li>Calculate the path from current (start) to next (end) position (see
	 * {@link Game#calculatePath(PositionVector, PositionVector)})</li>
	 * <li>Verify for each step what space type it hits:
	 * <ul>
	 * <li>TRACK: check for collision with other car (crashed &amp; don't continue),
	 * otherwise do nothing</li>
	 * <li>WALL: car did collide with the wall - crashed &amp; don't continue</li>
	 * <li>FINISH_*: car hits the finish line - wins only if it crosses the line in
	 * the correct direction</li>
	 * </ul>
	 * </li>
	 * <li>If the car crashed or wins, set its position to the crash/win
	 * coordinates</li>
	 * <li>If the car crashed, also detect if there is only one car remaining,
	 * remaining car is the winner</li>
	 * <li>Otherwise move the car to the end position</li>
	 * </ol>
	 * <p>
	 * The calling method must check the winner state and decide how to go on. If
	 * the winner is different than {@link Game#NO_WINNER}, or the current car is
	 * already marked as crashed the method returns immediately.
	 * </p>
	 *
	 * @param acceleration A Direction containing the current cars acceleration
	 *                     vector (-1,0,1) in x and y direction for this turn
	 */
	public void doCarTurn(Direction acceleration) {
		currentCar.accelerate(acceleration);
		List<PositionVector> positionList = calculatePath(currentCar.getPosition(), currentCar.nextPosition());
		int hasWinner = NO_WINNER;
		for (PositionVector position : positionList) {
			if (hasWinner != NO_WINNER || currentCar.isCrashed()) {
				return;
			}
			if (!(position.equals(positionList.get(0)))) {
				switch (track.getSpaceType(position)) {
				case TRACK:
					for (Car car : players) {
						if (car.getPosition().equals(position) && !car.equals(currentCar)) {
							currentCar.setPosition(position);
							currentCar.crash();
							return;
						}
						currentCar.setPosition(position);
					}
					break;
				case WALL:
					currentCar.setPosition(position);
					currentCar.crash();
					return;
				case FINISH_UP:
				case FINISH_DOWN:
				case FINISH_LEFT:
				case FINISH_RIGHT:
					currentCar.setPosition(position);
					hasWinner = getWinner();
					break;
				}
			}
		}

	}

	/**
	 * Returns all of the grid positions in the path between two positions, for use
	 * in determining line of sight. Determine the 'pixels/positions' on a
	 * raster/grid using Bresenham's line algorithm.
	 * (https://de.wikipedia.org/wiki/Bresenham-Algorithmus) Basic steps are -
	 * Detect which axis of the distance vector is longer (faster movement) - for
	 * each pixel on the 'faster' axis calculate the position on the 'slower' axis.
	 * Direction of the movement has to correctly considered
	 * 
	 * @param startPosition Starting position as a PositionVector
	 * @param endPosition   Ending position as a PositionVector
	 * @return Intervening grid positions as a List of PositionVector's, including
	 *         the starting and ending positions.
	 */
	public List<PositionVector> calculatePath(PositionVector startPosition, PositionVector endPosition) {
		List<PositionVector> positionList = new ArrayList<>();

		int dx = endPosition.getX() - startPosition.getX();
		int dy = endPosition.getY() - startPosition.getY();

		int absoluteDX = getAbsoluteValue(dx);
		int absoluteDY = getAbsoluteValue(dy);

		int fastStepType = 1;
		int slowStepType = 1;

		int fasterDirection = dx;
		int slowerDirection = dy;

		if (absoluteDY > absoluteDX) {
			fasterDirection = dy;
			slowerDirection = dx;
		}

		if (fasterDirection < 0) {
			fasterDirection = getAbsoluteValue(fasterDirection);
			fastStepType = -1;
		}

		if (slowerDirection < 0) {
			slowerDirection = getAbsoluteValue(slowerDirection);
			slowStepType = -1;
		}

		double errorCorrection = (double) absoluteDX / 2;
		positionList.add(startPosition);
		int xDirection = positionList.get(0).getX();
		int yDirection = positionList.get(0).getY();
		for (int i = 0; i < fasterDirection; i++) {
			if (fasterDirection == absoluteDX) {
				xDirection = positionList.get(i).getX() + fastStepType;
				errorCorrection = errorCorrection - slowerDirection;
				if (errorCorrection < 0) {
					yDirection = positionList.get(i).getY() + slowStepType;
					errorCorrection = errorCorrection + fasterDirection;
				}
			} else {
				yDirection = positionList.get(i).getY() + fastStepType;
				errorCorrection = errorCorrection - slowerDirection;
				if (errorCorrection < 0) {
					xDirection = positionList.get(i).getX() + slowStepType;
					errorCorrection = errorCorrection + fasterDirection;
				}
			}
			positionList.add(new PositionVector(xDirection, yDirection));
		}

		return positionList;
	}

	private int getAbsoluteValue(int number) {
		if (number < 0)
			return number * (-1);
		return number;
	}

	public Track getTrack() {
		return track;
	}
}
