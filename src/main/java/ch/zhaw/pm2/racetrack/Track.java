package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class represents the racetrack board.
 *
 * <p>
 * The racetrack board consists of a rectangular grid of 'width' columns and
 * 'height' rows. The zero point of the grid is at the top left. The x-axis
 * points to the right and the y-axis points downwards.
 * </p>
 * <p>
 * Positions on the track grid are specified using {@link PositionVector}
 * objects. These are vectors containing an x/y coordinate pair, pointing from
 * the zero-point (top-left) to the addressed space in the grid.
 * </p>
 *
 * <p>
 * Each position in the grid represents a space which can hold an enum object of
 * type {@link Config.SpaceType}.<br>
 * Possible Space types are:
 * <ul>
 * <li>WALL : road boundary or off track space</li>
 * <li>TRACK: road or open track space</li>
 * <li>FINISH_LEFT, FINISH_RIGHT, FINISH_UP, FINISH_DOWN : finish line spaces
 * which have to be crossed in the indicated direction to win the race.</li>
 * </ul>
 * <p>
 * Beside the board the track contains the list of cars, with their current
 * state (position, velocity, crashed,...)
 * </p>
 *
 * <p>
 * At initialization the track grid data is read from the given track file. The
 * track data must be a rectangular block of text. Empty lines at the start are
 * ignored. Processing stops at the first empty line following a non-empty line,
 * or at the end of the file.
 * </p>
 * <p>
 * Characters in the line represent SpaceTypes. The mapping of the Characters is
 * as follows:
 * </p>
 * <ul>
 * <li>WALL : '#'</li>
 * <li>TRACK: ' '</li>
 * <li>FINISH_LEFT : '&lt;'</li>
 * <li>FINISH_RIGHT: '&gt;'</li>
 * <li>FINISH_UP : '^;'</li>
 * <li>FINISH_DOWN: 'v'</li>
 * <li>Any other character indicates the starting position of a car.<br>
 * The character acts as the id for the car and must be unique.<br>
 * There are 1 to {@link Config#MAX_CARS} allowed.</li>
 * </ul>
 *
 * <p>
 * All lines must have the same length, used to initialize the grid width).
 * Beginning empty lines are skipped. The the tracks ends with the first empty
 * line or the file end.<br>
 * An {@link InvalidTrackFormatException} is thrown, if
 * <ul>
 * <li>not all track lines have the same length</li>
 * <li>the file contains no track lines (grid height is 0)</li>
 * <li>the file contains more than {@link Config#MAX_CARS} cars</li>
 * </ul>
 *
 * <p>
 * The Track can return a String representing the current state of the race
 * (including car positons)
 * </p>
 */
public class Track {

	public static final char CRASH_INDICATOR = 'X';
	private ArrayList<String> track;
	private ArrayList<Car> cars;
	private boolean isQuarterMileTrack = false;
	private static final String QUARTER_MILE_COMPARISION = "quarter-mile.txt";

	/**
	 * Initialize a Track from the given track file.
	 *
	 * @param trackFile Reference to a file containing the track data
	 * @throws FileNotFoundException       if the given track file could not be
	 *                                     found
	 * @throws InvalidTrackFormatException if the track file contains invalid data
	 *                                     (no tracklines, ...)
	 */
	public Track(File trackFile) throws FileNotFoundException, InvalidTrackFormatException {
		if (trackFile.getName().equals(QUARTER_MILE_COMPARISION)) isQuarterMileTrack = true;
		track = new ArrayList<>();
		cars = new ArrayList<>();
		if (!trackFile.exists()) {
			throw new FileNotFoundException("File does not exist in the given directory");
		}
		Scanner trackReader = new Scanner(trackFile);
		while (trackReader.hasNextLine()) {
			String trackLine = trackReader.nextLine().strip();
			track.add(trackLine);
		}
		createCars();
		clearTrack();
		trackReader.close();
		for (String line : track) {
			if (fileContainsInvalidData(line)) {
				throw new InvalidTrackFormatException("Data File contains invalid symbols.");
			}
		}
	}
	
	private void clearTrack() {
		for (int i = 0; i < track.size(); i++) {
			String trackLine = track.get(i);
			char[] lineSymbols = trackLine.toCharArray();
			for (int z = 0; z < lineSymbols.length; z++) {
				if (lineSymbols[z] != Config.SpaceType.FINISH_DOWN.value
						&& lineSymbols[z] != Config.SpaceType.FINISH_UP.value
						&& lineSymbols[z] != Config.SpaceType.FINISH_RIGHT.value
						&& lineSymbols[z] != Config.SpaceType.FINISH_LEFT.value
						&& lineSymbols[z] != Config.SpaceType.WALL.value
						&& lineSymbols[z] != Config.SpaceType.TRACK.value) {
					trackLine = trackLine.replace(lineSymbols[z], ' ');
					track.remove(i);
					track.add(i, trackLine);
				}
			}
		}
	}

	/**
	 * Adds a new car to the car-list if and only if the position is at a correct
	 * starting place.
	 * 
	 * @param carId    The specified ID for this car.
	 * @param position The starting position of this car.
	 */
	private void createCars() {
		int carCounter = 0;
		int longestRowValue = 0;
		for (int i = 0; i<track.size(); i++) {
			if(track.get(i).length()>longestRowValue) {
				longestRowValue = track.get(i).length();
			}
		}
		char[][] trackSymbols = new char[track.size()][longestRowValue];
		char[] lineSymbols = new char[longestRowValue];
		for (int row = 0; row < track.size(); row++) {
			lineSymbols = track.get(row).toCharArray();
			for (int column = 0; column < lineSymbols.length; column++) {
				trackSymbols[row][column] = lineSymbols[column];
			}
		}
		for (int row = 0; row < trackSymbols.length; row++) {
			for (int column = 0; column < trackSymbols[row].length; column++) {
				if (checkIfValidStartingPosition(trackSymbols, row, column)) {
					carCounter++;
					if (carCounter < Config.MAX_CARS) {
						cars.add(new Car(trackSymbols[row][column], new PositionVector(column, row)));
					}
				}
			}
		}
	}

	private boolean checkIfValidStartingPosition(char[][] trackSymbols, int rowCounter, int columnCounter) {
		int validPositionOfFinishUpSign = 1;
		int validPositionOfFinishDownSign = -1;
		int validPositionOfFinishRightSign = -2;
		int validPositionOfFinishLeftSign = 2;
		int validPositionOfWallWhenQuarterMile = 2;
		if(isQuarterMileTrack) {
			if (isNoTrackSign(trackSymbols, rowCounter, columnCounter)
					&& (rowCounter > 0 && columnCounter > 0
							&& rowCounter <= trackSymbols.length
							&& columnCounter <= (trackSymbols[rowCounter].length - validPositionOfWallWhenQuarterMile))
					&&((trackSymbols[rowCounter][columnCounter+validPositionOfWallWhenQuarterMile]==Config.SpaceType.WALL.value)
							))
				return true;
			return false;
		}
		if (isNoTrackSign(trackSymbols, rowCounter, columnCounter)
				&& (rowCounter > 0 && columnCounter > 0
						&& rowCounter <= (trackSymbols.length + validPositionOfFinishUpSign)
						&& columnCounter <= (trackSymbols[rowCounter].length + validPositionOfFinishUpSign))
				&&((trackSymbols[rowCounter+validPositionOfFinishUpSign][columnCounter]==Config.SpaceType.FINISH_UP.value)
						||(trackSymbols[rowCounter+validPositionOfFinishDownSign][columnCounter]==Config.SpaceType.FINISH_DOWN.value)
						||(trackSymbols[rowCounter][columnCounter+validPositionOfFinishLeftSign]==Config.SpaceType.FINISH_LEFT.value)
						||(trackSymbols[rowCounter][columnCounter+validPositionOfFinishRightSign]==Config.SpaceType.FINISH_RIGHT.value)
						))
			return true;
		return false;
	}
	
	private boolean isNoTrackSign(char[][] trackSymbols, int rowCounter, int columnCounter) {
		if(!(trackSymbols[rowCounter][columnCounter] == Config.SpaceType.FINISH_DOWN.value
				|| trackSymbols[rowCounter][columnCounter] == Config.SpaceType.FINISH_UP.value
				|| trackSymbols[rowCounter][columnCounter] == Config.SpaceType.FINISH_RIGHT.value
				|| trackSymbols[rowCounter][columnCounter] == Config.SpaceType.FINISH_LEFT.value
				|| trackSymbols[rowCounter][columnCounter] == Config.SpaceType.WALL.value
				|| trackSymbols[rowCounter][columnCounter] == Config.SpaceType.TRACK.value)) {
			return true;
		}
		
		return false;
	}

	/**
	 * Return the type of space at the given position. If the location is outside
	 * the track bounds, it is considered a wall.
	 *
	 * @param position The coordinates of the position to examine
	 * @return The type of track position at the given location
	 */
	public Config.SpaceType getSpaceType(PositionVector position) {
		if (position.getY() > track.size() || position.getX() >= track.get(position.getY()).length()) {
			return Config.SpaceType.WALL;	
		}
		Character charSymbol = track.get(position.getY()).charAt(position.getX());
		return showSpaceTypeAtSpecifiedPosition(charSymbol);
	}

	private Config.SpaceType showSpaceTypeAtSpecifiedPosition(Character symbol) {
		if (symbol.equals(Config.SpaceType.FINISH_DOWN.value)) {
			return Config.SpaceType.FINISH_DOWN;	
		}
		if (symbol.equals(Config.SpaceType.FINISH_UP.value)) {
			return Config.SpaceType.FINISH_UP;	
		}
		if (symbol.equals(Config.SpaceType.FINISH_RIGHT.value)) {
			return Config.SpaceType.FINISH_RIGHT;
		}
		if (symbol.equals(Config.SpaceType.FINISH_LEFT.value)) {
			return Config.SpaceType.FINISH_LEFT;
		}
		if (symbol.equals(Config.SpaceType.WALL.value)) {
			return Config.SpaceType.WALL;
		}
		return Config.SpaceType.TRACK;
	}

	/**
	 * Return the number of cars.
	 *
	 * @return Number of cars
	 */
	public int getCarCount() {
		return cars.size();
	}

	/**
	 * Get instance of specified car.
	 *
	 * @param carIndex The zero-based carIndex number
	 * @return The car instance at the given index
	 */
	public Car getCar(int carIndex) {
		return cars.get(carIndex);
	}

	/**
	 * Get the id of the specified car.
	 *
	 * @param carIndex The zero-based carIndex number
	 * @return A char containing the id of the car
	 */
	public char getCarId(int carIndex) {
		return cars.get(carIndex).getID();
	}

	/**
	 * Get the position of the specified car.
	 *
	 * @param carIndex The zero-based carIndex number
	 * @return A PositionVector containing the car's current position
	 */
	public PositionVector getCarPos(int carIndex) {
		return cars.get(carIndex).getPosition();
	}

	/**
	 * Get the velocity of the specified car.
	 *
	 * @param carIndex The zero-based carIndex number
	 * @return A PositionVector containing the car's current velocity
	 */
	public PositionVector getCarVelocity(int carIndex) {
		return cars.get(carIndex).getVelocity();
	}

	/**
	 * Gets character at the given position. If there is a crashed car at the
	 * position, {@link #CRASH_INDICATOR} is returned.
	 *
	 * @param y            position Y-value
	 * @param x            position X-vlaue
	 * @param currentSpace char to return if no car is at position (x,y)
	 * @return character representing position (x,y) on the track
	 */
	public char getCharAtPosition(int y, int x, Config.SpaceType currentSpace) {
		for (int i = 0; i < cars.size(); i++) {
			if ((cars.get(i).getPosition().getY() == y) || (cars.get(i).getPosition().getX() == x)) {
				if (cars.get(i).isCrashed()) {
					return CRASH_INDICATOR;
				}
				return cars.get(i).getID();
			}
		}

		return currentSpace.value;
	}

	/**
	 * Return a String representation of the track, including the car locations.
	 *
	 * @return A String representation of the track
	 */
	public String toString() {
		if (cars.size() > 0) {
			for (Car car : cars) {
				int yCoordinate = car.getPosition().getY();
				int xCoordinate = car.getPosition().getX();
				char carIndicator = car.getID();
				if(car.isCrashed()) {
					carIndicator = CRASH_INDICATOR;
				}
				String trackLine = track.get(yCoordinate);
				String newTrackLineFront = trackLine.substring(0, xCoordinate) + carIndicator;
				String newTrackLineEnd = trackLine.substring(xCoordinate + 1);
				String newTrackLine = newTrackLineFront + newTrackLineEnd;
				track.remove(yCoordinate);
				track.add(yCoordinate, newTrackLine);
			}
		}
		String trackInOneLine = track.get(0);
		for (int i = 1; i < track.size(); i++) {
			trackInOneLine += "\n";
			trackInOneLine += track.get(i);
		}
		clearTrack();
		return trackInOneLine;
	}

	private boolean fileContainsInvalidData(String trackLine) {
		boolean hasFinishDownSigns = trackLine.contains(String.valueOf(Config.SpaceType.FINISH_DOWN.value));
		boolean hasFinishUpSigns = trackLine.contains(String.valueOf(Config.SpaceType.FINISH_UP.value));
		boolean hasFinishLeftSigns = trackLine.contains(String.valueOf(Config.SpaceType.FINISH_RIGHT.value));
		boolean hasFinishRightSigns = trackLine.contains(String.valueOf(Config.SpaceType.FINISH_LEFT.value));
		if ((hasFinishDownSigns && hasFinishUpSigns) || (hasFinishDownSigns && hasFinishRightSigns)
				|| (hasFinishDownSigns && hasFinishLeftSigns) || (hasFinishUpSigns && hasFinishLeftSigns)
				|| (hasFinishUpSigns && hasFinishRightSigns) || (hasFinishLeftSigns && hasFinishRightSigns))
			return false;
		if (!(trackLine.contains(String.valueOf(Config.SpaceType.WALL.value))
				|| (trackLine.contains(String.valueOf(Config.SpaceType.TRACK.value))))
				|| (trackLine.contains(String.valueOf(Config.SpaceType.FINISH_DOWN.value))
						&& trackLine.contains(String.valueOf(Config.SpaceType.FINISH_LEFT.value))
						&& trackLine.contains(String.valueOf(Config.SpaceType.FINISH_UP.value))
						&& trackLine.contains(String.valueOf(Config.SpaceType.FINISH_RIGHT.value)))) {
			return true;
		}
		return false;
	}
}
