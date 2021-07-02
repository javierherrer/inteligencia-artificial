package aima.gui.sudoku.csp;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.SolutionStrategy;

/**
 * Lee y resuelve los sudokus especificados en los ficheros
 * 
 */
public class SudokuApp {

	public SudokuApp() {
		Sudoku[] lista = union(Sudoku.listaSudokus2("easy50.txt"), Sudoku.listaSudokus2("top95.txt"),
				Sudoku.listaSudokus2("hardest.txt"));

		/*
		 * MRV + DEG + AC3 + LCV
		 * 
		 * Entrelazan búsqueda (backtracking), algoritmos de propagación de
		 * restricciones considerando diferente número de variables, como las
		 * restricciones sobre los valores de dos variables (AC3, Arc consistency
		 * version 3), y heurísticas de propósito general como elegir primero las
		 * variables con menos número de valores posibles.
		 */
		SolutionStrategy strategy = new ImprovedBacktrackingStrategy(true, true, true, true);

		resolverSudokus(lista, strategy);
	}

	private void resolverSudokus(Sudoku[] lista, SolutionStrategy strategy) {
		int resueltos = 0;
		for (Sudoku sudoku : lista) {
			System.out.println("---------");
			if (resolverSudoku(sudoku, strategy)) {
				resueltos++;
			}
		}

		System.out.println("+++++++++");
		System.out.println("Numero de sudokus solucionados:" + resueltos);
	}

	private boolean resolverSudoku(Sudoku sudoku, SolutionStrategy strategy) {
		sudoku.imprimeSudoku();

		if (sudoku.completo()) {
			System.out.println("SUDOKU COMPLETO");
		} else {
			try {
				System.out.println("SUDOKU INCOMPLETO - Resolviendo");
				SudokuProblem problem = new SudokuProblem(sudoku.pack_celdasAsignadas());

				strategy.addCSPStateListener(new CSPStateListener() {
					@Override
					public void stateChanged(Assignment assignment, CSP csp) {
//					System.out.println("Assignment evolved : " + assignment);
					}

					@Override
					public void stateChanged(CSP csp) {
//					System.out.println("CSP evolved : " + csp);
					}
				});

				double start = System.currentTimeMillis();
				Assignment sol = strategy.solve(problem);
				double end = System.currentTimeMillis();

				System.out.println(sol);
				System.out.println("Time to solve = " + ((end - start) / 1000) + "segundos");
				System.out.println("SOLUCION:");
				
				Sudoku solucion = new Sudoku(sol);
				solucion.imprimeSudoku();
				if (solucion.correcto()) {
					System.out.println("Sudoku solucionado correctamente");
					return true;
				} else {
					System.out.println("Sudoku solucionado incorrectametente");
					return false;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Unión de múltiples vectores
	 * 
	 */
	private Sudoku[] union(Sudoku[]... arrays) {
		int maxSize = 0;
		int counter = 0;

		for (Sudoku[] array : arrays)
			maxSize += array.length;
		Sudoku[] accumulator = new Sudoku[maxSize];

		for (Sudoku[] array : arrays)
			for (Sudoku i : array)
				if (!isDuplicated(accumulator, counter, i))
					accumulator[counter++] = i;

		Sudoku[] result = new Sudoku[counter];
		for (int i = 0; i < counter; i++)
			result[i] = accumulator[i];

		return result;
	}

	private boolean isDuplicated(Sudoku[] array, int counter, Sudoku value) {
		for (int i = 0; i < counter; i++)
			if (array[i] == value)
				return true;
		return false;
	}

	public static void main(String args[]) {
		new SudokuApp();
	}

}
