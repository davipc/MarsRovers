package com.catalyser.mars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catalyser.mars.core.InputParser;
import com.catalyser.mars.core.RoverManager;
import com.catalyser.mars.domain.Input;
import com.catalyser.mars.impl.TextFileInputParser;

/**
 * This is the main solution class. It reads the input from a file, then kicks the execution of the simulation.
 * 
 * @author davi
 *
 */
public class App {
	
	private static Logger log = LoggerFactory.getLogger(App.class);	
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			log.error("This program requires one argument: the path to the file where the input is defined"); 
			System.exit(1);
		}
		
		InputParser<String> inputParser = new TextFileInputParser();
		Input parsedInput = inputParser.parseInput(args[0]);
		
		RoverManager manager = new RoverManager();
		manager.setInput(parsedInput);
		
		// deploys the rovers and has them execute the commands
		manager.deployRovers();
		log.info("Final rover positions (in same order as input):\n{}", manager.getRoverStatus());
	}
}
