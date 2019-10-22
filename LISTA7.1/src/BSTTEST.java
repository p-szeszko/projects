import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BSTTEST {

	@Test
	public void testInsert() {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(10 == bst.getRoot().getData());
		assertTrue(8 == bst.getRoot().getLeft().getData());
		assertTrue(20 == bst.getRoot().getRight().getData());
		assertTrue(21 == bst.getRoot().getRight().getRight().getData());
		assertThrows(NullPointerException.class, () -> bst.insert(null));

	}

	@Test
	public void testSearch() {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		bst.insert(6);
		assertTrue(8 == bst.BSTSearch(bst.getRoot(), 8).getData());
		assertTrue(null == bst.BSTSearch(bst.getRoot(), 11));
		assertThrows(NullPointerException.class, () -> bst.BSTSearch(null));

	}

	@Test
	public void testBSTHeigh() {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(2 == bst.findHeight());
		BST bst2 = new BST();
		assertTrue(-1 == bst2.findHeight());
	}

	@Test
	public void testMin() {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(8 == bst.minValue());
		BST bst2 = new BST();
		assertThrows(NullPointerException.class, () -> bst2.minValue());

	}

	@Test
	public void testMax() {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(21 == bst.maxValue());
		BST bst2 = new BST();
		assertThrows(NullPointerException.class, () -> bst2.minValue());

	}

	@Test
	public void LeafTest() {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		assertTrue(2 == bst.getLeafCount());
		BST bst2 = new BST();
		assertThrows(NullPointerException.class, () -> bst2.getLeafCount());
	}

	@Test
	public void DeleteTest() {

		BST bst = new BST();
		bst.insert(10);
		bst.insert(20);
		bst.insert(21);
		bst.insert(8);
		bst.Delete(8);
		bst.Delete(10);
		assertTrue(null == bst.BSTSearch(bst.getRoot(), 8));
		assertTrue(null == bst.BSTSearch(bst.getRoot(), 10));
		assertTrue(20 == (bst.getRoot().getData()));
		assertThrows(NullPointerException.class, () -> bst.Delete(null));
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
