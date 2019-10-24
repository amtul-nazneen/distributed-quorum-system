package quorum.app.quorumServer.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class QuorumTree {
	QNode root;
	List<Integer> quorum = new ArrayList<Integer>();
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	Random rand = new Random();

	public void createTree() {
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

	public static void main(String[] args) {
		QuorumTree qt = new QuorumTree();
		qt.createTree();
		for (int i = 0; i < 500; i++) {
			qt.clearQuorum();
			qt.displayRec(qt.root);
			qt.printQuorum();
		}
		qt.printMap();
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

	public void printQuorum() {
		String s = "";
		for (Integer i : quorum)
			s = s + i + ",";
		map.put(s.substring(0, s.length() - 1), 1);
	}

	public void clearQuorum() {
		quorum.clear();
	}

	public void printMap() {
		System.out.println("Quorum Size Total:--" + map.size());
		for (String key : map.keySet())
			System.out.println(key);
	}
}
