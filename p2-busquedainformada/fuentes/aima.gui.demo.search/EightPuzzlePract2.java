package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.math.Biseccion;

/**
 * @author Javier Herrer Torres (NIA: 776609)
 * 
 */

public class EightPuzzlePract2 {
	
	static int _depth = 0;
	static int _generatedNodes = 0;
	static int _expandedNodes = 0;

	public static void main(String[] args) {
		System.out.println("Experimentos");
		
		long[][] resultados = new long[4][25];
		double[][] effectiveB = new double[4][25];
		int prof_inicial = 2;
		int prof_final = 24;
		int num_experimentos = 100;
		
		experimento("BreathFirstSearch", prof_inicial, prof_final, 
					num_experimentos, resultados[0], effectiveB[0]);
		experimento("IDS", prof_inicial, prof_final, 
					num_experimentos, resultados[1], effectiveB[1]);
		experimento("A* misplaced", prof_inicial, prof_final, 
					num_experimentos, resultados[2], effectiveB[2]);
		experimento("A* Manhatan", prof_inicial, prof_final, 
					num_experimentos, resultados[3], effectiveB[3]);
		
		imprime_resultados(prof_inicial, prof_final, resultados, effectiveB);
	}

	public static void experimento(String searchName, int prof_ini, 
								   int profundidad, int numExp,
								   long[] resultados, double[] effectiveB) {
		Search search = null;
		
		double mediaNodosGenerados = 0;
		double mediaNodosExpandidos = 0;
		int experimento = 0;
		
		System.out.println(searchName);
		
		if (searchName.compareTo("IDS") == 0 && profundidad > 10) {
			profundidad = 10;
		}
		
		for (int pasos = prof_ini; pasos <= profundidad; pasos++) {
			experimento = 1;
			while (experimento <= numExp) {
				EightPuzzleBoard initialState = new EightPuzzleBoard(
						GenerateInitialEightPuzzleBoard.randomIni());
				EightPuzzleBoard finalState = 
						GenerateInitialEightPuzzleBoard.random(
								pasos, new EightPuzzleBoard(initialState));
				EightPuzzleGoalTest.setGoalState(
						new EightPuzzleBoard(finalState));
				
				if (searchName.compareTo("BreathFirstSearch") == 0) {
					search = new BreadthFirstSearch();
				} else if (searchName.compareTo("IDS") == 0) {
					search = new IterativeDeepeningSearch();
				} else if (searchName.compareTo("A* misplaced") == 0) {
					search = new AStarSearch(
							new GraphSearch(), 
							new MisplacedTilleHeuristicFunction2());
				} else if (searchName.compareTo("A* Manhatan") == 0) {
					search = new AStarSearch(
							new GraphSearch(), 
							new ManhattanHeuristicFunction2());
				}
				
				eightPuzzleSearch(search, initialState, finalState, 
								  "Busqueda " + searchName);
				
				if (_depth == pasos) {
					mediaNodosGenerados += _generatedNodes;
					mediaNodosExpandidos += _expandedNodes;
					
					experimento++;
				}
			}
			
			resultados[pasos] = Math.round(mediaNodosGenerados/numExp);
			Biseccion bf = new Biseccion();
			bf.setDepth(_depth);
			bf.setGeneratedNodes((int)resultados[pasos]);
			
			effectiveB[pasos] = 
					bf.metodoDeBiseccion(1.000000000000001, 4, 1E-10);
			mediaNodosExpandidos = Math.round(mediaNodosExpandidos/numExp);
			
			System.out.format("Depth: %3d %6d Nodos b*: %f3.2\n",  _depth, 
							  resultados[pasos], effectiveB[pasos]);
		}
	}
	
	public static void eightPuzzleSearch(Search search, 
										 EightPuzzleBoard initialState, 
										 EightPuzzleBoard finalState,
										 String message) {
		_depth = 0;
		_generatedNodes = 0;
		
		try {
			EightPuzzleGoalTest goalTest = new EightPuzzleGoalTest();
			EightPuzzleGoalTest.setGoalState(finalState);
			Problem problem = new Problem(initialState,
					EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(),
					goalTest);
			SearchAgent agent = new SearchAgent(problem, search);
			
			_depth = (int)Float.parseFloat(
					agent.getInstrumentation().getProperty("pathCost"));
			_generatedNodes = Integer.parseInt(
					agent.getInstrumentation().getProperty("nodesGenerated"));
			_expandedNodes = Integer.parseInt(
					agent.getInstrumentation().getProperty("nodesExpanded"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void imprime_resultados(int inidepth, int lastdepth, 
										   long[][] resultados, 
										   double[][] effectiveB) {
		for(int i = 1; i<91; i++) System.out.print("-");
		System.out.println();
		System.out.format("||    || %20s%17s || %19s%19s||\n","Nodos Generados",
						  " ","b*", " ");
		for(int i = 1; i<91; i++) System.out.print("-");
		System.out.println();
		System.out.format("|| %3s||","d");	
		System.out.format(" %6s  |", "BFS");
		System.out.format(" %6s  |", "IDS");
		System.out.format(" %6s  |", "A*h(1)");
		System.out.format(" %6s  ||", "A*h(2)");
		System.out.format(" %6s  |", "BFS");
		System.out.format(" %6s  |", "IDS");
		System.out.format(" %6s  |", "A*h(1)");
		System.out.format(" %6s  ||", "A*h(2)");
		System.out.println();
		for(int i = 1; i<91; i++) System.out.print("-");
		System.out.println();
		for(int i = 1; i<91; i++) System.out.print("-");
		System.out.println();
		
		for (int d = inidepth; d <= lastdepth; d++) {
			System.out.format("|| %3s||", d);	
			System.out.format(" %6s  |", resultados[0][d]);
			System.out.format(" %6s  |", resultados[1][d]);
			System.out.format(" %6s  |", resultados[2][d]);
			System.out.format(" %6s  ||", resultados[3][d]);
			System.out.format(" %6.2f  |", effectiveB[0][d]);
			System.out.format(" %6.2f  |", effectiveB[1][d]);
			System.out.format(" %6.2f  |", effectiveB[2][d]);
			System.out.format(" %6.2f  ||", effectiveB[3][d]);
			System.out.println();
		}
		
		for(int i = 1; i<91; i++) System.out.print("-");
		System.out.println();
	}
}