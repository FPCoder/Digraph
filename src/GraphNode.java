
public class GraphNode implements Comparable {
	private int cityNumber;
	private int previousVertex;
	private double distance;
	
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
