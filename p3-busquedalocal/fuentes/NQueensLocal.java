package aima.gui.demo.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.SearchAgent;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Javier Herrer Torres
 * 
 */

public class NQueensLocal {
	private static final int _boardSize = 8;
		
	public static void main(String[] args) {
		nQueensHillClimbingSearch_Statistics(10000);
		nQueensRandomRestartHillClimbing();
		nQueensSimulatedAnnealing_Statistics(1000);
		nQueensHillSimulatedAnnealingRestart();
		nQueensGeneticAlgorithmSearch();
	}
	
	private static void nQueensHillClimbingSearch_Statistics(int numExperimentos) {
		Set<NQueensBoard> tableros = generateSetNqueensBoard(_boardSize, numExperimentos);
		int fallos = 0;
		float costeFallos = 0;
		int exitos = 0;
		float costeExitos = 0;
		
		for (NQueensBoard estadoInicial : tableros) {
			try {
				Problem problem = new Problem(estadoInicial,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);

				
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					exitos++;
					costeExitos += agent.getActions().size();
				} else {
					fallos++;
					costeFallos += agent.getActions().size();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		costeFallos = costeFallos/fallos;
		costeExitos = costeExitos/exitos;
		
		System.out.println("\nNQueens HillClimbing con " + numExperimentos + " estados iniciales diferentes -->");
		System.out.println("Fallos: " + fallos);
		System.out.println("Coste medio fallos: " + costeFallos);
		System.out.println("Exitos: " + exitos);
		System.out.println("Coste medio Exitos: " + costeExitos);
	}
	
	private static void nQueensRandomRestartHillClimbing() {
		boolean exito = false;
		int reintentos = 0;
		int fallos = 0;
		float costeFallos = 0;
		float costeExitos = 0;
		
		while ( ! exito) {
			try {
				Problem problem = new Problem(generateRandomNqueensBoard(_boardSize),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);			
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					exito = true;
					costeExitos += agent.getActions().size();
				} else {
					fallos++;
					costeFallos += agent.getActions().size();
				}
				System.out.println("Search Outcome=" + search.getOutcome().toString());
				System.out.println("Final State=\n" + search.getLastSearchState());
				reintentos++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		costeFallos = costeFallos / fallos;
		
		System.out.println("Numero de intentos: " + reintentos);
		System.out.println("Fallos: " + fallos);
		System.out.println("Coste medio fallos: " + costeFallos);
		System.out.println("Coste Exito: " + costeExitos);
	}
	
	private static void nQueensSimulatedAnnealing_Statistics(int numExperimentos) {
		Set<NQueensBoard> tableros = generateSetNqueensBoard(_boardSize, numExperimentos);
		int fallos = 0;
		float costeFallos = 0;
		int exitos = 0;
		float costeExitos = 0;
		
		Scheduler sched = new Scheduler(10, 0.1, 500); //int k, double lam, int limit
		for (NQueensBoard estadoInicial : tableros) {
			try {
				Problem problem = new Problem(estadoInicial,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(), sched);
				SearchAgent agent = new SearchAgent(problem, search);

				
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					exitos++;
					costeExitos += agent.getActions().size();
				} else {
					fallos++;
					costeFallos += agent.getActions().size();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		costeFallos = costeFallos/fallos;
		costeExitos = costeExitos/exitos;
		
		System.out.println("\nNQueensDemo Simulated Annealing con " + numExperimentos + " estados iniciales diferentes -->");
		System.out.println("Parámetros Scheduler: Scheduler (10, 0.1, 500)\n");
		System.out.println("Fallos: " + fallos);
		System.out.println("Coste medio fallos: " + costeFallos);
		System.out.println("Exitos: " + exitos);
		System.out.println("Coste medio Exitos: " + costeExitos);
	}

	private static void nQueensHillSimulatedAnnealingRestart() {
		boolean exito = false;
		int reintentos = 0;
		int fallos = 0;
		float costeExitos = 0;
		
		Scheduler sched = new Scheduler(10, 0.1, 500); //int k, double lam, int limit
		while ( ! exito) {
			try {
				Problem problem = new Problem(generateRandomNqueensBoard(_boardSize),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(), sched);
				SearchAgent agent = new SearchAgent(problem, search);			
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					exito = true;
					costeExitos += agent.getActions().size();
				} else {
					fallos++;
				}
				System.out.println("Search Outcome=" + search.getOutcome().toString());
				System.out.println("Final State=\n" + search.getLastSearchState());
				reintentos++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		System.out.println("Numero de intentos: " + reintentos);
		System.out.println("Fallos: " + fallos);
		System.out.println("Coste Exito: " + costeExitos);
	}

	private static NQueensBoard generateRandomNqueensBoard(int size) {
		NQueensBoard board = new NQueensBoard(size);
		for (int i = 0; i < 8; i++) {
            int numAleatorio = (int) (Math.random()*8); 
            XYLocation newLocation = new XYLocation(i,numAleatorio);
            board.addQueenAt(newLocation);
        }
        return board;
	}

	public static Set<NQueensBoard> generateSetNqueensBoard(int boardSize, int populationSize){
		Set<NQueensBoard> setGeneratedNQueens = new HashSet<NQueensBoard>();
		while(setGeneratedNQueens.size()< populationSize){
			setGeneratedNQueens.add(generateRandomNqueensBoard(_boardSize)); 
		}
		return setGeneratedNQueens;
	}

	private static void nQueensSimulatedAnnealingSearch() {
		System.out.println("\nNQueensDemo Simulated Annealing  -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
					new AttackingPairsHeuristic());
			SearchAgent agent = new SearchAgent(problem, search);

			System.out.println();
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("GeneticAlgorithm");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					0.15);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = 
					ga.geneticAlgorithm(population, fitnessFunction, fitnessFunction, 0L);

			System.out.println("Parámetros iniciales:\tPoblación: 50, Probabilidad mutación: 0.15");
			System.out.println("Mejor individuo=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Tamaño tablero      = " + _boardSize);;
			System.out.println("Fitness             = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Es objetivo         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Tamaño de población = " + ga.getPopulationSize());
			System.out.println("Iteraciones         = " + ga.getIterations());
			System.out.println("Tiempo              = "
					+ ga.getTimeInMilliseconds() + "ms.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}
}