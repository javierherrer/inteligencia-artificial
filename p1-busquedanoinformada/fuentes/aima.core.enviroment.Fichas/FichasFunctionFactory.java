package aima.core.environment.Fichas;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.StepCostFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class FichasFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;
	private static StepCostFunction _stepCostFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new FichasActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new FichasResultFunction();
		}
		return _resultFunction;
	}
	
	public static StepCostFunction getStepCostFunction() {
		if (null == _stepCostFunction) {
			_stepCostFunction = new FichasStepCostFunction();
		}
		return _stepCostFunction;
	}

	private static class FichasActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			FichasBoard board = (FichasBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(FichasBoard.LEFT1)) {
				actions.add(FichasBoard.LEFT1);
			}
			if (board.canMoveGap(FichasBoard.LEFT2)) {
				actions.add(FichasBoard.LEFT2);
			}
			if (board.canMoveGap(FichasBoard.LEFT3)) {
				actions.add(FichasBoard.LEFT3);
			}
			if (board.canMoveGap(FichasBoard.RIGHT1)) {
				actions.add(FichasBoard.RIGHT1);
			}
			if (board.canMoveGap(FichasBoard.RIGHT2)) {
				actions.add(FichasBoard.RIGHT2);
			}
			if (board.canMoveGap(FichasBoard.RIGHT3)) {
				actions.add(FichasBoard.RIGHT3);
			}

			return actions;
		}
	}

	private static class FichasResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			FichasBoard board = (FichasBoard) s;

			if (FichasBoard.LEFT1.equals(a)
					&& board.canMoveGap(FichasBoard.LEFT1)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft(1);
				return newBoard;
			} else if (FichasBoard.LEFT2.equals(a)
					&& board.canMoveGap(FichasBoard.LEFT2)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft(2);
				return newBoard;
			} else if (FichasBoard.LEFT3.equals(a)
					&& board.canMoveGap(FichasBoard.LEFT3)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft(3);
				return newBoard;
			} else if (FichasBoard.RIGHT1.equals(a)
					&& board.canMoveGap(FichasBoard.RIGHT1)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight(1);
				return newBoard;
			} else if (FichasBoard.RIGHT2.equals(a)
					&& board.canMoveGap(FichasBoard.RIGHT2)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight(2);
				return newBoard;
			} else if (FichasBoard.RIGHT3.equals(a)
					&& board.canMoveGap(FichasBoard.RIGHT3)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight(3);
				return newBoard;
			}
			

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
	
	private static class FichasStepCostFunction implements StepCostFunction {
		public double c(Object s, Action a, Object sDelta) {
			FichasBoard board = (FichasBoard) s;
			double cost = 0;
			
			if (FichasBoard.LEFT1.equals(a) ||
					FichasBoard.RIGHT1.equals(a)) {
				cost = 1;
			} else if (FichasBoard.LEFT2.equals(a) ||
					FichasBoard.RIGHT2.equals(a)) {
				cost = 2;
			} else if (FichasBoard.LEFT3.equals(a) ||
					FichasBoard.RIGHT3.equals(a)) {
				cost = 3;
			}
			
			return cost;
		}	
	}
}