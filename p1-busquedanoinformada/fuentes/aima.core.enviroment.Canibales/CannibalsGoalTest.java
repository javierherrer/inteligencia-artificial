package aima.core.environment.Canibales;

import aima.core.search.framework.GoalTest;

/**
 * @author Javier Herrer Torres (NIA: 776609)
 * 
 */
public class CannibalsGoalTest implements GoalTest {
	CannibalsBoard goal = new CannibalsBoard(new int[] { 0, 0, 1, 3, 3});

	public boolean isGoalState(Object state) {
		CannibalsBoard board = (CannibalsBoard) state;
		return board.equals(goal);
	}
}