package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Used to test the car class
 * 
 * @author Moser Nadine, Meier Robin, Brändli Yves
 *
 */
public class CarTest {
	private Car model;
	private static final PositionVector STARTPOSITION = new PositionVector(5, 9);
	private static final char CAR_ID = 'c';

	/**
	 * Equivalence Partitioning: 2 
	 * Sets up the car and tests, if the Setup was
	 * successful
	 */
	@Test
	public void requirementCarSetUpSuccessful() {
		// Test
		assertEquals(STARTPOSITION.getX(), model.getPosition().getX());
		assertEquals(STARTPOSITION.getY(), model.getPosition().getY());
		assertEquals(CAR_ID, model.getID());
	}
	
	/**
	 * Equivalence Partitioning: 25
	 * Tested method: move
	 * Already given Tests
	 */
	@Test
	public void requirementMoveCalculatedRight() {
		// Setup
		requirementCarSetUpSuccessful();
		PositionVector positionChange = PositionVector.Direction.DOWN.vector;
		PositionVector positionAfterMove = new PositionVector(STARTPOSITION.getX()+positionChange.getX(), 
				STARTPOSITION.getY()+positionChange.getY());
		// Modify
		model.accelerate(PositionVector.Direction.DOWN);
		model.move();
		assertEquals(positionAfterMove, model.getPosition());
		
	}

