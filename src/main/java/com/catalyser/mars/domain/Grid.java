package com.catalyser.mars.domain;

import java.util.HashMap;
import java.util.Map;

import com.catalyser.mars.core.Rover;
import com.catalyser.mars.exceptions.BadCoordinatesException;
import com.catalyser.mars.exceptions.CoordinatesTakenException;

import lombok.Data;
import lombok.NonNull;

/**
 * Stores the grid dimension and the rovers in the surface.
 * @author davi
 *
 */
@Data
public class Grid {

	private final int maxX;

	private final int maxY;
	
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
	public void addRover(@NonNull Rover rover) throws BadCoordinatesException, CoordinatesTakenException {
		// Annotation is already checking for null rover
		
		// check rover coordinates
		if (rover.getCoordinates().getX() < 0 || rover.getCoordinates().getX() > maxX || 
			rover.getCoordinates().getX() < 0 || rover.getCoordinates().getY() > maxY) {
			
			throw new BadCoordinatesException(String.format("Rover coordinates are invalid: %s", rover));
		}
		
		// check there is not another rover at the same position
		boolean coordinatesTaken = isPositionTaken(rover.getCoordinates());
		if (coordinatesTaken) {
			throw new CoordinatesTakenException(String.format("Cannot deploy rover at specified coordinates, it is already taken: %s", rover));
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
	public void updateRoverPosition(@NonNull Rover rover) throws BadCoordinatesException, CoordinatesTakenException {
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
