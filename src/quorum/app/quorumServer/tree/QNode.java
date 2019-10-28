package quorum.app.quorumServer.tree;

/**
 * @author amtul.nazneen
 */

/**
 * Node class for the Binary Quorum Tree Structure
 */
public class QNode {
	int id;
	QNode left;
	QNode right;

	QNode(int id) {
		this.id = id;
		right = null;
		left = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public QNode getLeft() {
		return left;
	}

	public void setLeft(QNode left) {
		this.left = left;
	}

	public QNode getRight() {
		return right;
	}

	public void setRight(QNode right) {
		this.right = right;
	}

}
