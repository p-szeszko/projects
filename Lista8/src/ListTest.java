import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest {

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void readtest() {
		AdjacentL macierz = new AdjacentL(0);
		macierz.odczytzpliku("plik2.txt"); // Vertex: 0, 2, 4, 6, 8
		assertTrue(macierz.getTable().size() == 5);

		assertTrue(macierz.getTable().get(8).size() == 3);
		assertTrue(5 == macierz.getKeys().size());
	}

	@Test
	void addtest() {

		AdjacentL macierz = new AdjacentL(0);
		macierz.odczytzpliku("plik2.txt");
		assertTrue(macierz.getTable().get(8).size() == 3); // column
		assertTrue(macierz.getTable().get(2).size() == 2); // column
		macierz.addEdge(8, 4, 5);
		macierz.addEdge(2, 2);
		macierz.addEdge(0, 0);
		assertTrue(macierz.getTable().get(2).size() == 3); // column
		assertTrue(macierz.getTable().get(0).size() == 2); // column
		assertTrue(macierz.getTable().get(8).size() == 3); // column
		assertThrows(IllegalArgumentException.class, () -> macierz.addEdge(3, 2));
		assertThrows(IllegalArgumentException.class, () -> macierz.addEdge(-1, -1));

	}

	@Test
	void deletetest() {
		AdjacentL macierz = new AdjacentL(0);
		macierz.odczytzpliku("plik2.txt");
		macierz.removeEdge(2, 4);
		macierz.addEdge(2, 2);
		macierz.removeEdge(2, 2);

		assertThrows(IllegalArgumentException.class, () -> macierz.removeEdge(3, 2));
		assertThrows(IllegalArgumentException.class, () -> macierz.removeEdge(2, 5));// 3 and 5 not Vertex

	}

	@Test
	void addVertextest() {

		AdjacentL macierz = new AdjacentL(0);
		macierz.odczytzpliku("plik2.txt");
		macierz.addVertex(9);
		macierz.addEdge(0, 9);
		assertTrue(macierz.getTable().size() == 6); // row
		assertTrue(macierz.getTable().size() == 6); // column
		assertTrue(6 == macierz.getKeys().size()); // number of Vertex
		assertTrue(9 == macierz.getKeys().get(5));
		assertTrue(1 == macierz.getTable().get(macierz.getKeys().get(5)).size());
		assertThrows(IllegalArgumentException.class, () -> macierz.addVertex(null));

	}

	@Test
	void deleteVertextest() {
		AdjacentL macierz = new AdjacentL(0);
		macierz.odczytzpliku("plik2.txt");
		macierz.removeVertex(4);

		assertTrue(macierz.getTable().get(0).size() == 1); // column
		assertTrue(4 == macierz.getKeys().size()); // number of Vertex
		assertThrows(IllegalArgumentException.class, () -> macierz.addEdge(4, 2));
		assertThrows(IllegalArgumentException.class, () -> macierz.removeVertex(null));
		assertThrows(IllegalArgumentException.class, () -> macierz.removeVertex(9));

	}

	public static <X extends Throwable> Throwable assertThrows(final Class<X> exceptionClass, final Runnable block) {
		try {
			block.run();
		} catch (Throwable ex) {
			if (exceptionClass.isInstance(ex))
				return ex;
		}
		fail("Failed to throw expected exception");
		return null;
	}
}
