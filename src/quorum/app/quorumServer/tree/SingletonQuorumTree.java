package quorum.app.quorumServer.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author amtul.nazneen
 */

/**
 * Quorum Binary Tree class
 */
public class SingletonQuorumTree {

	private QNode root;
	private List<Integer> quorum;
	private Random rand;
	private static SingletonQuorumTree instance = null;

	public static SingletonQuorumTree getInstance() {
		if (instance == null) {
			instance = new SingletonQuorumTree();
		}
		return instance;
	}

	private SingletonQuorumTree() {
		createTree();
		quorum = new ArrayList<Integer>();
		rand = new Random();
	}

	/**
	 * Creates a tree with with 7 nodes as full binary tree
	 */
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

	/**
	 * Algorithm for randomly choosing a quorum set based on the 3 recursion rules
	 * 
	 * @param root
	 */
	public void recursiveQuorumSet(QNode root) {
		if (root == null)
			return;
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		int i = rand.nextInt(3);
		if (i == 2) {
			quorum.add(root.id);
			root = root.left;
			recursiveQuorumSet(root);
		} else if (i == 1) {
			quorum.add(root.id);
			root = root.right;
			recursiveQuorumSet(root);
		} else if (i == 0) {
			recursiveQuorumSet(root.left);
			recursiveQuorumSet(root.right);
		}
	}

	public QNode getRoot() {
		return root;
	}

	public List<Integer> getQuorum() {
		return quorum;
	}

}