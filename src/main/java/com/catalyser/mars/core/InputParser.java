package com.catalyser.mars.core;

import com.catalyser.mars.domain.Input;

/**
 * Defines the method to parsing the input.
 * 
 * @author davi
 *
 */
public interface InputParser<T> {
	
	public Input parseInput(T inputSource) throws Exception;
	
}
