package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.Input;

/**
 * Let the user decide the next move.
 * @author Moser Nadine, Meier Robin, Brändli Yves
 */
public class UserMoveStrategy implements MoveStrategy {
	
	private Input input;
	
	/**
	 * Constructor of the UserMoveStrategy.
	 * @param input used to request player move
	 */
	public UserMoveStrategy(Input input) {
		this.input = input;
	}
	/**
	 * Gets the next user move
	 * @return Direction Value
	 */
    @Override
    public Direction nextMove() {
        switch(input.requestPlayerMove()) {
        	case 1: return Direction.DOWN_LEFT;
        	case 2: return Direction.DOWN;
        	case 3: return Direction.DOWN_RIGHT;
        	case 4: return Direction.LEFT;
        	case 5: return Direction.NONE;
        	case 6: return Direction.RIGHT;
        	case 7: return Direction.UP_LEFT;
        	case 8: return Direction.UP;
        	case 9: return Direction.UP_RIGHT;
        	default: return Direction.NONE;
        }
    }
}
