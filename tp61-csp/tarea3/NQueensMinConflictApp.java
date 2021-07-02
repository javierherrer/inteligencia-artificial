package aima.gui.nqueens.csp;

import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;
import aima.core.search.csp.MinConflictsStrategy;
import aima.core.search.csp.SolutionStrategy;
import aima.core.search.csp.Variable;

public class NQueensMinConflictApp {
	private static final int _boardSize = 8;
	
	public NQueensMinConflictApp() {
		SolutionStrategy strategy = new MinConflictsStrategy(100);
		for (int i = 0; i < 50; i++) {
			System.out.println("---------");
			resolverNQueens(strategy);
		}

	}
	
	private void resolverNQueens(SolutionStrategy strategy) {
		try {
			NQueensProblem problem = new NQueensProblem();

			strategy.addCSPStateListener(new CSPStateListener() {
				@Override
				public void stateChanged(Assignment assignment, CSP csp) {
//				System.out.println("Assignment evolved : " + assignment);
				}

				@Override
				public void stateChanged(CSP csp) {
//				System.out.println("CSP evolved : " + csp);
				}
			});

			double start = System.currentTimeMillis();
			Assignment sol = strategy.solve(problem);
			double end = System.currentTimeMillis();

			if (sol != null) {
				System.out.println(sol);
				System.out.println("Time to solve = " + ((end - start) / 1000) + "segundos");
				System.out.println("SOLUCION:");
				getBoardPic(sol);
				System.out.println("NQueens solucionado correctamente");
			} else {
				System.out.println("NQueens NO solucionado");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getBoardPic(Assignment assignment) {
		List<Variable> variables = assignment.getVariables();
		
		StringBuffer buffer = new StringBuffer();
		for (int row = 1; (row <= 8); row++) { // row
			for (int col = 0; (col < 8); col++) { // col
				
				Variable var = variables.get(col);
				Object value = assignment.getAssignment(var);
				
				if (value.equals(row))
					buffer.append(" Q ");
				else
					buffer.append(" - ");
			}
			buffer.append("\n");
		}
		System.out.println(buffer.toString());
	}
	
	
	public static void main(String args[]) {
		new NQueensMinConflictApp();
	}
}
