package quorum.app.quorumServer.tree;

import java.util.ArrayList;
import java.util.List;

public class QuorumTree {
	QNode root;

	public void createTree() {
		/*
		 * QNode root = new QNode(1); QNode two = new QNode(2); QNode three = new
		 * QNode(3); QNode four = new QNode(4); QNode five = new QNode(5); QNode six =
		 * new QNode(6); QNode seven = new QNode(7);
		 */

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

	void printLevelOrder() {
		int h = height(root);
		int i;
		for (i = 1; i <= h; i++)
			printGivenLevel(root, i);
	}

	int height(QNode root) {
		if (root == null)
			return 0;
		else {
			int lheight = height(root.left);
			int rheight = height(root.right);
			if (lheight > rheight)
				return (lheight + 1);
			else
				return (rheight + 1);
		}
	}

	void printGivenLevel(QNode root, int level) {
		if (root == null)
			return;
		if (level == 1)
			System.out.print(root.id + " ");
		else if (level > 1) {
			printGivenLevel(root.left, level - 1);
			printGivenLevel(root.right, level - 1);
		}
	}

	public void caseI(QNode root, List<Integer> quorum) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		quorum.add(root.getId());
		caseI(root.left, quorum);

	}

	public void caseII(QNode root, List<Integer> quorum) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		quorum.add(root.getId());
		caseII(root.right, quorum);

	}

	public void caseIII(QNode root, List<Integer> quorum) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		caseIII(root.right, quorum);
		caseIII(root.left, quorum);

	}

	public void caseI_caseII(QNode root, List<Integer> quorum, int level) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		quorum.add(root.getId());
		level = level + 1;
		if (level % 2 == 0)
			caseI_caseII(root.left, quorum, level);
		else
			caseI_caseII(root.right, quorum, level);

	}

	public void caseII_caseI(QNode root, List<Integer> quorum, int level) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		quorum.add(root.getId());
		level = level + 1;
		if (level % 2 == 0)
			caseII_caseI(root.right, quorum, level);
		else
			caseII_caseI(root.left, quorum, level);

	}

	public void caseI_caseIII(QNode root, List<Integer> quorum, boolean include) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		if (include) {
			quorum.add(root.getId());
			include = false;
			root = root.left;
		}
		caseI_caseIII(root.left, quorum, include);
		caseI_caseIII(root.right, quorum, include);

	}

	public void caseIII_caseI(QNode root, List<Integer> quorum, boolean include) {
		if (root.left == null && root.right == null && include) {
			quorum.add(root.getId());
			return;
		}
		if (root.left == null && root.right == null && !include) {
			return;
		}
		if (include) {
			quorum.add(root.getId());
		}
		caseIII_caseI(root.left, quorum, true);
		caseIII_caseI(root.right, quorum, false);

	}

	public void caseIII_caseII(QNode root, List<Integer> quorum, boolean include) {
		caseIII(root, quorum);

	}

	public void caseII_caseIII(QNode root, List<Integer> quorum, boolean include) {
		if (root.left == null && root.right == null) {
			quorum.add(root.getId());
			return;
		}
		if (include) {
			quorum.add(root.getId());
			include = false;
			root = root.right;
		}
		caseII_caseIII(root.left, quorum, include);
		caseII_caseIII(root.right, quorum, include);

	}

	public static void main(String[] args) {
		QuorumTree qt = new QuorumTree();
		qt.createTree();
		// qt.printLevelOrder();
		List<Integer> quorum = new ArrayList<Integer>();
		System.out.println("\n---------- Case I----------");
		qt.caseI(qt.root, quorum);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
		quorum.clear();
		System.out.println("\n---------- Case II----------");
		qt.caseII(qt.root, quorum);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
		quorum.clear();
		System.out.println("\n---------- Case III----------");
		qt.caseIII(qt.root, quorum);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
		quorum.clear();
		System.out.println("\n---------- Case I and Case II----------");
		qt.caseI_caseII(qt.root, quorum, 1);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
		quorum.clear();
		System.out.println("\n---------- Case II and Case I----------");
		qt.caseII_caseI(qt.root, quorum, 1);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
		quorum.clear();
		System.out.println("\n---------- Case I and Case III----------");
		qt.caseI_caseIII(qt.root, quorum, true);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
		quorum.clear();
		System.out.println("\n---------- Case III and Case I----------");
		qt.caseIII_caseI(qt.root, quorum, false);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/

		quorum.clear();
		System.out.println("\n---------- Case II and Case III----------");
		qt.caseII_caseIII(qt.root, quorum, true);
		for (Integer id : quorum)
			System.out.print(id + ",");
		/*-----------------------------------------*/
	}
}
