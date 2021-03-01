package ch.zhaw.pm2.racetrack.strategy;

import static ch.zhaw.pm2.racetrack.PositionVector.Direction;

/**
 * Do not accelerate in any direction.
 */
public class DoNotMoveStrategy implements MoveStrategy {

    @Override
    public Direction nextMove() {
        // TODO implement
        throw new RuntimeException();
    }
}