	/**
	 * Equivalence Partitioning: 2 
	 * Tested method: setPosition 
	 * already given tests.
	 */
	@Test
	public void requirementSetUpPositionSuccessful() {
		// Setup
		requirementCarSetUpSuccessful();
		int firstXPosition = 11;
		int firstYPosition = 50;
		int secondXPosition = 15;
		int secondYPosition = 55;
		model.setPosition(new PositionVector(firstXPosition, firstYPosition));
		// Test
		assertEquals(firstXPosition, model.getPosition().getX());
		assertEquals(firstYPosition, model.getPosition().getY());
		// Modify
		model.setPosition(new PositionVector(secondXPosition, secondYPosition));
		// Test
		assertEquals(secondXPosition, model.getPosition().getX());
		assertEquals(secondYPosition, model.getPosition().getY());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tested method: nextPosition 
	 * already given tests.
	 */
	@Test
	public void requirementCalculateAcceleratedPositionWithoutChangingThePosition() {
		// Setup
		requirementCarSetUpSuccessful();
		model.accelerate(PositionVector.Direction.DOWN);
		// Test
		assertFalse(model.getPosition() == model.nextPosition());
		assertEquals(model.getPosition(), STARTPOSITION);
	}

	/**
	 * Equivalence Partitioning: 1 
	 * Tests if the instance variable is Setup to false
	 * after initialize a car. 
	 * already given tests.
	 */
	@Test
	public void requirementCrashedIsFalseAfterSetUp() {
		// Test
		assertFalse(model.isCrashed());
	}

	/**
	 * Equivalence Partitioning: 3 Tested method: crash 
	 * already given tests.
	 */
	@Test
	public void requirementMarkedAsCrashedSuccessfull() {
		// Setup
		setUpCar();
		model.crash();
		// Test
		assertTrue(model.isCrashed());
	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tested method: accelerate
	 */
	@Test
	public void requirementAccelerateWithEveryPossibleParameterChangeVelocityOneTime() {
		// Setup
		PositionVector startPositionAcceleration = new PositionVector();
		// Modify
		model.accelerate(PositionVector.Direction.DOWN);
		// Test
		assertEquals(PositionVector.Direction.DOWN.vector,
				model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.UP);
		// Test
		assertEquals(startPositionAcceleration,
				model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.LEFT);
		// Test
		assertEquals(PositionVector.Direction.LEFT.vector,
				model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.RIGHT);
		// Test
		assertEquals(startPositionAcceleration,
				model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.DOWN_LEFT);
		// Test
		assertEquals(
				PositionVector.Direction.DOWN_LEFT.vector, model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.UP_RIGHT);
		// Test
		assertEquals(startPositionAcceleration,
				model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.DOWN_RIGHT);
		// Test
		assertEquals(
				PositionVector.Direction.DOWN_RIGHT.vector, model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.UP_LEFT);
		// Test
		assertEquals(startPositionAcceleration,
				model.getVelocity());
		// Modify
		model.accelerate(PositionVector.Direction.NONE);
		// Test
		assertEquals(startPositionAcceleration,
				model.getVelocity());
	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards maximum, positive X-Direction (51) and
	 * Y-Direction (15)
	 */
	@Test
	public void requirementCalculateAccelerationCorrectInPositivePossibleXAndYDirectionsOnBoard() {
		// Setup
		int maxXValue = 51;
		for (int i = 0; i < maxXValue; i++) {
			model.accelerate(PositionVector.Direction.RIGHT);
		}
		// Test
		assertEquals(maxXValue,
				model.getVelocity().getX());
		// Modify
		int maxYValue = 15;
		for (int i = 0; i < maxYValue; i++) {
			model.accelerate(PositionVector.Direction.DOWN);
		}
		// Test
		assertEquals(maxYValue,
				model.getVelocity().getY());
	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards maximum, negative X-Direction (51) and
	 * Y-Direction (15)
	 */
	@Test
	public void requirementCalculateAccelerationCorrectInNegativePossibleXAndYDirectionsOnBoard() {
		// Setup
		int maxXValue = 51;
		for (int i = 0; i < maxXValue; i++) {
			model.accelerate(PositionVector.Direction.LEFT);
		}
		// Test
		assertEquals(-maxXValue,
				model.getVelocity().getX());
		// Modify
		int maxYValue = 15;
		for (int i = 0; i < maxYValue; i++) {
			model.accelerate(PositionVector.Direction.UP);
		}
		// Test
		assertEquals( -maxYValue,
				model.getVelocity().getY());
	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards maximum diagonal Up_Right directions (13).
	 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalUpRightDirection() {
		// Setup
		PositionVector endPosition = new PositionVector(13, -13);
		int maxDiagonal = 13;
		for (int i = 0; i < maxDiagonal; i++) {
			model.accelerate(PositionVector.Direction.UP_RIGHT);
		}
		// Test
		assertEquals(
				
				endPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards maximum diagonal Up_Left directions (13).
	 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalUpLeftDirection() {
		// Setup
		PositionVector endPosition = new PositionVector(-13, -13);
		int maxDiagonal = 13;
		for (int i = 0; i < maxDiagonal; i++) {
			model.accelerate(PositionVector.Direction.UP_LEFT);
		}
		// Test
		assertEquals(
				
				endPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards maximum diagonal Down_Right directions
	 * (13).
	 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalDownRightDirection() {
		// Setup
		PositionVector endPosition = new PositionVector(13, 13);
		int maxDiagonal = 13;
		for (int i = 0; i < maxDiagonal; i++) {
			model.accelerate(PositionVector.Direction.DOWN_RIGHT);
		}
		// Test
		assertEquals(
				
				endPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards maximum diagonal Down_Left directions (13).
	 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalDownLeftDirection() {
		// Setup
		PositionVector endPosition = new PositionVector(-13, 13);
		int maxDiagonal = 13;
		for (int i = 0; i < maxDiagonal; i++) {
			model.accelerate(PositionVector.Direction.DOWN_LEFT);
		}
		// Test
		assertEquals(
				
				endPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 
	 * 4 Tests, if the method accelerate calculates the
	 * acceleration right for the boards positive X-maximum direction to negative
	 * X-maximum direction and back.
	 */
	@Test
	public void requirementCalculateAccelerationCorrectFromPositiveXMaximumToNegativXMaximumPosition() {
		// Setup
		PositionVector xPositiveMaximumPosition = new PositionVector(51, 0);
		PositionVector xNegativeMaximumPosition = new PositionVector(-51, 0);
		int maxXValue = 51;
		int maxXValueOneMaxToOther = 102;
		for (int i = 0; i < maxXValue; i++) {
			model.accelerate(PositionVector.Direction.RIGHT);
		}
		// Modify
		for (int i = 0; i < maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.LEFT);
		}
		// Test
		assertEquals(
				
				xNegativeMaximumPosition, model.getVelocity());
		// Modify
		for (int i = 0; i < maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.RIGHT);
		}
		// Test
		assertEquals(
				
				xPositiveMaximumPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards positive Y-maximum direction to negative
	 * Y-maximum direction and back.
	 */
	@Test
	public void requirementCalculateAccelerationCorrectFromPositiveYMaximumToNegativYMaximumPosition() {
		// Setup
		PositionVector xPositiveMaximumPosition = new PositionVector(0, 15);
		PositionVector xNegativeMaximumPosition = new PositionVector(0, -15);
		int maxYValue = 15;
		int maxXValueOneMaxToOther = 30;
		for (int i = 0; i < maxYValue; i++) {
			model.accelerate(PositionVector.Direction.DOWN);
		}
		// Modify
		for (int i = 0; i < maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.UP);
		}
		// Test
		assertEquals(
				
				xNegativeMaximumPosition, model.getVelocity());
		// Modify
		for (int i = 0; i < maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.DOWN);
		}
		// Test
		assertEquals(
			
				xPositiveMaximumPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards positive diagonal maximum up right
	 * direction to diagonal down left direction and back.
	 */
	@Test
	public void requirementCalculateAccelerationCorrectFromDiagonalUpRightToDownLeftPosition() {
		// Setup
		PositionVector upRightPosition = new PositionVector(13, -13);
		PositionVector downLeftPosition = new PositionVector(-13, 13);
		int maxDiagonalValue = 13;
		int maxDiagonalValueOneMaxToOther = 26;
		for (int i = 0; i < maxDiagonalValue; i++) {
			model.accelerate(PositionVector.Direction.UP_RIGHT);
		}
		// Modify
		for (int i = 0; i < maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.DOWN_LEFT);
		}
		// Test
		assertEquals(
				
				downLeftPosition, model.getVelocity());
		// Modify
		for (int i = 0; i < maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.UP_RIGHT);
		}
		// Test
		assertEquals(
				
				upRightPosition, model.getVelocity());

	}

	/**
	 * Equivalence Partitioning: 4 
	 * Tests, if the method accelerate calculates the
	 * acceleration right for the boards positive diagonal maximum up left direction
	 * to diagonal down right direction and back.
	 */
	@Test
	public void requirementCalculateAccelerationCorrectFromDiagonalUpLeftToDownRightPosition() {
		// Setup
		PositionVector upLefttPosition = new PositionVector(-13, -13);
		PositionVector downRightPosition = new PositionVector(13, 13);
		int maxDiagonalValue = 13;
		int maxDiagonalValueOneMaxToOther = 26;
		for (int i = 0; i < maxDiagonalValue; i++) {
			model.accelerate(PositionVector.Direction.UP_LEFT);
		}
		// Modify
		for (int i = 0; i < maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.DOWN_RIGHT);
		}
		// Test
		assertEquals(
				
				downRightPosition, model.getVelocity());
		// Modify
		for (int i = 0; i < maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.UP_LEFT);
		}
		// Test
		assertEquals(upLefttPosition, model.getVelocity());

	}

	/**
	 * Sets up a car model for the testing.
	 */
	@BeforeEach
	public void setUpCar() {
		model = new Car(CAR_ID, STARTPOSITION);
	}

}
