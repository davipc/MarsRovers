package com.catalyser.mars;

import com.catalyser.mars.core.InputParser;
import com.catalyser.mars.core.RoverManager;
import com.catalyser.mars.domain.Input;
import com.catalyser.mars.impl.TextFileInputParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
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
