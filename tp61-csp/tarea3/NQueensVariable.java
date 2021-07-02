package aima.gui.nqueens.csp;

import aima.core.search.csp.Variable;

public class NQueensVariable extends Variable {
	private int column;

	public NQueensVariable(String name, int column) {
		super(name);
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
}
