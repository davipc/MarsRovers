package com.catalyser.mars.core;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.domain.Input;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;
import com.catalyser.mars.impl.CautiousPositionPlanner;

/**
 * The manager handles all the rovers being deployed in Mars.
 * Once all are deployed, it asks them one by one to execute their commands.
 *  
 * @author davip
 *
 */
public class RoverManager {

	private static final Logger log = LoggerFactory.getLogger(RoverManager.class);

	private Input input; 

	private List<Rover> deployedRovers =  new ArrayList<>();
	private Grid grid;

	public void setInput(Input input) {
		this.input = input;
	}
	
	/**
	 * Deploys the rover in mars, then immediately start executing the commands on each rover
	 * @param rover
	 */
	public void deployRovers() {
		if (input == null) {
			throw new IllegalStateException("An input must be defined");
		}
		
		Coordinates maxCoord = input.getMaxCoordinates();
		grid = new Grid(maxCoord.getX(), maxCoord.getY());
		
		// put the rovers in the grid, so each rover is aware of each other
		// also set the moving strategy to use
		input.getRovers().stream().forEach(rover -> {
			try {
				grid.addRover(rover);
				rover.setPositionPlanner(CautiousPositionPlanner.getInstance());
				deployedRovers.add(rover);
			} catch (BadCoordinatesException | CoordinatesTakenException e) {
				log.error(String.format("Rover %s could not be deployed", rover), e);
			}
		});
		
		executeCommands();
	}
	
	private void executeCommands() {
		for (Rover rover: deployedRovers) {
			try {
				rover.executeCommands(grid);
			} catch (BadCoordinatesException | CoordinatesTakenException e) {
				log.error(String.format("Error executing rover commands, rover %s will stay put", rover), e);
			}
		}
	}
	
	public String getRoverStatus() {
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		deployedRovers.stream().forEach(rover -> joiner.add(rover.getCoordinates().getX() + " " + rover.getCoordinates().getY() + " " + rover.getDirection()));
		return joiner.toString();
	}
	
}
