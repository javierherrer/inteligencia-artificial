package aima.gui.demo.search;

import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.Fichas.FichasBoard;
import aima.core.environment.Fichas.FichasFunctionFactory;
import aima.core.environment.Fichas.FichasGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

/**
 * @author Javier Herrer Torres (NIA: 776609)
 * 
 */

public class FichasPract1 {
	static FichasBoard initialBoard = new FichasBoard();

	public static void main(String[] args) {		
		fichasSearch(new BreadthFirstSearch(new GraphSearch()), initialBoard, "BFS", true, "");
		fichasSearch(new DepthLimitedSearch(10), initialBoard, "DLS(10)", true, "");
		fichasSearch(new IterativeDeepeningSearch(), initialBoard, "IDLS", true, "");
		fichasSearch(new UniformCostSearch(new GraphSearch()), initialBoard, "UCS-f", true, "");
	}
	
	private static void fichasSearch(Search searchTipe, FichasBoard initialState, String title, Boolean exec, String optional) {
		int depth;
		int expandedNodes;
		int queueSize;
		int maxQueueSize;
		
		try {
			if (exec) {
				Problem problem = new Problem(initialState, 
					  FichasFunctionFactory.getActionsFunction(), 
					  FichasFunctionFactory.getResultFunction(),
					  new FichasGoalTest(),
					  FichasFunctionFactory.getStepCostFunction());
			
				long time = System.currentTimeMillis();
				SearchAgent agent = new SearchAgent(problem, searchTipe);
				time = System.currentTimeMillis() - time;			
	
				//Get search results
				String pathcostM =agent.getInstrumentation().getProperty("pathCost");
				if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
				else depth = 0;
				if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
				else expandedNodes =
				(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
				if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0;
				else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
				if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
				else maxQueueSize =
				(int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
				
				System.out.println("\nProblema de las fichas " + title + " -->\n"
								   + "pathCost : " + pathcostM + "\n"
								   + "nodesExpanded : " + expandedNodes + "\n"
								   + "queueSize : " + queueSize + "\n"
								   + "maxQueueSize : " + maxQueueSize + "\n" 
								   + "Tiempo:" + time + "\n");
				FichasBoard goal = new FichasBoard(new int[] { 2, 2, 2, 0, 1, 1, 1 });
				System.out.println("SOLUCIÓN:\n"
								   + "GOAL STATE\n" + goal.toString() + "\n"
								   + "CAMINO ENCONTRADO");
				executeActions(agent.getActions(), problem);
			}
		} catch (Exception e) {
			System.out.println("error");
		}
	}

	public static void executeActions(List<Action> actions, Problem problem) {
		Object initialState = problem.getInitialState();
		ResultFunction resultFunction = problem.getResultFunction();
		
		Object state = initialState;
		System.out.print("ESTADO INICIAL\n");
		System.out.println(state);
		
		for (Action action : actions) {
			System.out.print(action.toString() + "\n");
			state = resultFunction.result(state, action);
			System.out.println(state);
		}
	}
}