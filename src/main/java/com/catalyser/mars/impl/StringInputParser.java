package com.catalyser.mars.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catalyser.mars.core.InputParser;
import com.catalyser.mars.core.Rover;
import com.catalyser.mars.domain.Command;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Direction;
import com.catalyser.mars.domain.Input;

/**
 * Reads the input from a string. 
 * The entire String needs to be defined and passed to this reader before it can provide the parsed information 
 * (grid dimensions and rover information).
 * @author davi
 *
 */
public class StringInputParser implements InputParser<String> {

	private static Logger log = LoggerFactory.getLogger(StringInputParser.class);
	
	private int id = 1;
	
	/**
	 * Ensures the input string is in the expected format, so it won't break our scanner
	 * It also checks the max coordinates are not negative (they can be 0, as 0,0 is a valid coordinate 
	 * 
	 * @param str
	 * @throws IllegalArgumentException In case of invalid format 
	 */
	private void validateInputString(String str) {
		log.debug("Validating String input");
		boolean valid = true;

		valid = str.matches("^\\d+ \\d+(\\R\\d+ \\d+ [N|S|E|W]\\R[L|R|M]+)+$");
		
		if (!valid) {
			throw new IllegalArgumentException("String input is not in a valid format");
		}
		log.debug("Finished validating String input");
	}
	
	public Input parseInput(String rawInput) {
		log.info(String.format("Parsing input string %s", rawInput));

		// then validate the whole input
		validateInputString(rawInput);		
		
		// finally proceed to parsing it, populating the appropriate objects
		List<Rover> rovers = new ArrayList<>();
		
		Input.InputBuilder inputBuilder = Input.builder();
		
		Scanner scanner = new Scanner(rawInput);
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		inputBuilder.maxCoordinates(new Coordinates(x, y));
		
		// put scanner at the start of next line, on first rover info
		scanner.nextLine();

		Coordinates roverCoords;
		String roverDirection;
		String commandsStr;
		List<Command> commands;
		while (scanner.hasNext()) {
			// TODO: could use a builder to create the Rover
			roverCoords = new Coordinates(scanner.nextInt(), scanner.nextInt());
			roverDirection = scanner.next();
			scanner.nextLine();
			commandsStr = scanner.nextLine();
			commands = Command.parseCommands(commandsStr);
			rovers.add(new Rover(id++, roverCoords, Direction.valueOf(roverDirection), commands));
		}
		scanner.close();
		inputBuilder.rovers(rovers);
		log.info("Finished parsing input file");
		
		return inputBuilder.build();
	}
}
