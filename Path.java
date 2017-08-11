import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * <h1>Path</h1>
 * contain a list of visited edges and calculate f for each edge.
 *
 * @author  Ella Liu
 * @version 1.0
 * @since   2017-05-1
 */
public class Path {
	
	/** The edges. */
	private List<Edge> edges;
	
	/** The front node. */
	private Node frontNode;
	
	/** The heu map. */
	private Heuristic heuMap;
	
	/** The goal. */
	private Set<String> goal;
	
	/** The goal dupped. */
	private Set<String> goalDupped;
	
	/** The g. */
	private int g;
	
	/** The h. */
	private int h;
	
	/** The f. */
	private int f;
	
	/** The cost. */
	private int cost;
	
	/** The init heu. */
	private int initHeu;
	
	/**
	 * Instantiates a new path with one edge. Used for the beginning of Astar
	 *
	 * @param e the e
	 * @param goal the goal
	 * @param heuMap the heu map
	 * @param initHeu the init heu
	 * @param goalDupped the goal dupped
	 */
	Path(Edge e, Set<String> goal, Heuristic heuMap, int initHeu, Set<String> goalDupped){
		edges = new ArrayList<Edge>();
		this.goalDupped = goalDupped;
		this.heuMap = heuMap;
		edges.add(e);
		this.goal = goal;
		frontNode = e.getEnd();
		this.initHeu = initHeu;
		g = heuMap.getWeights().get(e.getName());
		//System.out.println("path g "+g);
		cost = e.getWeight();
		//initHeu
		if (goal.contains(e.getName())){
			if(goalDupped.contains(e.getName())){
				initHeu-=e.getWeight()*(2+1);
			}
			else{
				initHeu-=e.getWeight();
			}
			heuMap.updateMap(e.getName());
			goal.remove(e.getName());
			cost+=e.getEnd().getCost();//uploading cost
		}
		h = heuMap.Dijkstra(frontNode)+initHeu;
		f = h+g;
	}
	
	/**
	 * Instantiates a new path with current Path and one extended edge.
	 *
	 * @param curPath the cur path
	 * @param e the e
	 */
	Path(Path curPath, Edge e){
		List<Edge> edges = new ArrayList<Edge>();
		for(Edge temp:curPath.edges){
			edges.add(temp);
		}
		edges.add(e);
		this.edges = edges;
		frontNode = e.getEnd();
		heuMap = new Heuristic();
		heuMap.copyHeu(curPath.getHeuMap());
		goal = heuMap.getGoal();
		goalDupped = curPath.getGoalDupped();
		g = curPath.getG()+heuMap.getWeights().get(e.getName());
		cost = curPath.getCost()+e.getWeight();
		//System.out.println("newcost ="+ curPath.getCost()+"+"+e.getWeight());
		initHeu = curPath.getInitHeu();
		//check if goal
		if(goal.contains(e.getName())){
			goal.remove(e.getName());
			heuMap.updateMap(e.getName());//update goal heu
			//g += e.getStart().getCost();uploading cost
			cost+=e.getEnd().getCost();
			if(goalDupped.contains(e.getName())){
				initHeu-=e.getWeight()*(2+1);
			}
			else{
				initHeu-=e.getWeight();
			}
		}
		h = heuMap.Dijkstra(frontNode)+initHeu;
		f = h+g;
	}
	

	/**
	 * Gets the inits the heu.
	 *
	 * @return the inits the heu
	 */
	public int getInitHeu() {
		return initHeu;
	}

	/**
	 * Gets the goal dupped.
	 *
	 * @return the goal dupped
	 */
	public Set<String> getGoalDupped() {
		return goalDupped;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Checks for finished.
	 *
	 * @return true, if successful
	 */
	public boolean hasFinished(){
		//System.out.println("path has finished? "+goal.size());
		if(goal.size() == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the front node.
	 *
	 * @return the front node
	 */
	public Node getFrontNode() {
		return frontNode;
	}
	
	/**
	 * Prints the path.
	 *
	 * @return the string
	 */
	public String printPath(){
		String result = "";
		//result+=vehicles.size()+"\n";
		for (Edge edge: edges) {
			result+=edge.getName();
		}
		return result;
	}
	
	/**
	 * Gets the f.
	 *
	 * @return the f
	 */
	public int getF() {
		return f;
	}

	/**
	 * Gets the edges.
	 *
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * Gets the heu map.
	 *
	 * @return the heu map
	 */
	public Heuristic getHeuMap() {
		return heuMap;
	}


	/**
	 * Gets the goal.
	 *
	 * @return the goal
	 */
	public Set<String> getGoal() {
		return goal;
	}

	/**
	 * Gets the g.
	 *
	 * @return the g
	 */
	public int getG() {
		return g;
	}


	/**
	 * Gets the h.
	 *
	 * @return the h
	 */
	public int getH() {
		return h;
	}

}
