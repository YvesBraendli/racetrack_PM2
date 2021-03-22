package ch.zhaw.pm2.racetrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.pm2.racetrack.Config.StrategyType;
import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.strategy.*;

public class GameController {
	private Input input;
	private Output output;
	private HashMap<Character, MoveStrategy> strategies;
	private Game game;
	private Config config;

	public GameController() {
		config = new Config();
		strategies = new HashMap<>();
		TextIO textIO = TextIoFactory.getTextIO();
		TextTerminal<?> textTerminal = textIO.getTextTerminal();
		input = new Input(textIO);
		output = new Output(textTerminal);
	}

	public static void main(String[] args) {
		GameController gameController = new GameController();
		gameController.setup();
		gameController.run();
	}

	private void setup() {
		output.printWelcomeMessage();
		Track track = selectTrack();
		int numberOfCars = track.getCarCount();
		game = new Game(numberOfCars, track);
		selectStrategies(numberOfCars);
	}

	private Track selectTrack() {
		Track track = null;
		boolean isPossibleTrack = false;
		while (!isPossibleTrack) {
			File trackDirectory = config.getTrackDirectory();
			File selectedFile = input.requestFile(trackDirectory, "Please select a track:");
			try {
				track = new Track(selectedFile);
				isPossibleTrack = true;
			} catch (FileNotFoundException e) {
				output.printErrorFileNotFoundMessage();
			} catch (InvalidTrackFormatException e) {
				output.printErrorInvalidSymbolsMessage();
			}
		}
		return track;
	}

	private void selectStrategies(int numberOfCars) {
		boolean hasMinOneStrategyWithMovement = false;
		while (!hasMinOneStrategyWithMovement) {
			for (int i = 0; i < numberOfCars; i++) {
				char carId = game.getCarId(i);
				StrategyType selectedStrategyType = input.requestPlayerStrategy(carId);
				MoveStrategy selectedStrategy = null;
				switch (selectedStrategyType) {
				case DO_NOT_MOVE:
					selectedStrategy = new DoNotMoveStrategy();
					break;
				case PATH_FOLLOWER:
					File selectedFile = input.requestFile(config.getFollowerDirectory(), "Please select a follower File:");
					PositionVector startPosition = game.getCarPosition(game.getCurrentCarIndex());
					try {
						selectedStrategy = new PathFollowerMoveStrategy(selectedFile, startPosition);
					} catch (FileNotFoundException e) {
						output.printErrorFileNotFoundMessage();
					} catch (InvalidMoveFormatException e) {
						output.printErrorInvalidSymbolsMessage();
					}
					break;
				case USER:
					selectedStrategy = new UserMoveStrategy(input);
					break;
				case MOVE_LIST:
					boolean isPossibleSelection = false;
					while (!isPossibleSelection) {
						File seletedMoveFile = selectMoveFile();
						try {
							selectedStrategy = new MoveListStrategy(seletedMoveFile);
							isPossibleSelection = true;
						} catch (FileNotFoundException e) {
							output.printErrorFileNotFoundMessage();
						} catch (InvalidMoveFormatException e) {
							output.printErrorInvalidSymbolsMessage();
						}
					}
					break;

				}
				strategies.put(carId, selectedStrategy);

				if (StrategyType.DO_NOT_MOVE != selectedStrategyType) {
					hasMinOneStrategyWithMovement = true;
				}
			}
		}
	}

	private File selectMoveFile() {
		File moveDirectory = config.getMoveDirectory();
		File selectedFile = input.requestFile(moveDirectory, "Please select a file with moves:");

		return selectedFile;
	}

	private void run() {
		boolean isFinished = false;
		while (!isFinished) {
			output.printTrack(game.getTrack().toString());
			int currentCarIndex = game.getCurrentCarIndex();
			char currentPlayer = game.getCarId(currentCarIndex);

			output.printCurrentPlayer(currentPlayer);
			MoveStrategy currentStrategy = strategies.get(currentPlayer);
			if (currentStrategy instanceof DoNotMoveStrategy) {
				output.printDoNotMoveMessage();
			} else if(currentStrategy instanceof PathFollowerMoveStrategy || currentStrategy instanceof MoveListStrategy) {
				output.printAutomatedMoveMessage();
			} else {
				output.printAccelerationGrid();
			}
			Direction nextAcceleration = currentStrategy.nextMove();
			game.doCarTurn(nextAcceleration);

			int winner = game.getWinner();
			if (Game.NO_WINNER != winner) {
				output.printTrack(game.getTrack().toString());
				output.printWinner(game.getCarId(winner));
				input.requestGameClosure();
				return;
			}
			game.switchToNextActiveCar();
		}
	}
}
