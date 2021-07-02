package aima.core.environment.Canibales;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Javier Herrer Torres (NIA: 776609)
 */
public class CannibalsFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new CannibalsActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new CannibalsResultFunction();
		}
		return _resultFunction;
	}

	private static class CannibalsActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			CannibalsBoard board = (CannibalsBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMove(CannibalsBoard.MOVE1C)) {
				actions.add(CannibalsBoard.MOVE1C);
			}
			if (board.canMove(CannibalsBoard.MOVE2C)) {
				actions.add(CannibalsBoard.MOVE2C);
			}
			if (board.canMove(CannibalsBoard.MOVE1M)) {
				actions.add(CannibalsBoard.MOVE1M);
			}
			if (board.canMove(CannibalsBoard.MOVE2M)) {
				actions.add(CannibalsBoard.MOVE2M);
			}
			if (board.canMove(CannibalsBoard.MOVE1M1C)) {
				actions.add(CannibalsBoard.MOVE1M1C);
			}

			return actions;
		}
	}

	private static class CannibalsResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CannibalsBoard board = (CannibalsBoard) s;

			if (CannibalsBoard.MOVE1C.equals(a)
					&& board.canMove(CannibalsBoard.MOVE1C)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move1C();
				return newBoard;
			} else if (CannibalsBoard.MOVE2C.equals(a)
					&& board.canMove(CannibalsBoard.MOVE2C)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move2C();
				return newBoard;
			} else if (CannibalsBoard.MOVE1M.equals(a)
					&& board.canMove(CannibalsBoard.MOVE1M)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move1M();
				return newBoard;
			} else if (CannibalsBoard.MOVE2M.equals(a)
					&& board.canMove(CannibalsBoard.MOVE2M)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move2M();
				return newBoard;
			} else if (CannibalsBoard.MOVE1M1C.equals(a)
					&& board.canMove(CannibalsBoard.MOVE1M1C)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move1M1C();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}