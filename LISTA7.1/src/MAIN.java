
import java.util.ArrayList;

import javax.swing.JFrame;

public class MAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BST bst = new BST();
		bst.insert(5);
		bst.insert(1);
		bst.insert(7);
		bst.insert(87);

		bst.insert(9);
		bst.insert(13);
		bst.insert(40);
		bst.insert(32);
		bst.insert(16);
		bst.insert(30);

		ArrayList<Integer> lista = new ArrayList<>();

		JFrame frame = bst.Drzewo2();
		frame.setVisible(true);

		RBtree2 bst2 = new RBtree2();
		lista = bst2.preOrder(bst2.getRoot());
		bst2.insert(1);
		bst2.insert(2);
		bst2.insert(2);
		bst2.insert(2);

		bst2.insert(5);
		bst2.insert(6);
		bst2.insert(7);
		bst2.insert(8);
		bst2.insert(9);
		bst2.insert(10);

		JFrame frame2 = bst2.Drzewo2();
		frame2.setVisible(true);
		System.out.println(
				bst2.find(7).getKey() + " " + bst2.NodeNumber() + " " + bst2.getI() + " " + bst2.getRoot().getKey());

	}
}
