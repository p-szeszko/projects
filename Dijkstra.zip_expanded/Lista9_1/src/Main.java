
public class Main {

	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.addVertex(1);graph.addVertex(2);graph.addVertex(3);graph.addVertex(4);graph.addVertex(5);graph.addVertex(6);
		graph.addEdge(1, 2, 4);graph.addEdge(2, 3, 7);graph.addEdge(1, 3, 3);graph.addEdge(1, 4, 8);graph.addEdge(4, 6, 2);graph.addEdge(2, 6, 1);graph.addEdge(3, 4, 5);
		graph.display();
		System.out.println(graph.dijkstra(1,4));
	}

}
