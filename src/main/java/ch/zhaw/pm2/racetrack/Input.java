package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

import org.beryx.textio.TextIO;

/**
 * This class is used to get user inputs.
 * 
 * @author Moser Nadine, Meier Robin, Brändli Yves
 *
 */
public class Input {

	private TextIO textIO;

	/**
	 * Constructor for the Input class
	 * 
	 * @param textIO console object
	 */
	public Input(TextIO textIO) {
		this.textIO = textIO;
	}

	/**
	 * Displays a list of possible files in a directory for the user to choose from.
	 * Used for track selection and the follower/move list strategies
	 * 
	 * @param directory the path of the files
	 * @return the selected file as a File object
	 */
	public File requestFile(File directory) {
		File[] trackPathFiles = directory.listFiles();

		HashMap<String, File> files = new HashMap<String, File>();
		for (File file : trackPathFiles) {
			if (file.getName().endsWith(".txt")) {
				files.put(file.getName(), file);
			}
		}

		return files.get(textIO.newStringInputReader().withNumberedPossibleValues(new ArrayList<String>(files.keySet()))
				.read("Please select a track:"));
	}

	/**
	 * Used for strategy selection
	 * 
	 * @param CarID The symbol for the car
	 * @return the selected strategy as an enum value
	 */
	public Config.StrategyType requestPlayerStrategy(char carID) {
		return textIO.newEnumInputReader(Config.StrategyType.class)
				.read("Car " + carID + ": Please select your strategy:");
	}

	/**
	 * Used to select a acceleration value between 1-9
	 * 
	 * @return the acceleration as an int
	 */
	public int requestPlayerMove() {
		return textIO.newIntInputReader().withMinVal(1).withMaxVal(9)
				.read("Please enter an acceleration value between 1 and 9");
	}

}
