package com.catalyser.mars.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.catalyser.mars.core.InputParser;
import com.catalyser.mars.domain.Input;

import lombok.extern.slf4j.Slf4j;

/**
 * Reads the input from a file, then parses it. 
 * The entire String needs to be defined and passed to this reader before it can provide the parsed information 
 * (grid dimensions and rover information).
 * @author davi
 *
 */
@Slf4j
public class TextFileInputParser implements InputParser<String> {

	public Input parseInput(String filePath) throws IOException {
		log.info(String.format("Parsing input file %s", filePath));

		// first get the contents of the file given the input path
		String rawInput = new String(Files.readAllBytes(Paths.get(filePath)));

		StringInputParser strParser = new StringInputParser();
		Input parsedInput = strParser.parseInput(rawInput);
		
		log.info(String.format("Finished parsing input file %s", filePath));

		return parsedInput; 
	}
}
