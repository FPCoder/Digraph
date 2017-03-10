
/**
 * Contains data from road.dat and the connections between the cities.
 * @author evan
 *
 */
public class Edge implements Comparable {
	private Road data;
	private int sourceInd; // source index
	private int destinationInd; // destination index
	
	/**
	 * Parametized Constructor
	 * @param r - Road data
	 * @param s - source index
	 * @param d - destination index
	 */
	public Edge(Road r, int s, int d) { 
		data = r;
		sourceInd = s;
		destinationInd = d;
	}
	public Road getData() { return data; }
	public int src() { return sourceInd; }
	public int dst() { return destinationInd; }
	public int compareTo(Object lhs) {
		return data.getDist() - ((Edge) lhs).getData().getDist();
	}
}
