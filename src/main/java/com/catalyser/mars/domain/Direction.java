package com.catalyser.mars.domain;

public enum Direction {
	N("North"), S("South"), E("East"), W("West");
	
	private String description;
	
	private Direction(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Direction rotateRight() {
		switch(this) {
			case N: return E;
			case E: return S;
			case S: return W;
			case W: return N;
			// should never happen
			default: return N;
		}
	}

	public Direction rotateLeft() {
		switch(this) {
			case N: return W;
			case W: return S;
			case S: return E;
			case E: return N;
			// should never happen
			default: return N;
		}
	}
	
	// could use this kind of construct as well... but would be exposed to bugs in case new constants were added to the enum
//	private static Direction[] vals = values();
//    public Direction rotateRight()
//    {
//        return vals[(this.ordinal()+1) % vals.length];
//    }
	
}
