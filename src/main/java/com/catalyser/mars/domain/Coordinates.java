package com.catalyser.mars.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This is an immutable object. New coordinates will be calculated and set by the rover itself.
 * 
 * @author davi
 *
 */
@Data
@AllArgsConstructor
public class Coordinates {
	private final int x;
	private final int y;
}
