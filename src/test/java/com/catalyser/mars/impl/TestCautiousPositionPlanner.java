package com.catalyser.mars.impl;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.catalyser.mars.core.Rover;
import com.catalyser.mars.domain.Command;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Direction;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

public class TestCautiousPositionPlanner {

	private static CautiousPositionPlanner planner = CautiousPositionPlanner.getInstance();
	
	// no problem having everything static and statically initialized , since each test runs sequentially and adjusts 
	// the Direction as needed (coordinates are not changed)
	// the moment that changes, we would have to change everything to instance variables, and add each rover to the grid as needed
	
	private static Grid grid = new Grid(10,70);
	
	private static Rover roverAt00 = new Rover(1, new Coordinates(0, 0), Direction.E, Command.parseCommands("M"));
	private static Rover roverAtMaxCoord = new Rover(2, new Coordinates(10, 70), Direction.E, Command.parseCommands("M"));
	private static Rover roverCollider = new Rover(3, new Coordinates(1, 1), Direction.N, Command.parseCommands("M"));
	private static Rover roverCollided = new Rover(4, new Coordinates(1, 2), Direction.E, Command.parseCommands("M"));

	static {
		try {
			grid.addRover(roverAt00);
			grid.addRover(roverAtMaxCoord);
			grid.addRover(roverCollider);
			grid.addRover(roverCollided);
		} catch (BadCoordinatesException | CoordinatesTakenException e) {
			// since we know the rovers we know adding them will not throw any exceptions
			// but just in case...
			throw new IllegalArgumentException("Unexpected error adding rovers for tests");
		}
	}
	
	@Test
	public void testMovingBelowZeroX() {
		roverAt00.setDirection(Direction.W);
		Throwable thrown = catchThrowable(() -> planner.findNextCoordinates(roverAt00, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingBelowZeroY() {
		roverAt00.setDirection(Direction.S);
		Throwable thrown = catchThrowable(() -> planner.findNextCoordinates(roverAt00, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingPastMaxX() {
		roverAt00.setDirection(Direction.E);
		Throwable thrown = catchThrowable(() -> planner.findNextCoordinates(roverAtMaxCoord, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingPastMaxY() {
		roverAt00.setDirection(Direction.N);
		Throwable thrown = catchThrowable(() -> planner.findNextCoordinates(roverAtMaxCoord, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingToOccupiedCoordinates() {
		Throwable thrown = catchThrowable(() -> planner.findNextCoordinates(roverCollider, grid) );
		assertThat(thrown).isInstanceOf(CoordinatesTakenException.class).hasMessageContaining("were already taken");
	}
	
}
