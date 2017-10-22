package com.catalyser.mars.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.catalyser.mars.core.Rover;
import com.catalyser.mars.domain.Coordinates;
import com.catalyser.mars.domain.Direction;
import com.catalyser.mars.domain.Input;

public class TestStringInputParser {

	private StringInputParser parser = new StringInputParser();

	@Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testNoMaxCoordinates() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM");
    }

    @Test
    public void testNaNMaxCoordinates() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 X\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM");
    }

    @Test
    public void testExtraNumbersMaxCoordinates() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 5 1\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM");
    }

    @Test
    public void testNegativeNumberMaxCoordinates() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 -1\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM");
    }
    
    @Test
    public void testNoRovers() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 5\n");
    }

    @Test
    public void testNaNRoverCoordinates() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 5\n1 2 3 N\nLMLMLMLMM");
    }

    @Test
    public void testBadDirectionRover() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 5\n1 2 U\nLMLMLMLMM");
    }

    @Test
    public void testBadCommandRover() {	
    	thrown.expect(IllegalArgumentException.class);
    	thrown.expectMessage("String input is not in a valid format");
    	
    	parser.parseInput("5 6\n1 2 N\nJMLMLMLMM");
    }

    @Test
    public void testValidInputZeroXMaxCoordinates() {	
    	Input input = parser.parseInput("0 5\n1 2 S\nLMLMLMLMM");
    	assertThat(input).isNotNull();
    	assertThat(input.getMaxCoordinates()).isEqualTo(new Coordinates(0,5));
    	assertThat(input.getRovers()).containsExactly(new Rover(1, new Coordinates(1, 2), Direction.S, "LMLMLMLMM"));
    }

    @Test
    public void testValidInputMultipleDigitsMaxCoordinates() {	
    	Input input = parser.parseInput("100 5000\n1 2 S\nLMLMLMLMM");
    	assertThat(input).isNotNull();
    	assertThat(input.getMaxCoordinates()).isEqualTo(new Coordinates(100,5000));
    	assertThat(input.getRovers()).containsExactly(new Rover(1, new Coordinates(1, 2), Direction.S, "LMLMLMLMM"));
    }

    @Test
    public void testValidInputMultipleDigitsRoverCoordinates() {	
    	Input input = parser.parseInput("5 9\n101 982 S\nLMLMLMLMM");
    	assertThat(input).isNotNull();
    	assertThat(input.getMaxCoordinates()).isEqualTo(new Coordinates(5,9));
    	assertThat(input.getRovers()).containsExactly(new Rover(1, new Coordinates(101, 982), Direction.S, "LMLMLMLMM"));
    }
    
    @Test
    public void testValidInput1Rover() {	
    	Input input = parser.parseInput("5 6\n1 2 N\nRMLMLMLMM");
    	assertThat(input).isNotNull();
    	assertThat(input.getMaxCoordinates()).isEqualTo(new Coordinates(5,6));
    	assertThat(input.getRovers()).containsExactly(new Rover(1, new Coordinates(1, 2), Direction.N, "RMLMLMLMM"));
    }

    // rovers with coordinates higher than the max coordinates will not be validated by the parser
    @Test
    public void testValidInput1RoverCoordsHigherThanMax() {	
    	Input input = parser.parseInput("6 7\n8 9 E\nRMLMLMLMM");
    	assertThat(input).isNotNull();
    	assertThat(input.getMaxCoordinates()).isEqualTo(new Coordinates(6,7));
    	assertThat(input.getRovers()).containsExactly(new Rover(1, new Coordinates(8, 9), Direction.E, "RMLMLMLMM"));
    }

    @Test
    public void testValidInput2Rovers() {	
    	Input input = parser.parseInput("5 6\n1 2 N\nRMLMLMLMM\n6 1 W\nRLMRLMRLMLMRLRMLMRLMR");
    	assertThat(input).isNotNull();
    	assertThat(input.getMaxCoordinates()).isEqualTo(new Coordinates(5,6));
    	assertThat(input.getRovers()).containsExactly(
    			new Rover(1, new Coordinates(1, 2), Direction.N, "RMLMLMLMM"), 
    			new Rover(2, new Coordinates(6, 1), Direction.W, "RLMRLMRLMLMRLRMLMRLMR"));
    }
    
}