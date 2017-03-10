
/**
 * Used for determining the shortest path between two cities.
 * @author evan
 *
 */
public class GraphNode implements Comparable {
	private int cityNumber; // cityNum of the node
	private int previousVertex; // cityNum of the previous node
	private double distance; // between the two nodes
	
	/**
	 * Parametized Constructor
	 * @param num - cityNum
	 * @param prev - previous cityNum
	 * @param dist - distance between nodes
	 */
	public GraphNode(int num, int prev, double dist) {
		cityNumber = num;
		previousVertex = prev;
		distance = dist;
	}
	
	public double dist() { return distance; }
	public int num() { return cityNumber; }
	public int prev() { return previousVertex; }

	@Override
	public int compareTo(Object arg0) {
		return (int) (distance - ((GraphNode) arg0).dist());
	}
}
