import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatrixTest {

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void readtest() {
		AdjacentM macierz = new AdjacentM(0);
		macierz.odczytzpliku("plik.txt"); // Vertex: 0, 2, 4, 6, 8
		assertTrue(macierz.getLista().size() == 5);

		assertTrue(macierz.getLista().get(1).size() == 5);
		assertTrue(5 == macierz.getVertex().size());
	}

	@Test
	void addtest() {

		AdjacentM macierz = new AdjacentM(0);
		macierz.odczytzpliku("plik.txt");
		macierz.dodajkraw(2, 4, 1);
		macierz.dodajkraw(0, 8, 7);
		macierz.dodajkraw(8, 4, 5);
		macierz.dodajkraw(2, 2);
		assertTrue((macierz.getLista().get(1).get(2)).equals(1));
		assertTrue((macierz.getLista().get(2).get(1)).equals(1));
		assertTrue((macierz.getLista().get(1).get(1)).equals(1));
		assertThrows(IllegalArgumentException.class, () -> macierz.dodajkraw(3, 2));
	}

	@Test
	void deletetest() {
		AdjacentM macierz = new AdjacentM(0);
		macierz.odczytzpliku("plik.txt");
		macierz.usunKraw(2, 4);
		macierz.dodajkraw(2, 2);
		macierz.usunKraw(2, 2);
		assertTrue((macierz.getLista().get(1).get(2)).equals(0));
		assertTrue((macierz.getLista().get(2).get(1)).equals(0));
		assertTrue((macierz.getLista().get(1).get(1)).equals(0));
		assertThrows(IllegalArgumentException.class, () -> macierz.usunKraw(3, 2));
		assertThrows(IllegalArgumentException.class, () -> macierz.usunKraw(2, 5));// 3 and 5 not Vertex

	}

	@Test
	void addVertex() {

		AdjacentM macierz = new AdjacentM(0);
		macierz.odczytzpliku("plik.txt");
		macierz.addVertex(9);
		macierz.dodajkraw(0, 9);
		assertTrue(macierz.getLista().size() == 6); // row
		assertTrue(macierz.getLista().get(0).size() == 6); // column
		assertTrue(6 == macierz.getVertex().size()); // number of Vertex
		assertTrue(9 == macierz.getVertex().get(5));
		assertTrue(macierz.getLista().get(5).get(5).equals(0));
		assertTrue((macierz.getLista().get(0).get(5)).equals(1));
		assertTrue((macierz.getLista().get(5).get(0)).equals(1));
		assertThrows(IllegalArgumentException.class, () -> macierz.addVertex(4));
	}

	@Test
	void deleteVertex() {
		AdjacentM macierz = new AdjacentM(0);
		macierz.odczytzpliku("plik.txt");
		macierz.deleteVertex(4);
		assertTrue(macierz.getLista().size() == 4); // row
		assertTrue(macierz.getLista().get(0).size() == 4); // column
		assertTrue(4 == macierz.getVertex().size()); // number of Vertex
		assertThrows(IllegalArgumentException.class, () -> macierz.dodajkraw(4, 2)); // add Edge to nonexisting Vertex

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
