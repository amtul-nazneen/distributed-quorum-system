package quorum.app.quorumServer.tree;

import java.util.ArrayList;
import java.util.List;

public class QuorumGenerator {

	public static void main(String[] args) {
		QuorumGenerator qgen = new QuorumGenerator();
		List<Integer> quorum = qgen.generateQuorum();
		for (Integer id : quorum)
			System.out.print(id + ",");
	}

	public List<Integer> generateQuorum() {
		QuorumTree qt = new QuorumTree();
		qt.createTree();
		QNode root = qt.root;
		int i = 3;
		List<Integer> quorum = new ArrayList<Integer>();
		if (i == 1) {
			System.out.println("\n---------- Case I----------");
			qt.caseI(root, quorum);
		}
		if (i == 2) {
			System.out.println("\n---------- Case II----------");
			qt.caseII(root, quorum);
		}
		if (i == 3) {
			System.out.println("\n---------- Case III----------");
			qt.caseIII(root, quorum);
		}
		return quorum;

	}
}
