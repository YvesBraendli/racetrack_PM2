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

	private void setUpCar() {
		model = new Car (CARID, STARTPOSITION);
	}
	
}
