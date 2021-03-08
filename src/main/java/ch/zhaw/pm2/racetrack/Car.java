package ch.zhaw.pm2.racetrack;

/**
 * Class representing a car on the racetrack.
 * Uses {@link PositionVector} to store current position on the track grid and current velocity vector.
 * Each car has an identifier character which represents the car on the race track board.
 * Also keeps the state, if the car is crashed (not active anymore). The state can not be changed back to uncrashed.
 * The velocity is changed by providing an acelleration vector.
 * The car is able to calculate the endpoint of its next position and on request moves to it.
 */
public class Car {
    /**
     * Car identifier used to represent the car on the track
     */
    private final char id;
    /**
     * Current position of the car on the track grid using a {@link PositionVector}
     */
    private PositionVector position;
    /**
     * Current velocity of the car using a {@link PositionVector}
     */
    private PositionVector velocity = new PositionVector(0, 0);
    /**
     * Indicator if the car has crashed
     */
    private boolean crashed = false;

    public Car(char id, PositionVector position) {
        this.id = id;
        setPosition(position);
    }

    /**
     * Set this Car position directly, regardless of current position and velocity.
     * This should only be used by the game controller in rare cases to set the crash or winning position.
     * The next position is normaly automatically calculated and set in the {@link #move()} method.
     *
     * @param position The new position to set the car directly to.
     */
    public void setPosition(final PositionVector position) {
    	this.position = position;
    }

    /**
     * Return the position that will apply after the next move at the current velocity.
     * Does not complete the move, so the current position remains unchanged.
     *
     * @return Expected position after the next move
     */
    public PositionVector nextPosition() {
    	int x = position.getX() + velocity.getX();
    	int y = position.getY() + velocity.getY();
    	return new PositionVector(x,y);
//        throw new UnsupportedOperationException();
    }

    /**
     * Add the specified amounts to this cars's velocity.
     * The only acceleration values allowed are -1, 0 or 1 in both axis
     * There are 9 possible acceleration vectors, which are defined in {@link PositionVector.Direction}.
     * Changes only velocity, not position.
     *
     * @param acceleration A Direction vector containing the amounts to add to the velocity in x and y dimension
     */
    public void accelerate(PositionVector.Direction acceleration) {
    	velocity = new PositionVector
    			(velocity.getX()+acceleration.vector.getX(),velocity.getY()+acceleration.vector.getY());
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Update this Car's position based on its current velocity.
     */
    public void move() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Mark this Car as being crashed.
     */
    public void crash() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Returns whether this Car has been marked as crashed.
     *
     * @return Returns true if crash() has been called on this Car, false otherwise.
     */
    public boolean isCrashed() {
        // TODO implement
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the current position of the car.
     * 
     * @return PositionVector, which contains the current position of the actual car.
     */
    public PositionVector getPosition() {
    	return position;
    }
    
}
