package quorum.app.quorumServer.tree;

public class QNode {
	int id;
	QNode left;
	QNode right;

	QNode(int id) {
		this.id = id;
		right = null;
		left = null;
	}
}
