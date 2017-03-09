

public class Vertex implements Comparable {
	private City data;
	private int value;
	private boolean visited;
	
	public Vertex(City ct) {
		data = ct;
	}
	public void setValue(int v) { value = v; }
	public void setCity(City ct) {
		data = ct;
	}
	public City getCity() { return data; }
	public int ind() { return data.getNum(); }
	public int getVal() { return value; }
	public boolean isVisited() { return visited; }
	public void toggle() { visited = !visited; }
	public int compareTo(Object arg0) {
		return ind() - ((Vertex) arg0).ind();
	}
}
