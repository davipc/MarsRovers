package com.catalyser.mars.core;

import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Grid;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

public interface PositionPlannerStrategy {
	public Coordinates findNextCoordinates(Rover rover, Grid grid) throws BadCoordinatesException, CoordinatesTakenException;
}
