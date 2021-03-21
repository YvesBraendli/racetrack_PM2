package ch.zhaw.pm2.racetrack.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import ch.zhaw.pm2.racetrack.PositionVector.Direction;

/**
* Implementation of MoveStrategy utilizing a seperate move list.
* @author Moser Nadine, Meier Robin, Brändli Yves
*/
public class MoveListStrategy implements MoveStrategy {
	

	private ArrayList<String> moveList;
	private int turnCount;
	private Scanner moveReader;
	
	/**
	 * Constructor of the MoveListStrategy class. Loads the move list file into an ArrayList 
	 * if they follow the correct syntax.
	 * @param moveListFile the file containing the moves
	 * @throws FileNotFoundException if the file does note exist
	 * @throws InvalidMoveFormatException if the content does not comply with the Direction Enum values
	 */
	public MoveListStrategy(File moveListFile) throws FileNotFoundException, InvalidMoveFormatException {
		if(!moveListFile.exists()) throw new FileNotFoundException("File does not exist in the given directory");
		moveReader = new Scanner(moveListFile);
		moveList = new ArrayList<String>();
		
		while(moveReader.hasNextLine()) {
			moveList.add(moveReader.nextLine());
		}
		moveReader.close();
		
		for(String move: moveList) {
			boolean isValidMove = false;
			for(Direction direction: Direction.values()) {
				if(move.equals(direction.toString())) {
					isValidMove = true;
				}
			}
			if(!isValidMove) throw new InvalidMoveFormatException("Data File contains invalid symbols.");
		}
	}

	/**
	 * Used to get the next entry in the moveList ArrayList.
	 * @return the next direction value in the list
	 */
    @Override
    public Direction nextMove() {
    	if(!(turnCount >= moveList.size())) {
    		int oldTurnCount = turnCount;
    		turnCount = turnCount+1;
    		return Direction.valueOf(moveList.get(oldTurnCount));
    	}
    	else {
    		return Direction.NONE;
    	}
    }
}
