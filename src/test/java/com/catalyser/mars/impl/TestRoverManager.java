package com.catalyser.mars.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.catalyser.mars.core.Rover;
import com.catalyser.mars.core.RoverManager;
import com.catalyser.mars.domain.Command;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Direction;
import com.catalyser.mars.domain.Input;

public class TestRoverManager {

	private RoverManager manager = new RoverManager();
	
	@Test
	public void testSuccessfulDeployment() {
		Input inputSample = Input.builder().maxCoordinates(new Coordinates(5, 5)).rovers(
				Arrays.asList(
						new Rover(1, new Coordinates(1, 2), Direction.N, Command.parseCommands("LMLMLMLMM")),
						new Rover(2, new Coordinates(3, 3), Direction.E, Command.parseCommands("MMRMMRMRRM"))
				)).build();


		manager.setInput(inputSample);
		
		manager.deployRovers();
		assertThat(manager.getRoverStatus()).isEqualTo("1 3 N" + System.lineSeparator() + "5 1 E");
	}

	// only the first rover sent to a coordinate is deployed 
	@Test
	public void testError2RoversSameCoordinates() {
		Input input2RoversSameCoord = Input.builder().maxCoordinates(new Coordinates(5, 5)).rovers(
				Arrays.asList(
						new Rover(1, new Coordinates(1, 2), Direction.N, Command.parseCommands("LMLMLMLMM")),
						new Rover(2, new Coordinates(1, 2), Direction.E, Command.parseCommands("MMRMMRMRRM"))
				)).build();

		manager.setInput(input2RoversSameCoord);
		
		manager.deployRovers();
		assertThat(manager.getRoverStatus()).isEqualTo("1 3 N");
	}
	
	
	@Test
	public void testErrorRoverMovingOutOfSurface() {
		// first rover will try to get past the border at 1 5
		Input inputRoverMovingOut = Input.builder().maxCoordinates(new Coordinates(5, 5)).rovers(
				Arrays.asList(
						new Rover(1, new Coordinates(1, 5), Direction.N, Command.parseCommands("RMMMMM")),
						new Rover(2, new Coordinates(1, 2), Direction.W, Command.parseCommands("LM"))
				)).build();

		manager.setInput(inputRoverMovingOut);
		
		manager.deployRovers();
		
		// first rover stopped when reached the border, second rover performed all commands
		assertThat(manager.getRoverStatus()).isEqualTo("1 5 E" + System.lineSeparator() + "1 1 S");
	}

	@Test
	public void testErrorRoverColliding() {
		// second rover will get a position that would make it collide with the first
		Input inputRoverColliding = Input.builder().maxCoordinates(new Coordinates(5, 5)).rovers(
				Arrays.asList(
						new Rover(1, new Coordinates(1, 2), Direction.S, Command.parseCommands("M")),
						new Rover(2, new Coordinates(2, 1), Direction.W, Command.parseCommands("M"))
				)).build();
		
		manager.setInput(inputRoverColliding);
		
		manager.deployRovers();

		// second rover stopped when detected it would collide with the first, first one performed all commands
		assertThat(manager.getRoverStatus()).isEqualTo("1 1 S" + System.lineSeparator() + "2 1 W");
	}
	
}
