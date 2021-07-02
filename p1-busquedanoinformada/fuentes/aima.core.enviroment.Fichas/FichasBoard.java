package aima.core.environment.Fichas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class FichasBoard {

	public static Action LEFT1 = new DynamicAction("Left1");

	public static Action LEFT2 = new DynamicAction("Left2");
	
	public static Action LEFT3 = new DynamicAction("Left3");
	
	public static Action RIGHT1 = new DynamicAction("Right1");

	public static Action RIGHT2 = new DynamicAction("Right2");
	
	public static Action RIGHT3 = new DynamicAction("Right3");


	public static final int TOKEN_B = 1;
	public static final int TOKEN_V = 2;
	public static final int TOKEN_EMPTY = 0;
	
	private int[] state;

	//
	// PUBLIC METHODS
	//

	public FichasBoard() {
		state = new int[] { TOKEN_B, TOKEN_B, TOKEN_B,
							TOKEN_EMPTY,
							TOKEN_V, TOKEN_V, TOKEN_V};
	}

	public FichasBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public FichasBoard(FichasBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}
	
	public void moveGapLeft(int times) {
		int gapPos = getGapPosition();
		
		if (gapPos - times >= 0) {
			int valueOnLeft = getValueAt(gapPos - times);
			setValue(gapPos, valueOnLeft);
			setValue(gapPos - times, TOKEN_EMPTY);
		}
	}

	public void moveGapRight(int times) {
		int gapPos = getGapPosition();
		
		if (gapPos + times < 7) {
			int valueOnRight = getValueAt(gapPos + times);
			setValue(gapPos, valueOnRight);
			setValue(gapPos + times, TOKEN_EMPTY);
		}
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int gapPos = getGapPosition();
		if (where.equals(LEFT1))
			retVal = gapPos != 0;
		else if (where.equals(RIGHT1))
			retVal = gapPos != 6;
		else if (where.equals(LEFT2))
			retVal = gapPos > 1;
		else if (where.equals(RIGHT2))
			retVal = gapPos < 5;
		else if (where.equals(LEFT3))
			retVal = gapPos > 2;
		else if (where.equals(RIGHT3))
			retVal = gapPos < 4;
		
		return retVal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FichasBoard other = (FichasBoard) obj;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public String toString() {
		String retVal = "\t+---+---+---+---+---+---+---+\n\t| ";
		for (int i = 0; i < 7; i++) {
			switch(state[i]) {
			case TOKEN_B:
				retVal += "B | ";
				break;
			case TOKEN_V:
				retVal += "V | ";
				break;
			case TOKEN_EMPTY:
				retVal += "  | ";
				break;
			}
		}
		retVal += "\n\t+---+---+---+---+---+---+---+";

		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	private int getValueAt(int x) {
		return state[x];
	}

	private int getGapPosition() {
		return getPositionOf(0);
	}

	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 7; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	private void setValue(int x, int val) {
		state[x] = val;
	}
}