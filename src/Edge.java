
public class Edge implements Comparable {
	private Road data;
	private int sourceInd;
	private int destinationInd;
	
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
