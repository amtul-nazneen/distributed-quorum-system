package quorum.app.quorumServer.tree;

import java.util.ArrayList;
import java.util.List;

public class QuorumTree2 {
	QNode root;

	public void createTree() {
		QNode root = new QNode(4);
		QNode two = new QNode(2);
		QNode three = new QNode(6);
		QNode four = new QNode(1);
		QNode five = new QNode(3);
		QNode six = new QNode(5);
		QNode seven = new QNode(7);
		root.left = two;
		root.right = three;
		two.left = four;
		two.right = five;
		three.left = six;
		three.right = seven;
		this.root = root;
	}

	public QNode returnLeft(QNode root, List<Integer> quorum) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return null;
		}
		// quorum.add(root.getId());
		return root.left;

	}

	public QNode returnRight(QNode root, List<Integer> quorum) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return null;
		}
		// quorum.add(root.getId());
		return root.right;

	}

	public ArrayList<QNode> returnLeftAndRight(QNode root, List<Integer> quorum) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return null;
		}
		ArrayList<QNode> list = new ArrayList<QNode>();
		list.add(root.left);
		list.add(root.right);
		return list;

	}

	public void caseI_caseII(QNode root, List<Integer> quorum, int level) {

	}

	public static void main(String[] args) {
		QuorumTree2 qt = new QuorumTree2();
		qt.createTree();
		List<Integer> quorum = new ArrayList<Integer>();
		System.out.println("\n---------- Case I----------");

	}
}
