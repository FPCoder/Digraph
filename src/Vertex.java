

public class Vertex implements Comparable {
	private City data;
	private double value; // used for finding shortest path, current shortest distance
	private boolean visited; // used for finding shortest path
	
	public Vertex(City ct) {
		data = ct;
	}
	public void setValue(double v) { value = v; }
	public City getCity() { return data; }
	
	/**
	 * returns the city number of the data's city
	 * @return data.getNum() = cityNum
	 */
	public int ind() { return data.getNum(); }
	
	/**
	 * Get current shortest distance in getShortestDistance()
	 * @return shortest distance from source to this
	 */
	public double getVal() { return value; }
	public boolean isVisited() { return visited; }
	public void toggle() { visited = !visited; }
	public int compareTo(Object arg0) {
		return ind() - ((Vertex) arg0).ind();
	}
}
