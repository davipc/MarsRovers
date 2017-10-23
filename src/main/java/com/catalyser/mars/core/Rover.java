package com.catalyser.mars.core;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catalyser.mars.domain.Command;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Direction;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

/**
 * Each instance represents a rover.
 * They know how to execute their commands, and update their positions on the shared grid by themselves (rather than asking the manager to do it). 
 * @author davi
 *
 */
public class Rover {

	private static Logger log = LoggerFactory.getLogger(Rover.class);
	
	private final int id;
	private Coordinates coordinates;
	private Direction direction;
	private List<Command> commands;
	private PositionPlannerStrategy positionPlanner;
	
	public Rover(int id, Coordinates coordinates, Direction direction, List<Command> commands) {
		this.id = id;
		this.coordinates = coordinates;
		this.direction = direction;
		this.commands = commands;
	}
	
	public PositionPlannerStrategy getPositionPlanner() {
		return positionPlanner;
	}
	public void setPositionPlanner(PositionPlannerStrategy positionPlanner) {
		this.positionPlanner = positionPlanner;
	}
	public int getId() {
		return id;
	}
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction newDirection) {
		this.direction = newDirection;
	}
	public List<Command> getCommands() {
		return commands;
	}

	/**
	 * Executes the string of commands sent by NASA
	 * If there are problems during the execution of the commands, these will be reported back through the return parameter
	 * The behavior 
	 * @param grid The position of each rover in the grid before this commands execution
	 */
	public void executeCommands(Grid grid) throws BadCoordinatesException, CoordinatesTakenException {
		log.debug("Executing commands for rover {}", this);
		
		// using basic for loop to avoid having to create a wrapper functional interface for the possible thrown exception on M
		for (Command command: commands) {
			switch(command) {
				case L: 
					direction = direction.rotateLeft();
					break;
				case R: 
					direction = direction.rotateRight();
					break;
				case M: 
					// find the next position for this rover
					// if an exception is thrown by the strategy, the remaining commands will not be executed
					coordinates = positionPlanner.findNextCoordinates(this, grid);
					// the rover itself will update its position in the grid
					grid.updateRoverPosition(this);
			}
		}
		
		log.debug("Finished executing commands for rover {}", this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Rover(")
			.append("id=").append(id)
			.append(", coordinates=").append(coordinates)
			.append(", direction=").append(direction)
			.append(", commands=").append(commands)
			.append(")");
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof Rover))
			return false;
		
		Rover otherRover = (Rover) otherObj;
		
		return id == otherRover.id && 
						coordinates.equals(otherRover.coordinates) && 
						direction.equals(otherRover.direction) && 
						commands.containsAll(otherRover.commands) &&
						(positionPlanner == null && positionPlanner == null || 
							positionPlanner != null && positionPlanner.equals(otherRover.positionPlanner));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, coordinates, direction, commands, positionPlanner);
	}
}
