package com.catalyser.mars.exceptions;

public class BadCoordinatesException extends Exception {

	private static final long serialVersionUID = 5941390851166562522L;

	public BadCoordinatesException() {}
	public BadCoordinatesException(String msg) {
		super(msg);
	}
}
