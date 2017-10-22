package com.catalyser.mars.domain;

import java.util.List;

import com.catalyser.mars.core.Rover;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Input {

	@NonNull
	private Coordinates maxCoordinates;
	
	@NonNull
	private List<Rover> rovers;
	
}
