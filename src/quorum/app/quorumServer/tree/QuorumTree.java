package quorum.app.quorumServer.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuorumTree {
	QNode root;
	List<Integer> quorum;
	Random rand;

	public QuorumTree() {
		createTree();
		quorum = new ArrayList<Integer>();
		rand = new Random();
	}

	private void createTree() {
		QNode root = new QNode(1);
		QNode two = new QNode(2);
		QNode three = new QNode(3);
		QNode four = new QNode(4);
		QNode five = new QNode(5);
		QNode six = new QNode(6);
		QNode seven = new QNode(7);
		root.left = two;
		root.right = three;
		two.left = four;
		two.right = five;
		three.left = six;
		three.right = seven;
		this.root = root;
	}

	public void displayRec(QNode root) {
		if (root == null)
			return;
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		int i = rand.nextInt(1000);
		if (i % 3 == 2) {
			quorum.add(root.id);
			root = root.left;
			displayRec(root);
		} else if (i % 3 == 1) {
			quorum.add(root.id);
			root = root.right;
			displayRec(root);
		} else if (i % 3 == 0) {
			displayRec(root.left);
			displayRec(root.right);
		}
	}

	public QNode getRoot() {
		return root;
	}

	public List<Integer> getQuorum() {
		return quorum;
	}

}