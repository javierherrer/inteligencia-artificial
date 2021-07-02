package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.CannibalsFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.TreeSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

/**
 * @author Javier Herrer Torres (NIA: 776609)
 * 
 */

public class EightPuzzlePract1 {
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;
	
	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		System.out.format("%15s|%11s|%11s|%11s|%11s|%11s\n", "Problem", "Depth", "Expand", "Q.Size", "MaxQS", "tiempo");
		
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "BFS-G-3", true, "");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "BFS-T-3", true, "");
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "DFS-G-3", true, "");
		eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "DFS-T-3", false, "(1)");
		eightPuzzleSearch(new DepthLimitedSearch(9), boardWithThreeMoveSolution, "DLS-9-3", true, "");
		eightPuzzleSearch(new DepthLimitedSearch(3), boardWithThreeMoveSolution, "DLS-3-3", true, "");
		eightPuzzleSearch(new IterativeDeepeningSearch(), boardWithThreeMoveSolution, "IDS-3", true, "");
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), boardWithThreeMoveSolution, "UCS-F-3", true, "");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), boardWithThreeMoveSolution, "UCS-T-3", true, "");
		
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), random1, "BFS-G-9", true, "");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), random1, "BFS-T-9", true, "");
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), random1, "DFS-G-9", true, "");
		eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), random1, "DFS-T-9", false, "(1)");
		eightPuzzleSearch(new DepthLimitedSearch(9), random1, "DLS-9-9", true, "");
		eightPuzzleSearch(new DepthLimitedSearch(3), random1, "DLS-3-9", true, "");
		eightPuzzleSearch(new IterativeDeepeningSearch(), random1, "IDS-9", true, "");
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), random1, "UCS-F-9", true, "");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), random1, "UCS-T-9", true, "");

		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), extreme, "BFS-G-30", true, "");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), extreme, "BFS-T-30", false, "(2)");
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), extreme, "DFS-G-30", true, "");
		eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), extreme, "DFS-T-30", false, "(1)");
		eightPuzzleSearch(new DepthLimitedSearch(9), extreme, "DLS-9-30", true, "");
		eightPuzzleSearch(new DepthLimitedSearch(3), extreme, "DLS-3-30", true, "");
		eightPuzzleSearch(new IterativeDeepeningSearch(), extreme, "IDS-30", false, "(2)");
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), extreme, "UCS-F-30", true, "");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), extreme, "UCS-T-30", false, "(2)");
	}
	
	private static void eightPuzzleSearch(Search searchTipe, EightPuzzleBoard initialState, String title, Boolean exec, String optional) {
		int depth;
		int expandedNodes;
		int queueSize;
		int maxQueueSize;
		
		try {
			if (exec) {
				Problem problem = new Problem(initialState, 
					  CannibalsFunctionFactory.getActionsFunction(), 
					  CannibalsFunctionFactory.getResultFunction(),
					  new EightPuzzleGoalTest());
			
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
				
				System.out.format("%15s|%11d|%11d|%11d|%11d|%11d\n", title, depth, expandedNodes, queueSize, maxQueueSize, time);
			} else {
				System.out.format("%15s|%11s|%11s|%11s|%11s|%11s\n", title, "---", "---", "---", "---", optional);
			}
		} catch (Exception e) {
			System.out.println("error");
		}
	}
}