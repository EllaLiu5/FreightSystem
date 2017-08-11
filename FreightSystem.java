import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

// TODO: Auto-generated Javadoc
/**
* <h1>FreightSystem</h1>
* use Astar search to find an shortest path start from Sydney with requested edges, 
* can guarantee optimal solution.
*
* @author  Ella Liu
* @version 1.0
* @since   2017-05-1
*/
public class FreightSystem {
	
	/** The queue. */
	private OpenQueue queue;
	
	/** The nodes. */
	private final Hashtable<String, Node> nodes;
	
	/** The edges. */
	private final Hashtable<String, Edge> edges;
	
	/** The goal. */
	private final Hashtable<String, Edge> goal;
	
	/** The goal dup. */
	private Hashtable<String, List<String>> goalDup;
	
	/** The weights. */
	private Hashtable<String, Integer> weights;
	
	/** The goal dupped. */
	private Set<String> goalDupped;
	
	/** The goal single. */
	private List<String> goalSingle;
	
	/** The init heu. */
	private int initHeu;
	
	/***
	 * Class constructor.
	 */
	FreightSystem() {
		nodes = new Hashtable<String, Node>();
		edges = new Hashtable<String, Edge>();
		goal = new Hashtable<String, Edge>();
		queue = new OpenQueue();
		weights = new Hashtable<String, Integer>();
		goalDup = new Hashtable<String, List<String>>();
		goalSingle = new ArrayList<String>();
		goalDupped = new HashSet<String>();
		initHeu = 0;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public static void main(String[] args) throws IOException, ParseException {
		FreightSystem freightSystem = new FreightSystem();
		freightSystem.readRequest(args[0]);
		freightSystem.beginAstar();
	}
	
	/**
	 * read one line at one time from file.
	 *
	 * @param filename the filename
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 * @throw IOException, ParseException
	 */
	public void readRequest(String filename) throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				String lineRemove = line.split("\\#", 2)[0].trim();
				if(lineRemove!="") readInput(lineRemove);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			br.close();
		}
	}
	
	/**
	 * *
	 * 
	 * This function is dealing with the request,and dispatch this request into
	 * different method.
	 *
	 * @param request the request
	 */
	public void readInput(String request) {
		String[] req;
		//System.out.println(request);
		req = request.split(" ");
		try {
			switch (req[0]) {
			case "Unloading":
				addNode(req[1], req[2]);
				break;
			case "Cost":
				addEdge(req[1], req[2], req[3]);
				break;
			case "Job":
				addJob(req[1], req[2]);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.out.print("");
		}
	}
	
	/**
	 * Adds the node.
	 *
	 * @param co the co
	 * @param name the name
	 */
	public void addNode(String co, String name){
		int cost = Integer.parseInt(co);
		Node node = new Node(cost, name);
		nodes.put(name, node);
	}
	
	/**
	 * Adds the edge.
	 *
	 * @param we the we
	 * @param st the st
	 * @param en the en
	 */
	public void addEdge(String we, String st, String en){
		int weight = Integer.parseInt(we);
		weights.put(st+en, weight*2);
		weights.put(en+st, weight*2);
		Edge edgeStToEn = new Edge(weight, nodes.get(st), nodes.get(en), st+en);
		Edge edgeEnToSt = new Edge(weight, nodes.get(en), nodes.get(st), en+st);
		nodes.get(st).addEdge(edgeStToEn);
		nodes.get(en).addEdge(edgeEnToSt);
		edges.put(st+en, edgeStToEn);
		edges.put(en+st, edgeEnToSt);
	}
	
	/**
	 * Adds the job.
	 *
	 * @param st the st
	 * @param en the en
	 */
	public void addJob(String st, String en){
		int weight = weights.get(st+en);
		weights.put(st+en, weight/2);
		goal.put(st+en, edges.get(st+en));
		if(nodes.get(en).getEdges().size()!=1){
			if(goalDup.containsKey(st)) {
				goalDup.get(st).add(en);
			}
			else{
				List<String> temp = new ArrayList<String>();
				temp.add(en);
				goalDup.put(st, temp);
			}
		}
		else{
			goalSingle.add(st+en);
		}
	}
	
	/**
	 * Begin astar.
	 *
	 * @return true, if successful
	 */
	public boolean beginAstar(){
		//continue init heu
		if(goalSingle.size()==1){
			initHeu += goal.get(goalSingle.get(0)).getWeight();
		}
		else if(goalSingle.size()!=0){
			int index = findBiggestWeightIndex(goalSingle);
			for(int i=0; i<goalSingle.size(); i++){
				if(goal.containsKey(goal.get(goalSingle.get(i)).nameRverse())){//two dir goal
					initHeu += weights.get(goalSingle.get(i));
				}
				else if (i!=index){//not two dir not giggest
					//System.out.println("here adding dup heru");
					initHeu += weights.get(goalSingle.get(i))*(2+1);
					goalDupped.add(goalSingle.get(i));
				}
				else{//not two dir biggest
					initHeu += weights.get(goalSingle.get(i));
				}
			}
		}
		for(String start:goalDup.keySet()){
			int index=0;
			if(goalDup.get(start).size()>1){
				index = findBiggestWeightIndex(start, goalDup.get(start));
				for(int i=0; i<goalDup.get(start).size(); i++){
					if(goal.containsKey(goalDup.get(start).get(i)+start)){//two dir goal
						initHeu += weights.get(start+goalDup.get(start).get(i));
					}
					else if (i!=index){//not two dir not giggest
						//System.out.println("here adding dup heru"+weights.get(start+goalDup.get(start).get(i)));
						initHeu += weights.get(start+goalDup.get(start).get(i))*(2+1);
						goalDupped.add(start+goalDup.get(start).get(i));
					}
					else{//not two dir biggest
						initHeu += weights.get(start+goalDup.get(start).get(i));
					}
				}
			}
			else{
				initHeu += weights.get(start+goalDup.get(start).get(0));
			}		
		}
		//System.out.println("astar begin");
		for (Edge edge : nodes.get("Sydney").getEdges()){
			Set<String> goalCopy = new HashSet<String>();
			for(String g:goal.keySet()){
				goalCopy.add(g);
			}
			Heuristic hu = new Heuristic(goalCopy, nodes, edges, weights);
			Path p = new Path(edge, goalCopy, hu, initHeu, goalDupped);
			//System.out.println("push: h "+p.getH()+" g "+p.getG());
			//printPath(p);
			queue.push(p);
		}
		//int count = 0;
		while(!queue.isEmpty()){
			Path curPath = queue.pop();//find the path with min cost + heu
			//System.out.println("pop h "+curPath.getH()+" g "+curPath.getG()+" f "+curPath.getF());
			//printPath(curPath);
			//System.out.println("curgoal "+curPath.getGoal());
			if(curPath.hasFinished()){
				printPath(curPath);
				return true;
			}
			else{//not finished, push more
				Node curNode = curPath.getFrontNode();
				//System.out.println("curnode "+curNode.getName());
				for (Edge e:curNode.getEdges()){
					Path newPath = new Path(curPath, e);
					//System.out.println("push: h "+newPath.getH()+" g "+newPath.getG()+" f "+newPath.getF());
					//printPath(newPath);
					queue.push(newPath);
				}
			}
			//count++;
			//if(count==50) break;
		}
		return false;
	}
	
	
	/**
	 * Prints the path.
	 *
	 * @param curPath the cur path
	 */
	public void printPath(Path curPath){
		System.out.println(queue.getCount()+" nodes expanded");
		System.out.println("cost = "+curPath.getCost());
		for (Edge edge: curPath.getEdges()) {
			if(goal.containsKey(edge.getName())){
				System.out.println("Job "+edge.getStart().getName()+" to "+edge.getEnd().getName());
			}
			else{
				System.out.println("Empty "+edge.getStart().getName()+" to "+edge.getEnd().getName());
			}
		}
	}
	
	/**
	 * Find biggest weight index.
	 *
	 * @param start the start
	 * @param nodes the nodes
	 * @return the int
	 */
	public int findBiggestWeightIndex(String start, List<String> nodes){
		int max = weights.get(start+nodes.get(0));
		int index=0;
		for(int i=0; i<nodes.size(); i++){
			int temp = weights.get(start+nodes.get(i));
			if(max<temp){
				max=temp;
				index=i;
			}
		}
		return index;
	}
	
	/**
	 * Find biggest weight index.
	 *
	 * @param goles the goles
	 * @return the int
	 */
	public int findBiggestWeightIndex(List<String> goles){
		int max = weights.get(goles.get(0));
		int index=0;
		for(int i=0; i<goles.size(); i++){
			int temp = weights.get(goles.get(i));
			if(max<temp){
				max=temp;
				index=i;
			}
		}
		return index;
	}
}
