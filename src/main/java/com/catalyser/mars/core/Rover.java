package com.catalyser.mars.core;

import java.util.List;

import com.catalyser.mars.domain.Command;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Direction;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(exclude="positionPlanner")
public class Rover {
	private final int id;
	
	@NonNull
	private Coordinates coordinates;
	
	@NonNull
	private Direction direction;
	
	@NonNull
	private List<Command> commands;
	
	private PositionPlannerStrategy positionPlanner;
	
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
}
