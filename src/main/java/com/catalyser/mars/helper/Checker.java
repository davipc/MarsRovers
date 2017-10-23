package com.catalyser.mars.helper;

public class Checker {

	/**
	 * Throws an exception if the input parameter is null
	 * @param obj
	 * @param name The name of the parameter (to use in the exception message)
	 */
	public static void checkForNull(Object obj, String name) {
		if (obj == null) {
			throw new IllegalArgumentException(name + " cannot be null");
		}
	}
	
}
