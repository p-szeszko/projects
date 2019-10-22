
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BST {
	public class BSTNode {
		private BSTNode left;
		private BSTNode right;
		private Integer data;

		public BSTNode(Integer data) {
			this.data = data;
			left = right = null;
		}

		public BSTNode getLeft() {
			return left;
		}

		public void setLeft(BSTNode left) {
			this.left = left;
		}

		public BSTNode getRight() {
			return right;
		}

		public void setRight(BSTNode right) {
			this.right = right;
		}

		public Integer getData() {
			return data;
		}

		public void setData(Integer data) {
			this.data = data;
		}
	}

	private BSTNode root;
	public JFrame frame = new JFrame("DrzewoBST");
	public JPanel panel = new JPanel();

	ArrayList<Integer> lista = new ArrayList<>();

	public BSTNode getRoot() {
		return this.root;
	}

	public BST() {
		root = null;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);

		panel.setLayout(null);
	}

	public void insert(Integer data) {
		if (root == null) {
			this.root = new BSTNode(data);

			return;
		}
		insertNode(this.root, data);

	}

	public BSTNode insertNode(BSTNode root, Integer data)

	{
		if (data == null) {
			throw new NullPointerException();
		}
		BSTNode temp = null;
		if (data <= root.getData()) {
			if (root.getLeft() == null) {
				root.setLeft(new BSTNode(data));
				return root.getLeft();
			} else
				temp = root.getLeft();
		} else {
			if (root.getRight() == null) {
				root.setRight(new BSTNode(data));
				return root.getRight();
			} else {
				temp = root.getRight();
			}
		}
		return insertNode(temp, data);

	}

	public BSTNode BSTSearch(Integer data) {
		return BSTSearch(this.root, data);
	}

	public BSTNode BSTSearch(BSTNode root, Integer data) {
		if (data == null) {
			throw new NullPointerException();
		}

		if (root == null || root.data == data) {
			return root;
		}

		if (root.data > data) {
			return BSTSearch(root.getLeft(), data);
		} else if (root.data < data) {
			return BSTSearch(root.getRight(), data);

		}
		return root;

	}

	public void Delete(Integer data) {
		root = DeleteBST(root, data);
	}

	public BSTNode DeleteBST(BSTNode root, Integer data) {
		if (root == null) {
			return root;
		}
		if (data < root.data)
			root.left = DeleteBST(root.getLeft(), data);
		else if (data > root.data)
			root.right = DeleteBST(root.getRight(), data);

		else if (root.getLeft() == null && root.getRight() == null) {
			return null;
		} else if (root.getLeft() == null) {
			return root.getRight();
		} else if (root.getRight() == null) {
			return root.getLeft();
		} else {
			Integer minValue = minValue(root.getRight());
			root.setData(minValue);
			root.setRight(DeleteBST(root.getRight(), minValue));
		}
		return root;
	}

	public Integer minValue() {
		if (this.root == null) {
			throw new NullPointerException();
		}
		return minValue(root);
	}

	public Integer minValue(BSTNode root) {
		if (root.getLeft() != null)
			return minValue(root.getLeft());

		return root.getData();
	}

	public Integer findHeight() {

		return getNodeHeight(this.root);
	}

	private Integer getNodeHeight(BSTNode node) {

		if (node == null) {
			return -1;
		}

		return Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight())) + 1;
	}

	public int getLeafCount() {
		if (this.root == null) {
			throw new NullPointerException();
		}
		return getLeafCount(root);
	}

	private int getLeafCount(BSTNode node) {
		if (node == null)
			return 0;
		if (node.left == null && node.right == null)
			return 1;
		else
			return getLeafCount(node.left) + getLeafCount(node.right);
	}

	public Integer maxValue() {
		if (this.root == null) {
			throw new NullPointerException();
		}
		return maxValue(root);
	}

	public Integer maxValue(BSTNode root) {
		if (root.getRight() != null)
			return maxValue(root.getRight());
		return root.getData();
	}

	public Integer NodeNumber() {
		return NodeNumber(root);
	}

	private Integer NodeNumber(BSTNode root) {
		if (root == null)
			return 0;
		return NodeNumber(root.getLeft()) + NodeNumber(root.getRight()) + 1;
	}

	public Integer InferiorNodes() {
		return InferiorNodes(root);
	}

	private Integer InferiorNodes(BSTNode root) {
		return NodeNumber() - getLeafCount();
	}

	public ArrayList<Integer> preOrder(BSTNode root) {

		if (root == null)
			return null;

		lista.add(root.getData());
		preOrder(root.getLeft());

		preOrder(root.getRight());

		return this.lista;
	}

	public JFrame Drzewo2() {
		Drzewo(this.root, frame.getWidth() / 2, 30);
		this.frame.add(this.panel);
		return this.frame;
	}

	public void Drzewo(BSTNode root, int x, int y) {
		if (root == null)
			return;

		JTextField tekst = new JTextField(" " + root.getData());
		tekst.setBounds(x, y, 40, 40);
		this.panel.add(tekst);

		Drzewo(root.getLeft(), x - 40, y + 70);

		Drzewo(root.getRight(), x + 70, y + 70);

	}

}
