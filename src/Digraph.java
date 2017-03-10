import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * A Directional Graph contining all the information from the files
 * city.dat and road.dat
 * Consists of Cities(Vertecies) and Roads(Edges).
 * @author evan
 *
 */
public class Digraph {
	private static Vertex[] verts; // vertices
	private static Edge[][] edges; // list of origin cities w/ destinations
	private String[] datFiles = { "city.dat", "road.dat" };
	
	/**
	 * Default Constructor
	 * Memory allocated for the vertices and edges cannot be increased,
	 * so the arrays cannot be expanded.
	 */
	public Digraph() {
		verts = new Vertex[21];
		edges = new Edge[21][21];
	}
	
	/**
	 * Reads the city and road files
	 */
	public void init() {
		readCity();
		readRoad();
	}
	
	/**
	 * Returns an array of vertices for manipulation in the driver
	 * @return array of Vertices
	 */
	public Vertex[] getVerts(){
		return verts;
	}
	
	/**
	 * Find the CityNumber (or index) from the given city code.
	 * @param code - city code
	 * @return index of city w/ given code
	 */
	public int ind(String code) {
		return getCity(code).getNum();
	}
	
	/**
	 * Reads city.dat and places the data into "verts".
	 */
	public void readCity() {
		try {
			Scanner in = new Scanner(new FileReader(datFiles[0]));
			String nextEntry;
			Scanner entry;
			City ct;
			int num, pop, elev;
			String code, name;
			
			while (in.hasNext()) {
				nextEntry = in.nextLine(); // prepare next line
				entry = new Scanner(nextEntry); // read next line from city.dat
				
				num = entry.nextInt();
				code = entry.next();
				name = entry.next();
				if (!entry.hasNextInt()) { // if city name has two words, add it
					name += entry.next();
				}
				pop = entry.nextInt();
				elev = entry.nextInt();
				ct = new City(num, code, name, pop, elev);
				
				verts[num] = new Vertex(ct);
				
				entry.close();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read road.dat and places the data into "edges".
	 */
	public void readRoad() {
		try {
			Scanner in = new Scanner(new FileReader(datFiles[1]));
			String nextEntry;
			Road rd;
			Scanner entry;
			int start, end, dist;
			
			while (in.hasNext()) {
				nextEntry = in.nextLine(); // put line into String var
				entry = new Scanner(nextEntry); // read next line from file
				
				start= entry.nextInt();
				end = entry.nextInt();
				dist = entry.nextInt();
				rd = new Road(dist);

				edges[start][end] = new Edge(rd, start, end);

				entry.close();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Find a city by it's cityCode.
	 * @param code - cityCode
	 * @return City with matching cityCode, or null if no match found
	 */
	public static City getCity(String code) {
		for (int i = 1; i < verts.length; ++i) {
			if (verts[i].getCity().getCode().equals(code)) {
				return verts[i].getCity();
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param source - source of road
	 * @param destination - end of road
	 * @return Edge var between cities
	 */
	public static Edge getEdge(String source, String destination) {
		int src = getCity(source).getNum();
		int dst = getCity(destination).getNum();
		
		return edges[src][dst];
	}
	
	/**
	 * 
	 * @param src - City Num of source city
	 * @param dst - City Num of destination city
	 * @return - distance between the two cities
	 */
	public static int distBetween(int src, int dst) {
		if (edges[src][dst] == null) {
			return -1; // infinity
		}
		return edges[src][dst].getData().getDist();
	}
	
	/**
	 * Display information for a city, given the cityCode
	 * @param code - cityCode for the city being searched
	 */
	public static void query(String code) {
		City ct = getCity(code);
		
		if (ct == null) {
			System.out.println("City not found");
		}
		else {
			System.out.println(
					ct.getNum() + " " +
					ct.getCode() + " " +
					ct.getName() + " " +
					ct.getPop() + " " +
					ct.getElev());
		}
	}
	
	/**
	 * Insert a new road between the two cities
	 * @param source - source of the road
	 * @param destination - destination of the road
	 */
	public static void insert(String source, String destination, int dist) {
		if (getEdge(source, destination) == null) { // if no existing road, add it
			int src = getCity(source).getNum();
			int dst = getCity(destination).getNum();
			edges[src][dst] = new Edge(new Road(dist), src, dst);
		}
		else { // road already exists
			System.out.println("Road already exists between these cities");
		}
	}
	
	/*public static void checkEdge(String source, String destination) { // FIXME: debugging roads
		if (getEdge(source, destination) == null) {
			System.out.println("Road not found");
		}
		else {
			Edge ed = getEdge(source, destination);
			int src = ed.src();
			int dst = ed.dst();
			
			System.out.println("Distance between " +
					verts[src].getCity().getName() + " and " +
					verts[dst].getCity().getName() + " is " + 
					ed.getData().getDist() + ".");
		}
	}*/
	
	/**
	 * Remove a road between two cities.
	 * @param source - source city
	 * @param destination - end city
	 */
	public void remove(String source, String destination) {
		Edge ed = getEdge(source, destination);
		
		if (ed == null) {
			System.out.println("Road not found");
			return;
		}
		
		edges[ed.src()][ed.dst()] = null;
	}
	
	/**
	 * Returns a list of the neighbors for the given cityNumber
	 * @param v - cityNumber to get neighbors of
	 * @return - all unvisited neighbors of cityNum City
	 */
	public LinkedList<Integer> getNeighbors(int v) {
		LinkedList<Integer> vs = new LinkedList<Integer>(); // cityNumbers to ret
		
		// read the entire row of a given city, viewing all possible connections
		for (int i = 0, j = 0; i < verts.length; ++i) {
			if (edges[v][i] != null) { // i = edge connecting cities
				j = edges[v][i].dst(); // j = cityNum of connected city
			}
			else {
				continue;
			}
			if (!verts[j].isVisited()) {
				vs.add(i);
			}
		}
		
		return vs;
	}
	
}
