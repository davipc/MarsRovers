package com.catalyser.mars.domain;

public enum Direction {
	N("North"), E("East"), S("South"), W("West");
	
	private static Direction[] vals = values();
	
	private String description;
	
	private Direction(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

    public Direction rotateRight()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }
	
    public Direction rotateLeft()
    {
        return vals[(this.ordinal()+ vals.length -1) % vals.length];
    }
	
}
