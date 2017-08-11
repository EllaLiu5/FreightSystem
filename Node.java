import java.util.ArrayList;
import java.util.List;

/**
* <h1>Node</h1>
* node with linked edges
*
* @author  Ella Liu
* @version 1.0
* @since   2017-05-1
*/
public class Node {
	
	/** The cost. */
	private int cost;
	
	/** The name. */
	private String name;
	
	/** The edges. */
	private List<Edge> edges;

	/**
	 * Instantiates a new node.
	 *
	 * @param cost the cost
	 * @param name the name
	 */
	public Node(int cost, String name) {
		this.cost = cost;
		this.name = name;
		edges = new ArrayList<Edge>();
	}

	/**
	 * Adds the edge.
	 *
	 * @param e the e
	 */
	public void addEdge(Edge e){
		edges.add(e);
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
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the edges.
	 *
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}
	
	
}
