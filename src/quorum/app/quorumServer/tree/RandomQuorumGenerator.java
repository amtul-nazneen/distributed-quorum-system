package quorum.app.quorumServer.tree;

import java.util.HashMap;
import java.util.List;

/**
 * @author amtul.nazneen
 */
/**
 * Class that Randomly generates a quorum set each time
 */
public class RandomQuorumGenerator {
	public static List<Integer> getQuorum(int clientId) {
		QuorumTree qt = new QuorumTree();
		qt.recursiveQuorumSet(qt.getRoot());
		return qt.getQuorum();
	}

	/**
	 * Tested method for testing the quorum sets
	 */
	public static void test() {
		QuorumTree qt = new QuorumTree();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 500; i++) {
			qt.getQuorum().clear();
			qt.recursiveQuorumSet(qt.getRoot());
			String s = "";
			for (Integer id : qt.getQuorum()) {
				s = s + id + ",";
			}
			map.put(s.substring(0, s.length() - 1), 1);
		}
		System.out.println("Quorum Size Total:--" + map.size());
		for (String key : map.keySet())
			System.out.println(key);
	}
}
