

/**
* <h1>Edge</h1>
* edge with two nodes and weight
*
* @author  Ella Liu
* @version 1.0
* @since   2017-05-1
*/
public class Edge {
	
	/** The start. */
	private Node start;
	
	/** The end. */
	private Node end;
	
	/** The weight. */
	private int weight;
	
	/** The name. */
	private String name;	

	/**
	 * Instantiates a new edge.
	 *
	 * @param weight the weight
	 * @param start the start
	 * @param end the end
	 * @param name the name
	 */
	public Edge(int weight, Node start, Node end, String name) {
		this.start = start;
		this.end = end;
		this.weight = weight;
		this.name = name;
	}

	/**
	 * Name rverse.
	 *
	 * @return the string
	 */
	public String nameRverse(){
		return end.getName()+start.getName();
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
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Node getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Node start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public Node getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(Node end) {
		this.end = end;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
