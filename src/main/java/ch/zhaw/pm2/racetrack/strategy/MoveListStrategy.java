package ch.zhaw.pm2.racetrack.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.PositionVector.Direction;

public class MoveListStrategy implements MoveStrategy {
	

	private ArrayList<String> moveList;
	private int turnCount;
	private Scanner moveReader;
	
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
			for(PositionVector.Direction direction: PositionVector.Direction.values()) {
				if(move.equals(direction.toString())) {
					isValidMove = true;
				}
			}
			if(!isValidMove) throw new InvalidMoveFormatException("Data File contains invalid symbols."); //TODO check if a general exception would make more sense
		}
	}

    @Override
    public Direction nextMove() {
        // TODO implement
    	
        throw new RuntimeException();
    }
}
