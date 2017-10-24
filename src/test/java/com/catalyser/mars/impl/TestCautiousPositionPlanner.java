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

	private CautiousPositionPlanner planner = CautiousPositionPlanner.getInstance();
	
	private Grid grid = new Grid(10,70);
	
	@Test
	public void testMovingBelowZeroX() {
		Rover roverAt00 = new Rover(1, new Coordinates(0, 0), Direction.W, Command.parseCommands("M"));
		Throwable thrown = catchThrowable(() -> grid.addRover(roverAt00) );
		assertThat(thrown).isNull();
		thrown = catchThrowable(() -> planner.findNextCoordinates(roverAt00, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingBelowZeroY() {
		Rover roverAt00 = new Rover(1, new Coordinates(0, 0), Direction.S, Command.parseCommands("M"));
		Throwable thrown = catchThrowable(() -> grid.addRover(roverAt00) );
		assertThat(thrown).isNull();
		thrown = catchThrowable(() -> planner.findNextCoordinates(roverAt00, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingPastMaxX() {
		Rover roverAtMaxCoord = new Rover(2, new Coordinates(10, 70), Direction.E, Command.parseCommands("M"));
		Throwable thrown = catchThrowable(() -> grid.addRover(roverAtMaxCoord) );
		assertThat(thrown).isNull();
		thrown = catchThrowable(() -> planner.findNextCoordinates(roverAtMaxCoord, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingPastMaxY() {
		Rover roverAtMaxCoord = new Rover(2, new Coordinates(10, 70), Direction.N, Command.parseCommands("M"));
		Throwable thrown = catchThrowable(() -> grid.addRover(roverAtMaxCoord) );
		assertThat(thrown).isNull();
		thrown = catchThrowable(() -> planner.findNextCoordinates(roverAtMaxCoord, grid) );
		assertThat(thrown).isInstanceOf(BadCoordinatesException.class).hasMessageContaining("Can't move");
	}

	@Test
	public void testMovingToOccupiedCoordinates() {
		Rover roverCollider = new Rover(3, new Coordinates(1, 1), Direction.N, Command.parseCommands("M"));
		Rover roverCollided = new Rover(4, new Coordinates(1, 2), Direction.E, Command.parseCommands("M"));

		Throwable thrown = catchThrowable(() -> {
			grid.addRover(roverCollider);
			grid.addRover(roverCollided);
		} );
		assertThat(thrown).isNull();
		
		thrown = catchThrowable(() -> planner.findNextCoordinates(roverCollider, grid) );
		assertThat(thrown).isInstanceOf(CoordinatesTakenException.class).hasMessageContaining("were already taken");
	}
	
}
