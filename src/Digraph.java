import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Digraph {
	private static Vertex[] verts; // vertices
	private static Edge[][] edges; // list of origin cities w/ destinations
	private String[] datFiles = { "city.dat", "road.dat" };
	
	public Digraph() {
		verts = new Vertex[21];
		edges = new Edge[21][21];
	}
	
	public void init() {
		readCity();
		readRoad();
	}
	
	public Vertex[] getVerts(){
		return verts;
	}
	
	/**
	 * 
	 * @param code - city code
	 * @return index of city w/ given code
	 */
	public int ind(String code) {
		return getCity(code).getNum();
	}
	
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
				entry = new Scanner(nextEntry);
				
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
	public void readRoad() {
		try {
			Scanner in = new Scanner(new FileReader(datFiles[1]));
			String nextEntry = in.nextLine();
			Road rd;
			Scanner entry;
			int start, end, dist;
			
			while (in.hasNext()) {
				entry = new Scanner(nextEntry);
				
				start= entry.nextInt();
				end = entry.nextInt();
				dist = entry.nextInt();
				rd = new Road(dist);

				edges[start][end] = new Edge(rd, start, end);

				nextEntry = in.nextLine();
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
			Vertex v = verts[i];
			if (v.getCity().getCode().equals(code)) {
				return v.getCity();
			}
		}
		
		return null;
	}
	
	public static Edge getEdge(String source, String destination) {
		System.out.println("src: " + source + " dst: " + destination); // FIXME
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
		if (getEdge(source, destination) == null) {
			int src = getCity(source).getNum();
			int dst = getCity(destination).getNum();
			edges[src][dst] = new Edge(new Road(dist), src, dst);
		}
		else {
			System.out.println("Road already exists between these cities");
		}
	}
	
	public static void checkEdge(String source, String destination) { // FIXME: debugging roads
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
	}
	
	public void remove(String source, String destination) {
		Edge ed = getEdge(source, destination);
		
		if (ed == null) {
			System.out.println("Road not found");
			return;
		}
		
		edges[ed.src()][ed.dst()] = null;
	}
	
	/**
	 * 
	 * @param v - number of city to get neighbors of
	 * @return - all unvisited neighbor City Nums
	 */
	public LinkedList<Integer> getNeighbors(int v) {
		LinkedList<Integer> vs = new LinkedList<Integer>();
		
		for (int i = 0, j = 0; i < verts.length; ++i) {
			if (edges[v][i] != null) {
				j = edges[v][i].dst();
			}
			else {
				continue;
			}
			if (!verts[j].isVisited()) {
				vs.add(i); // +1 changes index in array to City Num
			}
		}
		
		return vs;
	}
	
}
