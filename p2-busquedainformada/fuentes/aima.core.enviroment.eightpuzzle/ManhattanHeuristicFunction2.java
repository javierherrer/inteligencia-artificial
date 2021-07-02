package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard goalBoard = EightPuzzleBoard.goal;
		
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation locgoal = goalBoard.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(i, loc, locgoal);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(int i, XYLocation loc, 
										   XYLocation locgoal) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int xgoalpos = locgoal.getXCoOrdinate();
		int ygoalpos = locgoal.getYCoOrdinate();
		
		retVal = Math.abs(xpos - xgoalpos) + Math.abs(ypos - ygoalpos);
		
		return retVal;
	}
}