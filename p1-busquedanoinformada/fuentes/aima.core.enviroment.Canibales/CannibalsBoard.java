package aima.core.environment.Canibales;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * @author Javier Herrer Torres (NIA: 776609)
 */
public class CannibalsBoard {

	public static Action MOVE1C = new DynamicAction("M1C");

	public static Action MOVE2C = new DynamicAction("M2C");
	
	public static Action MOVE1M = new DynamicAction("M1M");
	
	public static Action MOVE2M = new DynamicAction("M2M");

	public static Action MOVE1M1C = new DynamicAction("M1M1C");

	
	private static final int Mi = 0;
	private static final int Ci = 1;
	private static final int B  = 2;
	private static final int Mf = 3;
	private static final int Cf = 4;

	// state (Mi, Ci, B, Mf, Cf)
	private int[] state;

	//
	// PUBLIC METHODS
	//

	public CannibalsBoard() {
		state = new int[] { 3, 3, 0, 0, 0 };
	}

	public CannibalsBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CannibalsBoard(CannibalsBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}	
	
	public void move1C() {
		int x = getBoat();
		int y = 1 - x;
		
		int My = getMissionariesAt(y);
		int Cx = getCannibalsAt(x);
		int Cy = getCannibalsAt(y);
		
		if ( Cx >= 1 && (My >= Cy + 1 || My == 0) ) {
			setBoat(y);
			setCannibalsAt(x, Cx - 1);
			setCannibalsAt(y, Cy + 1);
		}
	}
	
	public void move2C() {
		int x = getBoat();
		int y = 1 - x;
		
		int My = getMissionariesAt(y);
		int Cx = getCannibalsAt(x);
		int Cy = getCannibalsAt(y);
		
		if ( Cx >= 2 && (My >= Cy + 2 || My == 0) ) {
			setBoat(y);
			setCannibalsAt(x, Cx - 2);
			setCannibalsAt(y, Cy + 2);
		}
	}
	
	public void move1M() {
		int x = getBoat();
		int y = 1 - x;
		
		int Mx = getMissionariesAt(x);
		int My = getMissionariesAt(y);
		int Cx = getCannibalsAt(x);
		int Cy = getCannibalsAt(y);
		
		if ( Mx >= 1 && (Mx >= Cx + 1 || Mx == 1) 
			&& My >= Cy - 1) {
			setBoat(y);
			setMissionariesAt(x, Mx - 1);
			setMissionariesAt(y, My + 1);
		}
	}
	
	public void move2M() {
		int x = getBoat();
		int y = 1 - x;
		
		int Mx = getMissionariesAt(x);
		int My = getMissionariesAt(y);
		int Cx = getCannibalsAt(x);
		int Cy = getCannibalsAt(y);
		
		if ( Mx >= 2 && (Mx >= Cx + 2 || Mx == 2) 
			&& My >= Cy - 2) {
			setBoat(y);
			setMissionariesAt(x, Mx - 2);
			setMissionariesAt(y, My + 2);
		}
	}
	
	public void move1M1C() {
		int x = getBoat();
		int y = 1 - x;
		
		int Mx = getMissionariesAt(x);
		int My = getMissionariesAt(y);
		int Cx = getCannibalsAt(x);
		int Cy = getCannibalsAt(y);
		
		if (Mx >= 1 && My >= Cy && Cx >= 1) {
			setBoat(y);
			setMissionariesAt(x, Mx - 1);
			setMissionariesAt(y, My + 1);
			setCannibalsAt(x, Cx - 1);
			setCannibalsAt(y, Cy + 1);
		}
	}
	
	public boolean canMove(Action action) {
		boolean retVal = true;
		int x = getBoat();
		int y = 1 - x;
		
		int Mx = getMissionariesAt(x);
		int My = getMissionariesAt(y);
		int Cx = getCannibalsAt(x);
		int Cy = getCannibalsAt(y);
		
		if (action.equals(MOVE1C))
			retVal = Cx >= 1 && (My >= Cy + 1 || My == 0);
		else if (action.equals(MOVE2C))
			retVal = Cx >= 2 && (My >= Cy + 2 || My == 0);
		else if (action.equals(MOVE1M))
			retVal = Mx >= 1 && (Mx >= Cx + 1 || Mx == 1) 
					 && My >= Cy - 1;
		else if (action.equals(MOVE2M))
			retVal = Mx >= 2 && (Mx >= Cx + 2 || Mx == 2) 
					 && My >= Cy - 2;
		else if (action.equals(MOVE1M1C))
			retVal = Mx >= 1 && My >= Cy && Cx >= 1;
			
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
		CannibalsBoard other = (CannibalsBoard) obj;
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
		String strMi = "M ".repeat(getMissionariesAt(0));
		String strCi = "C ".repeat(getMissionariesAt(0));
		String strMf = "M ".repeat(getMissionariesAt(1));
		String strCf = "C ".repeat(getMissionariesAt(1));
		String strBi = "";
		String strBf = "";
		if(getBoat() == 0) strBi = "BOTE";
		else strBf = "BOTE";
		
		
		String retVal = String.format("\tRIBERA-IZQ %6s %6s %6s --RIO--"
									  + "%6s %6s %6s RIBERA-DCH", 
									  strMi, strCi, strBi, strBf, strMf, strCf);

		return retVal;
	}

	//
	// PRIVATE METHODS
	//
	
	private int getMissionariesAt(int i) {
		if (i == 0) return state[Mi];
		else return state[Mf];
	}
	
	private void setMissionariesAt(int i, int value) {
		if (i == 0) state[Mi] = value;
		else state[Mf] = value;
	}

	private int getCannibalsAt(int i) {
		if (i == 0) return state[Ci];
		else return state[Cf];
	}
	
	private void setCannibalsAt(int i, int value) {
		if (i == 0) state[Ci] = value;
		else state[Cf] = value;
	}
	
	private int getBoat() {
		return state[B];
	}
	
	private void setBoat(int i) {
		state[B] = i;
	}
}