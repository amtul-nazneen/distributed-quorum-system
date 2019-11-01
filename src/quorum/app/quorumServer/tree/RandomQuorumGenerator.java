package quorum.app.quorumServer.tree;

import java.util.HashMap;
import java.util.List;

import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */
/**
 * Class that Randomly generates a quorum set each time
 */
public class RandomQuorumGenerator {
	public static List<Integer> getQuorum() {
		SingletonQuorumTree quorumTree = SingletonQuorumTree.getInstance();
		quorumTree.getQuorum().clear();
		quorumTree.recursiveQuorumSet(quorumTree.getRoot());
		return quorumTree.getQuorum();
	}

	/**
	 * Tested method for testing the quorum sets
	 */
	public static void test() {
		SingletonQuorumTree quorumTree = SingletonQuorumTree.getInstance();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 500; i++) {
			quorumTree.getQuorum().clear();
			quorumTree.recursiveQuorumSet(quorumTree.getRoot());
			String s = "";
			for (Integer id : quorumTree.getQuorum()) {
				s = s + id + ",";
			}
			map.put(s.substring(0, s.length() - 1), 1);
		}
		Utils.log("Quorum Size Total:--" + map.size());
		for (String key : map.keySet())
			System.out.println(key);
	}

	public static void main(String[] args) {
		test();
	}
}
