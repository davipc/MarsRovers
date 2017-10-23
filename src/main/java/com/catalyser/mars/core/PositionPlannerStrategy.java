package com.catalyser.mars.core;

import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

/**
 * Classes implementing this interface will be used to determine the next rover position - but will not move the rover. 
 * 
 * @author davi
 *
 */
public interface PositionPlannerStrategy {
	public Coordinates findNextCoordinates(Rover rover, Grid grid) throws BadCoordinatesException, CoordinatesTakenException;
}
