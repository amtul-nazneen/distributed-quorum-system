package quorum.app.quorumServer.tree;

public class QuorumTree {
	QNode root;

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

	public static void main(String[] args) {
		QuorumTree qt = new QuorumTree();
		qt.createTree();
		qt.printLevelOrder();
	}
}
