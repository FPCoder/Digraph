import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;


public class Project3 {
	
	public static void shortestPath(Digraph g, String source, String destination) {
		PriorityQueue<GraphNode> heap = new PriorityQueue<>();
		Stack<Integer> pathStk = new Stack<Integer>();
		Vertex[] verts = g.getVerts();
		GraphNode node;
		int[] prev = new int[21];
		int src = g.ind(source);
		int dst = g.ind(destination);
		double altDistance, minDistance;
		boolean found = false;
		
		for (int i = 1; i < verts.length; ++i) {
			verts[i].setValue(Double.POSITIVE_INFINITY);
			if (verts[i].isVisited())
				verts[i].toggle();
		}
		
		verts[src].setValue(0.0);
		heap.add(new GraphNode(g.ind(source), 0, 0));
		
		while (!heap.isEmpty()) {
			node = heap.remove();
			for (Integer v : g.getNeighbors(node.num())) {
				if (!verts[v].isVisited()) { // if node is not visited
					verts[v].toggle(); // visit the node
					
					// distance from source to node + distance of new edge
					altDistance = verts[node.num()].getVal() + 
							Digraph.distBetween(node.num(), v);
					minDistance = Math.min(verts[v].getVal(), altDistance);
					verts[v].setValue(minDistance);
					prev[v] = node.num();
					
					// and add it to the Queue
					heap.add(new GraphNode(v, node.num(), minDistance));
				}
				if (v == dst) {
					found = true;
				}
			}
			if (found == true) {
				break;
			}
		}
		if (found == false) {
			System.out.println("No path found between " + Digraph.getCity(source).getName() + " and " + 
					Digraph.getCity(destination).getName() + ".");
			return;
		}

		while (dst != src && dst > 0) { // city number stack from source to destination
			pathStk.add(dst);
			dst = prev[dst];
		}
		pathStk.add(src);

		System.out.println("The minimum distance between " + 
				Digraph.getCity(source).getName() + " and " +
				Digraph.getCity(destination).getName() + " is " +
				getTotalDist(pathStk) + " through the route: ");
		while (!pathStk.isEmpty()) {
			System.out.print(verts[pathStk.pop()].getCity().getCode());
			if (!pathStk.isEmpty()) System.out.print(", ");
		}
		System.out.println(".");
	}
	
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

		dg.init();
		
		while (true) {
			try {
				System.out.print("Command? ");
				input = in.nextLine();
				switch(input.charAt(0)) {
				case 'H':
					System.out.println("Q Query the city information by entering the city code.");
					System.out.println("D Find the minimum distance between two cities.");
					System.out.println("I Insert a road by entering two city codes and distance.");
					System.out.println("R Remove an existing road by entering two city codes.");
					System.out.println("H Display this message.");
					System.out.println("E Exit.");
					break;
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
					if (input.length() < 5) {
						throw new IndexOutOfBoundsException("Remove");
					}
					dg.remove(input.substring(0, 2), input.substring(3));
					break;
				case 'D':
					System.out.print("Enter city codes: ");
					input = in.nextLine();
					if (input.length() < 5) {
						throw new IndexOutOfBoundsException("Distance");
					}
					shortestPath(dg, input.substring(0, 2), input.substring(3, 5));
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
				System.out.println("exception thrown: " + e.getMessage());
			}
			System.out.println("----------------------------");
		}
	}
}
