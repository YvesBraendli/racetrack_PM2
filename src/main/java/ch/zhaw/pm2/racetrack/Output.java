package ch.zhaw.pm2.racetrack;

import org.beryx.textio.TextTerminal;

/**
 * This class is used for all the outputs to the console which do not require
 * player input.
 * 
 * @author Moser Nadine, Meier Robin, Brändli Yves
 *
 */
public class Output {
	private TextTerminal<?> textTerminal;

	/**
	 * The constructor for the Output class
	 * 
	 * @param textTerminal the terminal
	 */
	public Output(TextTerminal<?> textTerminal) {
		this.textTerminal = textTerminal;
	}

	/**
	 * Prints the track
	 * 
	 * @param track
	 */
	public void printTrack(String track) {
		textTerminal.print(track);
	}

	/**
	 * Message for the do not move strategy
	 */
	public void printDoNotMoveMessage() {
		textTerminal.println("Nothing happened...");
	}

	/**
	 * Prints the winner
	 * 
	 * @param carID the winners symbol
	 */
	public void printWinner(char carID) {
		textTerminal.println("\nCar " + carID + " won!");
	}

	/**
	 * Prints the welcome message
	 */
	public void printWelcomeMessage() {
		textTerminal.println("Welcome to Fischbein Racetrack 2021!");

	}

	/**
	 * Prints a turn switch message
	 * 
	 * @param carID the symbol of the car whose turn it is
	 */
	public void printCurrentPlayer(char carID) {
		textTerminal.println("\n\nCar " + carID + "'s turn:");
	}

	/**
	 * Prints the Acceleration help page
	 */
	public void printAccelerationGrid() {
		textTerminal.println("7 8 9	7=up-left 8=up 9=up-right\n" + "4 5 6	4=left 5=no acceleration 6=right\n"
				+ "1 2 3	1=down-left 2=down 3=down-right");

	}

	/**
	 * Prints the Track selection request
	 */
	public void printTrackSelectionMessage() {
		textTerminal.println("Please select a track:");
	}
	
	/**
	 * Prints the MoveList selection request
	 * 
	 * @param carID the cars symbol for which the move list is selected for
	 */
	public void printMoveListSelectionMessage(char carID) {
		textTerminal.println("Please select a move list for car " + carID + ":");
	}

	/**
	 * Prints the WayPoint selection request
	 * 
	 * @param carID the cars symbol for which the waypoint list is selected for
	 */
	public void printWayPointListSelectionMessage(char carID) {
		textTerminal.println("Please select a waypoint list to follow for car " + carID + ":");
	}
	
	/**
	 * Prints the error message that the selected file was not found
	 */
	public void printErrorFileNotFoundMessage() {
		textTerminal.println("Selected File was not found");
	}
	
	/**
	 * Prints the error message that the selected file contains invalid symbols
	 */
	public void printErrorInvalidSymbolsMessage() {
		textTerminal.println("Selected File contains invalid symbols");
	}
}
