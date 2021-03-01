package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class represents the racetrack board.
 *
 * <p>The racetrack board consists of a rectangular grid of 'width' columns and 'height' rows.
 * The zero point of he grid is at the top left. The x-axis points to the right and the y-axis points downwards.</p>
 * <p>Positions on the track grid are specified using {@link PositionVector} objects. These are vectors containing an
 * x/y coordinate pair, pointing from the zero-point (top-left) to the addressed space in the grid.</p>
 *
 * <p>Each position in the grid represents a space which can hold an enum object of type {@link Config.SpaceType}.<br>
 * Possible Space types are:
 * <ul>
 *  <li>WALL : road boundary or off track space</li>
 *  <li>TRACK: road or open track space</li>
 *  <li>FINISH_LEFT, FINISH_RIGHT, FINISH_UP, FINISH_DOWN :  finish line spaces which have to be crossed
 *      in the indicated direction to winn the race.</li>
 * </ul>
 * <p>Beside the board the track contains the list of cars, with their current state (position, velocity, crashed,...)</p>
 *
 * <p>At initialization the track grid data is read from the given track file. The track data must be a
 * rectangular block of text. Empty lines at the start are ignored. Processing stops at the first empty line
 * following a non-empty line, or at the end of the file.</p>
 * <p>Characters in the line represent SpaceTypes. The mapping of the Characters is as follows:</p>
 * <ul>
 *   <li>WALL : '#'</li>
 *   <li>TRACK: ' '</li>
 *   <li>FINISH_LEFT : '&lt;'</li>
 *   <li>FINISH_RIGHT: '&gt;'</li>
 *   <li>FINISH_UP   : '^;'</li>
 *   <li>FINISH_DOWN: 'v'</li>
 *   <li>Any other character indicates the starting position of a car.<br>
 *       The character acts as the id for the car and must be unique.<br>
 *       There are 1 to {@link Config#MAX_CARS} allowed. </li>
 * </ul>
 *
 * <p>All lines must have the same length, used to initialize the grid width).
 * Beginning empty lines are skipped.
 * The the tracks ends with the first empty line or the file end.<br>
 * An {@link InvalidTrackFormatException} is thrown, if
 * <ul>
 *   <li>not all track lines have the same length</li>
 *   <li>the file contains no track lines (grid height is 0)</li>
 *   <li>the file contains more than {@link Config#MAX_CARS} cars</li>
 * </ul>
 *
 * <p>The Track can return a String representing the current state of the race (including car positons)</p>
 */
public class Track {

    public static final char CRASH_INDICATOR = 'X';

    /**
     * Initialize a Track from the given track file.
     *
     * @param trackFile Reference to a file containing the track data
     * @throws FileNotFoundException       if the given track file could not be found
     * @throws InvalidTrackFormatException if the track file contains invalid data (no tracklines, ...)
     */
    public Track(File trackFile) throws FileNotFoundException//, InvalidTrackFormatException
    {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Return the type of space at the given position.
     * If the location is outside the track bounds, it is considered a wall.
     *
     * @param position The coordinates of the position to examine
     * @return The type of track position at the given location
     */
    public Config.SpaceType getSpaceType(PositionVector position) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Return the number of cars.
     *
     * @return Number of cars
     */
    public int getCarCount() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Get instance of specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return The car instance at the given index
     */
    public Car getCar(int carIndex) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Get the id of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A char containing the id of the car
     */
    public char getCarId(int carIndex) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Get the position of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current position
     */
    public PositionVector getCarPos(int carIndex) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Get the velocity of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current velocity
     */
    public PositionVector getCarVelocity(int carIndex) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Gets character at the given position.
     * If there is a crashed car at the position, {@link #CRASH_INDICATOR} is returned.
     *
     * @param y            position Y-value
     * @param x            position X-vlaue
     * @param currentSpace char to return if no car is at position (x,y)
     * @return character representing position (x,y) on the track
     */
    public char getCharAtPosition(int y, int x, Config.SpaceType currentSpace) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Return a String representation of the track, including the car locations.
     *
     * @return A String representation of the track
     */
    public String toString() {
        // TODO implement
        throw new UnsupportedOperationException();
    }
}
