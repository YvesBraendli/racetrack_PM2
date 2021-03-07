package ch.zhaw.pm2.racetrack;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarTest {
	private Car model;

	@Test
	public void requirementSetPositionSuccessfull() {
		int initialXPositon = 5;
		int initialYPositon = 9;
		int switchedXPosition = 11;
		int switchedYPosition = 50;
		//Setup
		setUpCar('c', new PositionVector(initialXPositon, initialYPositon));
		//Test
		assertEquals("SetUp not successfull", initialXPositon, model.getPosition().getX());
		assertEquals("Setup not successfull", initialYPositon, model.getPosition().getY());
		//Change
		model.setPosition(new PositionVector(switchedXPosition, switchedYPosition));
		assertEquals("Wrong new X-Position", switchedXPosition, model.getPosition().getX());
		assertEquals("Wrong new Y-Position", switchedYPosition, model.getPosition().getY());
	}

	private void setUpCar(char id, PositionVector position) {
		model = new Car (id, position);
	}
	
}
