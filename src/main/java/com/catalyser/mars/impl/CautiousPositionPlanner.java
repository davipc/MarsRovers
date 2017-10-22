package com.catalyser.mars.impl;

import com.catalyser.mars.core.PositionPlannerStrategy;
import com.catalyser.mars.core.Rover;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

import lombok.extern.slf4j.Slf4j;

/**
 * Since the rovers are not running concurrently, 
 * we only really need one instance of this, so we will use a singleton.
 * 
 * @author davi
 */
@Slf4j
public class CautiousPositionPlanner implements PositionPlannerStrategy {

	private static CautiousPositionPlanner instance;
	
	public static CautiousPositionPlanner getInstance() {
		if (instance == null) {
			instance = new CautiousPositionPlanner();
		}
		return instance;
	}
	
	private CautiousPositionPlanner() {}
	
	@Override
	public Coordinates findNextCoordinates(Rover rover, Grid grid) throws BadCoordinatesException, CoordinatesTakenException {
		log.debug(String.format("Calculating next position for rover %s", rover));
		
		int newX = rover.getCoordinates().getX();
		int newY = rover.getCoordinates().getY();
		
		switch(rover.getDirection()) {
			case N:
				newY++; break;
			case S:
				newY--; break;
			case E:
				newX++; break;
			case W:
				newX--; break;
		}

		// check if the new position is valid
		if (newX < 0 || newX > grid.getMaxX() || newY < 0 || newY >= grid.getMaxY()) {
			throw new BadCoordinatesException(String.format("Can't move %s from %s", rover.getDirection(), rover.getCoordinates()));
		}
		
		Coordinates newCoordinates = new Coordinates(newX, newY); 

		// finally check if the new position isn't already occupied
		if (grid.isPositionTaken(newCoordinates)) {
			throw new CoordinatesTakenException(String.format("The new coordinates for rover %s at command %s were already taken: %s", rover, rover.getDirection(), newCoordinates ));
		}
		
		log.debug(String.format("Finished calculating next position for rover %s: found %s", rover, newCoordinates));

		return newCoordinates; 
	}

}
