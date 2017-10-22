# Mars Rover #


## Synopsis ##


Mars Rover is a coding challenge provided by Catalyser as part of their interviewing process.

## Requirements ##

You are required to engineer a solution to the problem below. 
The problem below requires input. You are free to implement any mechanism for feeding input into the solution. 
You should provide evidence that your solution is complete by, as a minimum, indicating that it works correctly against the supplied
test data. It is highly recommended that you use a unit testing framework.  
The code you write should be of production quality. There is no perfect solution, the purpose of this
exercise is to see how you go about engineering a solution. Please don’t spend any more than a few
hours on this problem. Feel free to use third party libraries if required.
Solutions can be sent via .zip to your contact (the person you received this problem from), or uploaded
to a Github, Gitlab, or Bitbucket account where we are given access.

#### Mars Rover Problem ####

A squad of robotic rovers are to be landed by NASA on a plateau on Mars. This plateau, which is
curiously rectangular, must be navigated by the rovers so that their on-board cameras can get a
complete view of the surrounding terrain to send back to Earth. A rover's position and location is
represented by a combination of x and y co-ordinates and a letter representing one of the four cardinal
compass points. The plateau is divided up into a grid to simplify navigation. An example position
might be 0, 0, N, which means the rover is in the bottom left corner and facing North. In order to
control a rover, NASA sends a simple string of letters. The possible letters are 'L', 'R' and 'M'. 'L' and
'R' makes the rover spin 90 degrees left or right respectively, without moving from its current spot. 'M'
means move forward one grid point, and maintain the same heading.
Assume that the square directly North from (x, y) is (x, y+1).


#### INPUT ####

The first line of input is the upper-right coordinates of the plateau, the lower-left coordinates are
assumed to be 0,0.
The rest of the input is information pertaining to the rovers that have been deployed. Each rover has
two lines of input. The first line gives the rover's position, and the second line is a series of instructions
telling the rover how to explore the plateau. The position is made up of two integers and a letter
separated by spaces, corresponding to the x and y co-ordinates and the rover's orientation.
Each rover will be finished sequentially, which means that the second rover won't start to move until
the first one has finished moving.

#### OUTPUT ####

The output for each rover should be its final co-ordinates and heading.

#### INPUT AND OUTPUT ####

Test Input:  
5 5  
1 2 N  
LMLMLMLMM  
3 3 E  
MMRMMRMRRM  

Expected Output:  
1 3 N  
5 1 E  

## Assumptions ##

1 - All rovers are deployed before the first one starts executing its commands  
2 - Each rover is deployed with their string of commands  

What happens if:  
1 - multiple rovers are deployed at the same coordinates?  
    - assuming rovers that come after will not be deployed  
   
2 - a rover collides with another rover ?  
    - both break, also blocking the others' movements ?  
    - assuming not... it will rather not move if it detects another rover in the position it is moving to, and no more commands will be executed  
  
3 - a rover tries to go outside the grid area?  
    - assuming the rover will not move past grid boundaries, but no more commands will be executed (to avoid a rogue rover following an unplanned trajectory)  


## Running the tool ##


### Instructions ###

In order to run the application, either: 

* open it in Eclipse and run the App class with the path for the input file

* create an executable package by running this: 


```
#!maven
maven clean install
```

then copy the target/MarsRovers jar anywhere and run the tool on the command line with 


```
#!command line
java -cp <generated jar file>;<jars from dependencies> com.catalyser.mars.App <file path>
```

