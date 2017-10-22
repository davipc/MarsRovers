package com.catalyser.mars.exceptions;

public class CoordinatesTakenException extends Exception {

	private static final long serialVersionUID = 3850961819784267012L;

	public CoordinatesTakenException() {}
	public CoordinatesTakenException(String msg) {
		super(msg);
	}
}
