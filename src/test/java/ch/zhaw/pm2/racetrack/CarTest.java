package ch.zhaw.pm2.racetrack;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarTest {
	private Car model;
	private static final PositionVector STARTPOSITION = new PositionVector(5,9);
	private static final char CARID = 'c';

	/**
	 * Equivalence Partitioning
	 * Sets up the car and tests, if the Setup was successful
	 */
	@Test
	public void requirementCarSetUpSuccessful() {
		//Setup
		setUpCar();
		//Test
		assertEquals("Startposition in x at car Setup wrong", STARTPOSITION.getX(), model.getPosition().getX());
		assertEquals("Startposition in y at car Setup wrong", STARTPOSITION.getY(), model.getPosition().getY());
		assertEquals("Car-ID after Setup didn't matched", CARID, model.getID());
		}
	
	/**
	 * Equivalence Partitioning
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
		//Test
		assertEquals("Wrong new X-Position", firstXPosition, model.getPosition().getX());
		assertEquals("Wrong new Y-Position", firstYPosition, model.getPosition().getY());
		// Change
		model.setPosition(new PositionVector(secondXPosition, secondYPosition));
		//Test
		assertEquals("Wrong new X-Position", secondXPosition, model.getPosition().getX());
		assertEquals("Wrong new Y-Position", secondYPosition, model.getPosition().getY());
		
	}
	
	/**
	 * Equivalence Partitioning
	 * Tested method: nextPosition
	 * already given tests.
	 */
	@Test
	public void requirementCalculateAcceleratedPositionWithoutChangingThePosition() {
		requirementCarSetUpSuccessful();
		model.accelerate(PositionVector.Direction.DOWN);
		assertFalse("Position was not calculated right", model.getPosition() == model.nextPosition());
		assertEquals("Position was changed", model.getPosition(), STARTPOSITION);
	}
	
	/**
	 * Equivalence Partitioning
	 * Tests if the instance variable is Setup to false after initialize a car.
	 * already given tests.
	 */
	@Test
	public void requirementCrashedIsFalseAfterSetUp() {
		setUpCar();
		assertFalse("Model already crashed after Setup", model.isCrashed());
	}
	
	/**
	 * Equivalence Partitioning
	 * Tested method: crash
	 * already given tests.
	 */
	@Test
	public void requirementMarkedAsCrashedSuccessfull() {
		setUpCar();
		model.crash();
		assertTrue(model.isCrashed());
	}
	
	/**
Equivalence Partitioning
	 * Tested method: accelerate
	 */
	@Test
	public void requirementAccelerateWithEveryPossibleParameterChangeVelocityOneTime () {
		setUpCar();
		PositionVector startPositionAcceleration = new PositionVector();
		model.accelerate(PositionVector.Direction.DOWN);
		assertEquals("Velocity was not calculated right in Direction DOWN.", PositionVector.Direction.DOWN.vector, model.getVelocity());
		model.accelerate(PositionVector.Direction.UP);
		assertEquals("Velocity was not calculated right in Direction UP.", startPositionAcceleration, model.getVelocity());
		model.accelerate(PositionVector.Direction.LEFT);
		assertEquals("Velocity was not calculated right in Direction LEFT.", PositionVector.Direction.LEFT.vector, model.getVelocity());
		model.accelerate(PositionVector.Direction.RIGHT);
		assertEquals("Velocity was not calculated right in Direction Right.", startPositionAcceleration, model.getVelocity());
		model.accelerate(PositionVector.Direction.DOWN_LEFT);
		assertEquals("Velocity was not calculated right ind Direction DOWN_LEFT.", PositionVector.Direction.DOWN_LEFT.vector, model.getVelocity());
		model.accelerate(PositionVector.Direction.UP_RIGHT);
		assertEquals("Velocity was not calculated right in Direction UP_RIGHT.", startPositionAcceleration, model.getVelocity());
		model.accelerate(PositionVector.Direction.DOWN_RIGHT);
		assertEquals("Velocity was not calculated right in Direction DOWN_RIGHT.", PositionVector.Direction.DOWN_RIGHT.vector, model.getVelocity());
		model.accelerate(PositionVector.Direction.UP_LEFT);
		assertEquals("Velocity was not calculated right in Direction UP_LEFT.", startPositionAcceleration, model.getVelocity());
		model.accelerate(PositionVector.Direction.NONE);
		assertEquals("Velocity was not calculated right in Direction NONE.", startPositionAcceleration, model.getVelocity());
	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards maximum, positive X-Direction (51) and Y-Direction (15)
		 */
	@Test
	public void requirementCalculateAccelerationCorrectInPositivePossibleXAndYDirectionsOnBoard() {
		setUpCar();
		int maxXValue = 51;
		for (int i=0;i<maxXValue;i++) {
			model.accelerate(PositionVector.Direction.RIGHT);
		}
		assertEquals("Velocity was not calculated right to maximum X-Value of the board.", maxXValue, model.getVelocity().getX());
		int maxYValue = 15;
		for (int i=0;i<maxYValue;i++) {
			model.accelerate(PositionVector.Direction.DOWN);
		}
		assertEquals("Velocity was not calculated right to the maximum Y-Value of the board.", maxYValue, model.getVelocity().getY());
	}

	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards maximum, negative X-Direction (51) and Y-Direction (15)
		 */
	@Test
	public void requirementCalculateAccelerationCorrectInNegativePossibleXAndYDirectionsOnBoard() {
		setUpCar();
		int maxXValue = 51;
		for (int i=0;i<maxXValue;i++) {
			model.accelerate(PositionVector.Direction.LEFT);
		}
		assertEquals("Velocity was not calculated right to maximum X-Value of the board.", -maxXValue, model.getVelocity().getX());
		int maxYValue = 15;
		for (int i=0;i<maxYValue;i++) {
			model.accelerate(PositionVector.Direction.UP);
		}
		assertEquals("Velocity was not calculated right to the maximum Y-Value of the board.", -maxYValue, model.getVelocity().getY());
	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards maximum diagonal Up_Right directions (13).
		 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalUpRightDirection() {
		setUpCar();
		PositionVector endPosition = new PositionVector(13, -13);
		int maxDiagonal = 13;
		for (int i=0;i<maxDiagonal;i++) {
			model.accelerate(PositionVector.Direction.UP_RIGHT);
		}
		assertEquals("Velocity was not calculated right to maximum Diagonal-Value of the board with UP_RIGHT command from zero acceleration starting."
				, endPosition, model.getVelocity());

	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards maximum diagonal Up_Left directions (13).
		 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalUpLeftDirection() {
		setUpCar();
		PositionVector endPosition = new PositionVector(-13, -13);
		int maxDiagonal = 13;
		for (int i=0;i<maxDiagonal;i++) {
			model.accelerate(PositionVector.Direction.UP_LEFT);
		}
		assertEquals("Velocity was not calculated right to maximum Diagonal-Value of the board with UP_LEFT command from zero acceleration starting."
				, endPosition, model.getVelocity());

	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards maximum diagonal Down_Right directions (13).
		 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalDownRightDirection() {
		setUpCar();
		PositionVector endPosition = new PositionVector(13, 13);
		int maxDiagonal = 13;
		for (int i=0;i<maxDiagonal;i++) {
			model.accelerate(PositionVector.Direction.DOWN_RIGHT);
		}
		assertEquals("Velocity was not calculated right to maximum Diagonal-Value of the board with DOWN_RIGHT command from zero acceleration starting."
				, endPosition, model.getVelocity());

	}	

	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards maximum diagonal Down_Left directions (13).
		 */
	@Test
	public void requirementCalculateAccelerationCorrectInUpDiagonalDownLeftDirection() {
		setUpCar();
		PositionVector endPosition = new PositionVector(-13, 13);
		int maxDiagonal = 13;
		for (int i=0;i<maxDiagonal;i++) {
			model.accelerate(PositionVector.Direction.DOWN_LEFT);
		}
		assertEquals("Velocity was not calculated right to maximum Diagonal-Value of the board with DOWN_LEFT command from zero acceleration starting."
				, endPosition, model.getVelocity());

	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards positive X-maximum direction to negative X-maximum direction and back.
		 */
	@Test
	public void requirementCalculateAccelerationCorrectFromPositiveXMaximumToNegativXMaximumPosition() {
		setUpCar();
		PositionVector xPositiveMaximumPosition = new PositionVector(51, 0);
		PositionVector xNegativeMaximumPosition = new PositionVector(-51, 0);
		int maxXValue = 51;
		int maxXValueOneMaxToOther = 102;
		for (int i=0;i<maxXValue;i++) {
			model.accelerate(PositionVector.Direction.RIGHT);
		}
		for (int i=0; i<maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.LEFT);
		}
		assertEquals("Velocity was not calculated right to from X-positive, maximum Value to X-negative, maximum Value."
				, xNegativeMaximumPosition, model.getVelocity());
		for (int i=0; i<maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.RIGHT);
		}
		assertEquals("Velocity was not calculated right to from X-negative, maximum Value to X-positive, maximum Value."
				, xPositiveMaximumPosition, model.getVelocity());

	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards positive Y-maximum direction to negative Y-maximum direction and back.
		 */
	@Test
	public void requirementCalculateAccelerationCorrectFromPositiveYMaximumToNegativYMaximumPosition() {
		setUpCar();
		PositionVector xPositiveMaximumPosition = new PositionVector(0, 15);
		PositionVector xNegativeMaximumPosition = new PositionVector(0, -15);
		int maxYValue = 15;
		int maxXValueOneMaxToOther = 30;
		for (int i=0;i<maxYValue;i++) {
			model.accelerate(PositionVector.Direction.DOWN);
		}
		for (int i=0; i<maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.UP);
		}
		assertEquals("Velocity was not calculated right to from Y-positive, maximum Value to Y-negative, maximum Value."
				, xNegativeMaximumPosition, model.getVelocity());
		for (int i=0; i<maxXValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.DOWN);
		}
		assertEquals("Velocity was not calculated right to from Y-negative, maximum Value to Y-positive, maximum Value."
				, xPositiveMaximumPosition, model.getVelocity());

	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards positive diagonal maximum up right direction to diagonal down left direction and back.
		 */
	@Test
	public void requirementCalculateAccelerationCorrectFromDiagonalUpRightToDownLeftPosition() {
		setUpCar();
		PositionVector upRightPosition = new PositionVector(13, -13);
		PositionVector downLeftPosition = new PositionVector(-13, 13);
		int maxDiagonalValue = 13;
		int maxDiagonalValueOneMaxToOther = 26;
		for (int i=0;i<maxDiagonalValue;i++) {
			model.accelerate(PositionVector.Direction.UP_RIGHT);
		}
		for (int i=0; i<maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.DOWN_LEFT);
		}
		assertEquals("Velocity was not calculated right to from maximum up right position to maximum down left position."
				, downLeftPosition, model.getVelocity());
		for (int i=0; i<maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.UP_RIGHT);
		}
		assertEquals("Velocity was not calculated right to from maximum down left position to maximum up right position."
				, upRightPosition, model.getVelocity());

	}
	
	/**
	Equivalence Partitioning
		 * Tests, if the method accelerate calculates the acceleration right for the boards positive diagonal maximum up left direction to diagonal down right direction and back.
		 */
	@Test
	public void requirementCalculateAccelerationCorrectFromDiagonalUpLeftToDownRightPosition() {
		setUpCar();
		PositionVector upLefttPosition = new PositionVector(-13, -13);
		PositionVector downRightPosition = new PositionVector(13, 13);
		int maxDiagonalValue = 13;
		int maxDiagonalValueOneMaxToOther = 26;
		for (int i=0;i<maxDiagonalValue;i++) {
			model.accelerate(PositionVector.Direction.UP_LEFT);
		}
		for (int i=0; i<maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.DOWN_RIGHT);
		}
		assertEquals("Velocity was not calculated right to from maximum up left position to maximum down right position."
				, downRightPosition, model.getVelocity());
		for (int i=0; i<maxDiagonalValueOneMaxToOther; i++) {
			model.accelerate(PositionVector.Direction.UP_LEFT);
		}
		assertEquals("Velocity was not calculated right to from maximum down right position to maximum up left position."
				, upLefttPosition, model.getVelocity());

	}
	
	private void setUpCar() {
		model = new Car (CARID, STARTPOSITION);
	}
	
}
