import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;


public class Project3 {
	
	public static void shortestPath(Digraph g,
			String source, 
			String destination, 
			Stack<Integer> path) {
		Vertex[] verts = g.getVerts();
		boolean done = false;
		int vert = 0;
		PriorityQueue<Vertex> visitedVerts = new PriorityQueue<Vertex>();
		
		for (int i = 1; i < verts.length; ++i) {
			Vertex vertex = verts[i];
			if (vertex.getCity().getCode().equals(source)) {
				vert = vertex.ind();
			}
			if (vertex.isVisited()) {
				vertex.toggle();
			}
			dist[i] = Double.POSITIVE_INFINITY; // infinity
			prev[i] = -2; // undefined
		}
		
		verts[g.getCity(source).getNum()].toggle();
	}
	
	/*public static void shortestPath(Digraph g, String source, String destination) {
		Vertex[] verts = g.getVerts();
		boolean foundTarget = false;
		int target = Digraph.getCity(destination).getNum();
		int vert = 0;
		Double[] dist = new Double[verts.length];
		int[] prev = new int[verts.length]; // index of previous vertex
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		Stack<Integer> stk = new Stack<Integer>();
		
		// set all initial values
		for (int i = 1; i < verts.length; ++i) {
			Vertex vertex = verts[i];
			if (vertex.getCity().getCode().equals(source)) {
				vert = vertex.ind();
			}
			if (vertex.isVisited()) {
				vertex.toggle();
			}
			dist[i] = Double.POSITIVE_INFINITY; // infinity
			prev[i] = -2; // undefined
		}
		if (vert == 0) { // if city not found
			throw new IndexOutOfBoundsException("shortestpath(find)");
		}
		dist[vert] = 0.0;
		
		for (int i = 1; i < verts.length; ++i) {
			heap.add(verts[i].ind());
		}
		while (!heap.isEmpty()) {
			int u = heap.peek(); // city number
			//System.out.println("u: " + u); // FIXME
			if (dist[u] < 0.0) {
				System.out.println("inf value"); // FIXME
				break;
			}
			heap.remove();
			for (int v : g.getNeighbors(u)) {
				prev[v] = u;
				Double altDistance = dist[u] + Digraph.distBetween(u, v);
				//System.out.println("altd: " + altDistance); // FIXME
				
				if (altDistance < dist[v]) {
					dist[v] = altDistance;
					prev[v] = u;
				}
				//System.out.println("Compare: " + cityNumber + " " + target); // FIXME
				if (v == target) {
					foundTarget = true;
					break;
				}
			}
			if (foundTarget == true) break;
		}
		if (foundTarget) { // trace back path and print path in order
			//System.out.println("Found target"); // FIXME
			int start = Digraph.getCity(source).getNum();
			while (target != start) {
				//System.out.println("target: " + target); // FIXME
				stk.add(target);
				target = prev[target];
			}
			stk.add(start);
			System.out.println("The minimum distance between " +
					Digraph.getCity(source).getName() + " and " +
					Digraph.getCity(destination).getName() + " is " +
					getTotalDist(stk) + " through route: ");
			while (!stk.isEmpty()) {
				System.out.print(verts[stk.pop()].getCity().getCode() + ", ");
			}
			System.out.println();
		}
		else {
			//System.out.println("else"); // FIXME
			throw new IndexOutOfBoundsException("shortestpath(path not found)");
		}
	}*/
	
	public static int getTotalDist(Stack<Integer> stk) {
		int total = 0, curr = stk.pop(), prev;
		Stack<Integer> reverse = new Stack<Integer>();
		
		reverse.push(curr);
		while (!stk.isEmpty()) {
			prev = stk.pop();
			reverse.add(prev);
			total += Digraph.distBetween(curr, prev);
			curr = prev;
		}
		while (!reverse.isEmpty()) {
			stk.push(reverse.pop());
		}
		return total;
	}
	
	public static void main(String[] args) {
		Digraph dg = new Digraph();
		Scanner in = new Scanner(System.in);
		String input;
		Stack<Integer> path;

		dg.init();
		
		while (true) {
			try {
				System.out.print("Command? ");
				input = in.nextLine();
				switch(input.charAt(0)) {
				case 'Q':
					System.out.print("Enter city code: ");
					input = in.nextLine();
					if (input.length() < 2) {
						throw new IndexOutOfBoundsException("Query");
					}
					Digraph.query(input);
					break;
				case 'I':
					System.out.print("Enter city codes and distance: ");
					input = in.nextLine();
					if (input.length() < 7) {
						throw new IndexOutOfBoundsException("Insert");
					}
					Digraph.insert(input.substring(0, 2),
							input.substring(3, 5),
							Integer.parseInt(input.substring(6)));
					break;
				case 'R':
					System.out.print("Enter city codes: ");
					input = in.nextLine();
					if (input.length() != 5) {
						throw new IndexOutOfBoundsException("Remove");
					}
					dg.remove(input.substring(0, 2), input.substring(3));
					break;
				case 'D':
					System.out.print("Enter city codes: ");
					input = in.nextLine();
					if (input.length() != 5) {
						throw new IndexOutOfBoundsException("Distance");
					}
					shortestPath(dg, input.substring(0, 2), input.substring(3, 5));
					break;
				case 'C': //FIXME: debugging roads
					System.out.print("Enter city codes: ");
					input = in.nextLine();
					Digraph.checkEdge(input.substring(0, 2), input.substring(3));
					break;
				case 'E':
					System.out.println("Exiting...");
					in.close();
					return;
				default:
					System.out.println("Not a choice");
				}
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("input not correct format for " + e.getMessage());
			}
			System.out.println("----------------------------");
		}
	}
}
