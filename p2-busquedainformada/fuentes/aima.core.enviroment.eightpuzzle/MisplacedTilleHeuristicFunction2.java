package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class MisplacedTilleHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard goalBoard = EightPuzzleBoard.goal;
		return getNumberOfMisplacedTiles(board, goalBoard);
	}

	private int getNumberOfMisplacedTiles(EightPuzzleBoard board, 
										  EightPuzzleBoard goalBoard) {
		int numberOfMisplacedTiles = 0;
		if (!(board.getLocationOf(0).equals(goalBoard.getLocationOf(0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(1).equals(goalBoard.getLocationOf(1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(2).equals(goalBoard.getLocationOf(2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(3).equals(goalBoard.getLocationOf(3)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(4).equals(goalBoard.getLocationOf(4)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(5).equals(goalBoard.getLocationOf(5)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(6).equals(goalBoard.getLocationOf(6)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(7).equals(goalBoard.getLocationOf(7)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(8).equals(goalBoard.getLocationOf(8)))) {
			numberOfMisplacedTiles++;
		}
		// Subtract the gap position from the # of misplaced tiles
		// as its not actually a tile (see issue 73).
		if (numberOfMisplacedTiles > 0) {
			numberOfMisplacedTiles--;
		}
		return numberOfMisplacedTiles;
	}
}