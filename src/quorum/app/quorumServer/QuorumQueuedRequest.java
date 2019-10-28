package quorum.app.quorumServer;

import java.sql.Timestamp;
import java.util.Comparator;

import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Class object for Quorum Deferred Request object
 */
public class QuorumQueuedRequest implements Comparable<QuorumQueuedRequest> {
	private int clientNum;
	private Timestamp timestamp;

	/**
	 * Constructor for creating a Quorum Deferred Request object
	 * 
	 * @param clientNum: clientNum of the deferred reply
	 * @param timestamp: timestamp of the request before it had been deferred
	 */
	public QuorumQueuedRequest(int clientNum, Timestamp timestamp) {
		super();
		this.clientNum = clientNum;
		this.timestamp = timestamp;
	}

	public int getProcessNum() {
		return clientNum;
	}

	public void setProcessNum(int processNum) {
		this.clientNum = processNum;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(QuorumQueuedRequest o) {
		return this.clientNum - o.getProcessNum();
	}

	/**
	 * Comparator class to sort the deferred reply queues. First sorted based on
	 * timestamp and then clientID, if timestamps are the same
	 */
	public static final Comparator<QuorumQueuedRequest> QUORUM_REQ_COMP = new Comparator<QuorumQueuedRequest>() {

		@Override
		public int compare(QuorumQueuedRequest o1, QuorumQueuedRequest o2) {
			int c = Utils.compareTimestamp(o1.getTimestamp(), o2.getTimestamp(), false);
			if (c == 0)
				c = o1.getProcessNum() - o2.getProcessNum();
			return c;
		}

	};
}
