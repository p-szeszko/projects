
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RBtree2 {

	private final int RED = 0;
	private final int BLACK = 1;
	private ArrayList<Integer> lista = new ArrayList<>();
	public JFrame frame = new JFrame("DrzewoRB");
	public JPanel panel = new JPanel();
	public int i;
	int przesuniecie;

	public int getI() {
		return this.i;
	}

	public class Node {

		int key = -1, color = BLACK;
		Node left = nil, right = nil, parent = nil;

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		Node(int key) {
			this.key = key;
		}
	}

	private final Node nil = new Node(-1);
	private Node root = nil;

	public RBtree2() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);

		panel.setLayout(null);
		i = 0;
		przesuniecie = 500;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public Node find(Integer data) {
		if (data == null)
			throw new IllegalArgumentException();
		Node node = new Node(data);
		return findNode(node, root);
	}

	private Node findNode(Node findNode, Node node) {
		if (root == nil) {
			return null;
		}

		if (findNode.key < node.key) {
			if (node.left != nil) {
				return findNode(findNode, node.left);
			}
		} else if (findNode.key > node.key) {
			if (node.right != nil) {
				return findNode(findNode, node.right);
			}
		} else if (findNode.key == node.key) {
			return node;
		}
		return null;
	}

	public void insert(Integer data) {
		Node node = new Node(data);
		insert2(node);
	}

	private void insert2(Node node) {
		Node temp = root;
		if (root == nil) {
			root = node;
			node.color = BLACK;
			node.parent = nil;
		} else {
			node.color = RED;
			while (true) {
				if (node.key < temp.key) {
					if (temp.left == nil) {
						temp.left = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if (node.key >= temp.key) {
					if (temp.right == nil) {
						temp.right = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			fixTree(node);
		}
	}

	private void fixTree(Node node) {
		while (node.parent.color == RED) {
			Node uncle = nil;
			if (node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.right) {

					node = node.parent;
					rotateLeft(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;

				rotateRight(node.parent.parent);
			} else {
				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {

					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;

				rotateLeft(node.parent.parent);
			}
		}
		root.color = BLACK;
	}

	void rotateLeft(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {
			Node right = root.right;
			root.right = right.left;
			right.left.parent = root;
			root.parent = right;
			right.left = root;
			right.parent = nil;
			root = right;
		}
	}

	void rotateRight(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {
			Node left = root.left;
			root.left = root.left.right;
			left.right.parent = root;
			root.parent = left;
			left.right = root;
			left.parent = nil;
			root = left;
		}
	}

	void deleteTree() {
		root = nil;
	}

	void transplant(Node target, Node with) {
		if (target.parent == nil) {
			root = with;
		} else if (target == target.parent.left) {
			target.parent.left = with;
		} else
			target.parent.right = with;
		with.parent = target.parent;
	}

	public void delete(Integer data) {
		if (data == null)
			throw new IllegalArgumentException();
		Node node = new Node(data);
		delete2(node);
	}

	public void delete2(Node z) {
		if ((z = findNode(z, root)) == null)
			throw new IllegalArgumentException();
		Node x;
		Node y = z;
		int y_original_color = y.color;

		if (z.left == nil) {
			x = z.right;
			transplant(z, z.right);
		} else if (z.right == nil) {
			x = z.left;
			transplant(z, z.left);
		} else {
			y = treeMinimum(z.right);
			y_original_color = y.color;
			x = y.right;
			if (y.parent == z)
				x.parent = y;
			else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (y_original_color == BLACK)
			deleteFixup(x);

	}

	void deleteFixup(Node x) {
		while (x != root && x.color == BLACK) {
			if (x == x.parent.left) {
				Node w = x.parent.right;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateLeft(x.parent);
					w = x.parent.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					x = x.parent;
					continue;
				} else if (w.right.color == BLACK) {
					w.left.color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = x.parent.right;
				}
				if (w.right.color == RED) {
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(x.parent);
					x = root;
				}
			} else {
				Node w = x.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateRight(x.parent);
					w = x.parent.left;
				}
				if (w.right.color == BLACK && w.left.color == BLACK) {
					w.color = RED;
					x = x.parent;
					continue;
				} else if (w.left.color == BLACK) {
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = x.parent.left;
				}
				if (w.left.color == RED) {
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(x.parent);
					x = root;
				}
			}
		}
		x.color = BLACK;
	}

	Node treeMinimum(Node subTreeRoot) {
		while (subTreeRoot.left != nil) {
			subTreeRoot = subTreeRoot.left;
		}
		return subTreeRoot;
	}

	public Integer minValue() {
		if (this.root == nil) {
			throw new NullPointerException();
		}
		return minValue(root);
	}

	public Integer minValue(Node root) {
		if (root.getLeft() != nil)
			return minValue(root.getLeft());

		return root.getKey();
	}

	public Integer findHeight() {

		return getNodeHeight(this.root);
	}

	private Integer getNodeHeight(Node node) {

		if (node == nil) {
			return -1;
		}

		return Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight())) + 1;
	}

	public int getLeafCount() {
		if (this.root == nil) {
			throw new NullPointerException();
		}
		return getLeafCount(root);
	}

	private int getLeafCount(Node node) {
		if (node == nil)
			return 0;
		if (node.left == nil && node.right == nil)
			return 1;
		else
			return getLeafCount(node.left) + getLeafCount(node.right);
	}

	public Integer maxValue() {
		if (this.root == nil) {
			throw new NullPointerException();
		}
		return maxValue(this.root);
	}

	public Integer maxValue(Node root) {
		if (root.getRight() != nil)
			return maxValue(root.getRight());
		return root.getKey();
	}

	public Integer NodeNumber() {
		return NodeNumber(root);
	}

	private Integer NodeNumber(Node root) {
		if (root == nil)
			return 0;
		return NodeNumber(root.getLeft()) + NodeNumber(root.getRight()) + 1;
	}

	public Integer InferiorNodes() {
		return InferiorNodes(root);
	}

	private Integer InferiorNodes(Node root) {
		return NodeNumber() - getLeafCount();
	}

	public ArrayList<Integer> preOrder(Node root) {

		if (root == nil)
			return null;

		lista.add(root.getKey());

		preOrder(root.getLeft());
		preOrder(root.getRight());
		return this.lista;
	}

	public JFrame Drzewo2() {
		Drzewo(this.root, 500, 30);
		this.frame.add(this.panel);
		return this.frame;
	}

	public void Drzewo(Node root, int x, int y) {
		if (root == nil)
			return;
		else {
			przesuniecie = przesuniecie / 2;
			this.i = i + 1;
			JTextField tekst = new JTextField(
					" " + root.getKey() + " " + root.getColor() + " Rodzic= " + root.getParent().getKey());
			tekst.setBounds(x, y, 100, 100);
			this.panel.add(tekst);
			Drzewo(root.getLeft(), x - przesuniecie - 20, y + 150);

			Drzewo(root.getRight(), x + przesuniecie + 120, y + 150);

		}

	}
}
