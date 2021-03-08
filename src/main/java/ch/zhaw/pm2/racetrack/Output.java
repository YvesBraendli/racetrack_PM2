package ch.zhaw.pm2.racetrack;

import org.beryx.textio.TextTerminal;

public class Output {
	private TextTerminal<?> textTerminal;

	public Output(TextTerminal<?> textTerminal) {
		this.textTerminal = textTerminal;
	}
	
	public void printTrack(String track) {
		textTerminal.println(track);
	}
	
	public void printDoNotMoveMessage() {
		textTerminal.println("Nothing happened...");
	}
	
	public void printWinner(char carID) {
		textTerminal.println("Car " + carID + " won!");
	}
	
	public void printWelcomeMessage() {
		textTerminal.println("Welcome to Fischbein Racetrack 2021!");
		
	}
	
	public void printCurrentPlayer(char carID) {
		textTerminal.println("Car " + carID + "'s turn");
	}
}
