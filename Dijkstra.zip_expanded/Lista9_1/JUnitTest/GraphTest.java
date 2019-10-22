import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {
	private Graph graph1;
	private Graph graph2;
	private Graph graph3;
	private Graph graph4;

	@BeforeEach
	void setUp() throws Exception {
		//empty
		graph1= new Graph();
		
		graph2= new Graph();
		graph2.addVertex(1);graph2.addVertex(2);graph2.addVertex(3);
		graph2.addEdge(1, 2, 3);graph2.addEdge(1, 3, 1);graph2.addEdge(2, 3, 5);
		
		graph3= new Graph();
		graph3.addVertex(1);graph3.addVertex(2);graph3.addVertex(3);graph3.addVertex(4);graph3.addVertex(5);graph3.addVertex(6);
		graph3.addEdge(1, 2, 4);graph3.addEdge(2, 3, 7);graph3.addEdge(1, 3, 3);graph3.addEdge(1, 4, 8);graph3.addEdge(4, 6, 2);graph3.addEdge(2, 6, 1);graph3.addEdge(3, 4, 5);
		
		graph4= new Graph();
		graph4.addVertex(1);graph4.addVertex(2);graph4.addVertex(3);
		graph4.addEdge(1,3,7);
	}
	@Test
	void testDijkstra() {
		assertThrows(IllegalArgumentException.class, () -> graph1.dijkstra(3, 7));
		assertEquals((Integer)1,graph2.dijkstra(1, 3));
		assertEquals((Integer)7,graph3.dijkstra(1, 4));
		assertEquals((Integer)Integer.MAX_VALUE,graph4.dijkstra(1, 2));
	}

}
