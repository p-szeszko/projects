import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class RBTest {

	@Test
	public void testInsert() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(20 == bst.getRoot().getKey());
		assertTrue(10 == bst.getRoot().getLeft().getKey());
		assertTrue(8 == bst.getRoot().getLeft().getLeft().getKey());
		assertTrue(21 == bst.getRoot().getRight().getKey());
		assertThrows(NullPointerException.class, () -> bst.insert(null));

	}

	@Test
	public void testSearch() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		bst.insert(6);

		assertTrue(8 == bst.find(8).getKey());
		assertTrue(null == bst.find(7));
		assertThrows(IllegalArgumentException.class, () -> bst.find(null));

	}

	@Test
	public void testRBHeigh() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(2 == bst.findHeight());
		RBtree2 bst2 = new RBtree2();
		assertTrue(-1 == bst2.findHeight());
	}

	@Test
	public void testMin() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(8 == bst.minValue());
		RBtree2 bst2 = new RBtree2();
		assertThrows(NullPointerException.class, () -> bst2.minValue());

	}

	@Test
	public void testMax() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(21 == bst.maxValue());
		RBtree2 bst2 = new RBtree2();
		assertThrows(NullPointerException.class, () -> bst2.maxValue());

	}

	@Test
	public void LeafTest() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(2 == bst.getLeafCount());
		RBtree2 bst2 = new RBtree2();
		assertThrows(NullPointerException.class, () -> bst2.getLeafCount());
	}

	@Test
	public void DeleteTest() {

		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		bst.delete(8);
		bst.delete(10);
		assertTrue(null == bst.find(10));
		assertTrue(null == bst.find(10));
		assertTrue(20 == (bst.getRoot().getKey()));
		assertThrows(IllegalArgumentException.class, () -> bst.delete(null));
	}

	@Test
	public void NodeTest() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(4 == bst.NodeNumber());

	}

	@Test
	public void IFNodeTest() {
		RBtree2 bst = new RBtree2();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(2 == bst.InferiorNodes());

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
