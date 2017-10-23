package com.catalyser.mars.domain;

import java.util.HashMap;
import java.util.Map;

import com.catalyser.mars.core.Rover;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;
import com.catalyser.mars.helper.Checker;

/**
 * Stores the grid dimension and the rovers in the surface.
 * @author davi
 *
 */
public class Grid {

	private final int maxX;

	private final int maxY;
	
	public Grid(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	// we will go with a map rather than a matrix
	// reason: we assume the grid dimension could be large
	// pro: small memory footprint for storing the rovers
	// cons: when checking if there is already a rover in the position we are moving to,
	// the search will take O(n) each time instead of the O(1) we would have using a matrix implementation 
	private Map<Integer, Coordinates> mapTakenCoords = new HashMap<>();

	/**
	 * Adds a rover to the plateau grid
	 * @param rover
	 * @throws BadCoordinatesException If the specified coordinates are not valid
	 * @throws CoordinatesTakenException If there is a rover already at the specified coordinates
	 */
	public void addRover(Rover rover) throws BadCoordinatesException, CoordinatesTakenException {
		Checker.checkForNull(rover, "rover");
		
		// check rover coordinates
		if (rover.getCoordinates().getX() < 0 || rover.getCoordinates().getX() > maxX || 
			rover.getCoordinates().getX() < 0 || rover.getCoordinates().getY() > maxY) {
			
			throw new BadCoordinatesException(String.format("Rover coordinates are invalid: %s", rover));
		}
		
		// check there is not another rover at the same position
		boolean coordinatesTaken = isPositionTaken(rover.getCoordinates());
		if (coordinatesTaken) {
			throw new CoordinatesTakenException(String.format("Cannot deploy rover at specified coordinates, the position is already taken: %s", rover));
		}
		
		// if we got to this point it's all good, add the rover
		mapTakenCoords.put(rover.getId(), rover.getCoordinates());
	}
	
	/**
	 * Changes the position of the rover in the grid.
	 * @param rover
	 * @throws BadCoordinatesException see {@link #addRover(Rover)}
	 * @throws CoordinatesTakenException see {@link #addRover(Rover)}
	 */
	public void updateRoverPosition(Rover rover) throws BadCoordinatesException, CoordinatesTakenException {
		Checker.checkForNull(rover, "rover");
		
		mapTakenCoords.remove(rover.getId());
		addRover(rover);
	}
	
	/**
	 * Checks if the input coordinates are already taken by a distinct rover.
	 * @param coord
	 * @return True if they are taken, false otherwise.
	 */
	public boolean isPositionTaken(Coordinates coord) {
		return mapTakenCoords.values().stream().anyMatch(takenCoord -> takenCoord.equals(coord));
	}
}
