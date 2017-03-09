import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;


public class Project3 {
	
	public static void shortestPath(Digraph g, String source, String destination) {
		Vertex[] verts = g.getVerts();
		boolean foundTarget = false;
		int target = Digraph.getCity(destination).getNum();
		int v = 0;
		int[] dist = new int[verts.length];
		int[] prev = new int[verts.length]; // index of previous vertex
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		Stack<Integer> stk = new Stack<Integer>();
		
		// set all initial values
		for (int i = 0; i <= verts.length; ++i) {
			Vertex vertex = verts[i];
			if (vertex.getCity().getCode().equals(source)) {
				v = vertex.ind();
			}
			if (vertex.isVisited()) {
				vertex.toggle();
			}
			dist[i] = -1; // infinity
			prev[i] = -1; // undefined
		}
		dist[v - 1] = 0;
		
		if (v == 0) { // if city not found
			throw new IndexOutOfBoundsException("shortestpath(find)");
		}
		
		for (Vertex vertex : verts) {
			heap.add(vertex.ind());
		}
		while (!heap.isEmpty()) {
			int u = heap.peek(); // city number
			System.out.println("u: " + u); // FIXME
			/*if (dist[u - 1] < 0) {
				System.out.println("inf value"); // FIXME
				break;
			}*/
			heap.remove();
			for (int cityNumber : g.getNeighbors(u)) {
				int altDistance = addInf(dist[u - 1], Digraph.distBetween(u, cityNumber));
				System.out.println("altd: " + altDistance); // FIXME
				
				if (compareInf(altDistance, dist[cityNumber - 1]) > 0) {
					dist[cityNumber - 1] = altDistance;
					prev[cityNumber - 1] = u;
				}
				System.out.println("Compare: " + cityNumber + " " + target); // FIXME
				if (cityNumber == target) {
					foundTarget = true;
					break;
				}
			}
			if (foundTarget == true) break;
		}
		if (foundTarget) { // trace back path and print path in order
			System.out.println("Found target"); // FIXME
			int start = Digraph.getCity(source).getNum();
			while (target != start) {
				System.out.println("target: " + target); // FIXME
				stk.add(target);
				target = prev[target - 1];
			}
			System.out.print("The minimum distance between " +
					Digraph.getCity(source).getName() + " and " +
					Digraph.getCity(destination) + " is: ");
			while (!stk.isEmpty()) {
				System.out.print(verts[stk.pop() - 1].getCity().getCode() + ", ");
			}
		}
		else {
			System.out.println("else"); // FIXME
			throw new IndexOutOfBoundsException("shortestpath(end)");
		}
	}
	
	public static int addInf(int a, int b) {
		if (a < 0) return -1;
		if (b < 0) return -1;
		return a + b;
	}
	public static int compareInf(int a, int b) {
		if (a < 0) return 1;
		if (b < 0) return -1;
		return a - b;
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
