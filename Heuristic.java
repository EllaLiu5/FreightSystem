import java.util.*;


/**
* <h1>Heuristic</h1>
* a map for it's belonged path, can calculate shortest path from node to node, help path
* to generate h
*
* @author  Ella Liu
* @version 1.0
* @since   2017-05-1
*/
public class Heuristic {
	
	/** The goal. */
	private Set<String> goal;
	
	/** The nodes. */
	private Hashtable<String, Node> nodes;
	
	/** The edges. */
	private Hashtable<String, Edge> edges;
	
	/** The weights. */
	private Hashtable<String, Integer> weights;
	
	/** The settled nodes. */
	Set<Node> settledNodes;
    
    /** The un settled nodes. */
    Set<Node> unSettledNodes;
    
    /** The predecessors. */
    Map<Node, Node> predecessors;
    
    /** The distance. */
    Map<Node, Integer> distance;
	
    /**
     * Instantiates a new heuristic.
     */
    Heuristic(){
    	nodes = new Hashtable<String, Node>();
    	edges = new Hashtable<String, Edge>();
    	weights = new Hashtable<String, Integer>();
    	goal = new HashSet<String>();
    }
    
	/**
	 * Instantiates a new heuristic.
	 *
	 * @param goal the goal
	 * @param nodes the nodes
	 * @param edges the edges
	 * @param weightsCopy the weights copy
	 */
	Heuristic(Set<String> goal, Hashtable<String, Node> nodes, Hashtable<String, Edge> edges, Hashtable<String, Integer> weightsCopy){
		this.goal = goal;
		this.nodes = nodes;
		this.edges = edges;
		weights = new Hashtable<String, Integer>();
		for(String g:weightsCopy.keySet()){
			weights.put(g, weightsCopy.get(g));
		}
	}
	
	/**
	 * Update map.
	 *
	 * @param e the e
	 */
	public void updateMap(String e){
		goal.remove(e);
		int newWeight = weights.get(e)*2;
		//System.out.println(e+" update "+newWeight);
		weights.put(e, newWeight);
		//System.out.println(weights);
	}
	
	/**
	 * Gets the edges.
	 *
	 * @return the edges
	 */
	public Hashtable<String, Edge> getEdges() {
		return edges;
	}

	/**
	 * Sets the edges.
	 *
	 * @param edges the edges
	 */
	public void setEdges(Hashtable<String, Edge> edges) {
		this.edges = edges;
	}

	/**
	 * Gets the weights.
	 *
	 * @return the weights
	 */
	public Hashtable<String, Integer> getWeights() {
		return weights;
	}

	/**
	 * Sets the weights.
	 *
	 * @param weights the weights
	 */
	public void setWeights(Hashtable<String, Integer> weights) {
		this.weights = weights;
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
	 * Sets the goal.
	 *
	 * @param goal the new goal
	 */
	public void setGoal(Set<String> goal) {
		this.goal = goal;
	}

	/**
	 * Dijkstra Algorithm to calculate shortest path from begin to nearest start node of goal
	 *
	 * @param begin the begin
	 * @return the int
	 */
	public int Dijkstra(Node begin){
		settledNodes = new HashSet<Node>();
	    unSettledNodes = new HashSet<Node>();
	    distance = new HashMap<Node, Integer>();
	    //System.out.println(begin.getName());
	    distance.put(begin, 0);
	    unSettledNodes.add(begin);
        while (unSettledNodes.size() > 0) {
                Node node = getMinimum(unSettledNodes);
                settledNodes.add(node);
                unSettledNodes.remove(node);
                findMinimalDistances(node);
        }
        return getMinDistanceGoal();
	}

    /**
     * Find minimal distances.
     *
     * @param node the node
     */
    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                unSettledNodes.add(target);
            }
        }
    }

    /**
     * Gets the distance.
     *
     * @param node the node
     * @param target the target
     * @return the distance
     */
    private int getDistance(Node node, Node target) {
        return weights.get(node.getName()+target.getName());
    }

    /**
     * Gets the neighbors.
     *
     * @param node the node
     * @return the neighbors
     */
    private List<Node> getNeighbors(Node node) {
        List<Node> neighborNodes = new ArrayList<Node>();
        List<Edge> neighborEdges = node.getEdges();
        for (Edge edge : neighborEdges) {
        	neighborNodes.add(edge.getEnd());
        }
        return neighborNodes;
    }

    /**
     * Gets the minimum.
     *
     * @param nodes the nodes
     * @return the minimum
     */
    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node node : nodes) {
            if (minimum == null) {
                    minimum = node;
            } else {
                if (getShortestDistance(node) < getShortestDistance(minimum)) {
                        minimum = node;
                }
            }
        }
        return minimum;
    }

    /**
     * Gets the shortest distance.
     *
     * @param destination the destination
     * @return the shortest distance
     */
    private int getShortestDistance(Node destination) {
        if (distance.containsKey(destination)) {
        	return distance.get(destination);
        } else {
            return Integer.MAX_VALUE;
        }
    }
    
    /**
     * Gets the goal edge with min distace from begin node. Helper function.
     *
     * @return the min distance goal
     */
    public int getMinDistanceGoal() {
        int minimum = -1;
        for (String e : goal) {
        	if(getShortestDistance(edges.get(e).getStart())==Integer.MAX_VALUE){
        		System.out.println("0 nodes expanded");
        		System.out.println("No solution");
        		System.exit(0);
        	}
            if (minimum == -1) {
                    minimum = getShortestDistance(edges.get(e).getStart());
            } else {
                if (getShortestDistance(edges.get(e).getStart()) < minimum) {
                        minimum = getShortestDistance(edges.get(e).getStart());
                }
            }
        }
        return minimum;
    }
    
    /**
     * Copy Heuristic
     *
     * @param oldHeu Heuristic to be copied
     */
    public void copyHeu(Heuristic oldHeu){
    	this.nodes = oldHeu.nodes;
    	this.edges = oldHeu.edges;
    	for(String w:oldHeu.getWeights().keySet()){
    		this.weights.put(w, oldHeu.getWeights().get(w));
    	}
		for(String g:oldHeu.goal){
			this.goal.add(g);
		}
    }
}
