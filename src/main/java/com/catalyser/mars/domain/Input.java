package com.catalyser.mars.domain;

import java.util.List;

import com.catalyser.mars.core.Rover;

public class Input {

	private final Coordinates maxCoordinates;
	private final List<Rover> rovers;

	public Input(Coordinates maxCoordinates, List<Rover> rovers) {
		this.maxCoordinates = maxCoordinates;
		this.rovers = rovers;
	}

	public Coordinates getMaxCoordinates() {
		return maxCoordinates;
	}

	public List<Rover> getRovers() {
		return rovers;
	}
	
	public static InputBuilder builder() {
		return new InputBuilder();
	}
	
	public static class InputBuilder {
		private Coordinates maxCoordinates;
		private List<Rover> rovers;
		
		public InputBuilder() {}
		
		public InputBuilder maxCoordinates(Coordinates coordinates) {
			this.maxCoordinates = coordinates;
			return this;
		}
		
		public InputBuilder rovers(List<Rover> rovers) {
			this.rovers = rovers;
			return this;
		}
		
		public Input build() {
			return new Input(maxCoordinates, rovers);
		}
	}
}
