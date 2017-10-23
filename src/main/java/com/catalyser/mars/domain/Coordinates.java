package com.catalyser.mars.domain;

import java.util.Objects;

/**
 * This is an immutable object. New coordinates will be calculated and set by the rover itself.
 * 
 * @author davi
 *
 */
public class Coordinates {
	private final int x;
	private final int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Coordinates(")
			.append("x=").append(x)
			.append(", y=").append(y)
			.append(")");
		
		return sb.toString(); 
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof Coordinates))
			return false;
		
		Coordinates otherCoord = (Coordinates) otherObj;
		
		return x == otherCoord.x && y == otherCoord.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
}
