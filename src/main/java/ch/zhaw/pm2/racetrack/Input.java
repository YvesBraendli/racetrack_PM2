package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

import org.beryx.textio.TextIO;



public class Input {
	private TextIO textIO;
	private char[] possiblePlayerMoves = {'1','2','3','4','5','6','7','8','9','h','t','q'};
	
	
	public Input(TextIO textIO) {
		this.textIO = textIO;
	}
	
	
	public File requestTrack(File trackDirectory) {
		File[] trackPathFiles = trackDirectory.listFiles();
	
		HashMap<String, File> files = new HashMap<String, File>();
		for(File file: trackPathFiles) {
			if(file.getName().endsWith(".txt")) {
				files.put(file.getName(), file);
			}
		}
		
		return files.get(textIO.newStringInputReader()
				.withNumberedPossibleValues(new ArrayList<String>(files.keySet()))
				.read("Please select a track:"));	
	}
	
	
	public Config.StrategyType requestPlayerStrategy(char CarID) {
		return textIO.newEnumInputReader(Config.StrategyType.class).read("Car " + CarID + ": Please select your strategy:");
	}
	
//	public char requestPlayerMove() {
//		return textIO.newCharInputReader().(possiblePlayerMoves).read("Please select a move");
//	}
	
	
}
