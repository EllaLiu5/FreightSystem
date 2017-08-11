import java.util.*;

/**
* <h1>OpenQueue</h1>
* queue with current exiting paths in it.
*
* @author  Ella Liu
* @version 1.0
* @since   2017-05-1
*/
public class OpenQueue {
	
	/** The paths. */
	private List<Path> paths;
	
	/** The count. */
	private int count;
	
	/**
	 * Instantiates a new open queue.
	 */
	OpenQueue(){
		paths = new ArrayList<Path>();
		count = 0;
	}
	
	/**
	 * Pop the path with smallest f
	 *
	 * @return the path
	 */
	public Path pop(){
		//find the min heu
		int min = paths.get(0).getF();
		for(int i=0; i<paths.size();i++){
			if(min > paths.get(i).getF()){
				min = paths.get(i).getF();
			}
		}
		int minH = 0;
		int index = -1;
		for(int i=0; i<paths.size();i++){
			if(min == paths.get(i).getF()){
				if(index==-1){
					index=i;
					minH = paths.get(i).getH();
				}
				else{
					if( minH > paths.get(i).getH()){
						index=i;
						minH = paths.get(i).getH();
					}
				}
			}
		}
		Path p = paths.get(index);
		paths.remove(index);
		count++;
		//System.out.println("after pop: "+paths.size()+" count "+count);
		return p;
	}
	
	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Push path
	 *
	 * @param p the p
	 */
	public void push(Path p){
		paths.add(p);
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		if(paths.size() == 0){
			return true;
		}
		return false;
	}
}
