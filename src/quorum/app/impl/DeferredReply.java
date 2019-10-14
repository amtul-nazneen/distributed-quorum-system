package quorum.app.impl;

import java.sql.Timestamp;
import java.util.Comparator;

import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Class object for DeferredReply object
 */
public class DeferredReply implements Comparable<DeferredReply> {
	public boolean isDeferred;
	private int processNum;
	private Timestamp timestamp;

	/**
	 * Constructor for creating a deferred reply object
	 * 
	 * @param isDeferred: true/false
	 * @param processNum: process num of the deferred reply
	 * @param timestamp:  timestamp of the request before it had been deferred
	 */
	public DeferredReply(boolean isDeferred, int processNum, Timestamp timestamp) {
		super();
		this.isDeferred = isDeferred;
		this.processNum = processNum;
		this.timestamp = timestamp;
	}

	public boolean isDeferred() {
		return isDeferred;
	}

	public void setDeferred(boolean isDeferred) {
		this.isDeferred = isDeferred;
	}

	public int getProcessNum() {
		return processNum;
	}

	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(DeferredReply o) {
		return this.processNum - o.getProcessNum();
	}

	/**
	 * Comparator class to sort the deferred reply queues. First sorted based on
	 * timestamp and then process num, if timestamps are the same
	 */
	public static final Comparator<DeferredReply> DREP_COMP = new Comparator<DeferredReply>() {

		@Override
		public int compare(DeferredReply o1, DeferredReply o2) {
			int c = Utils.compareTimestamp(o1.getTimestamp(), o2.getTimestamp(), false);
			if (c == 0)
				c = o1.getProcessNum() - o2.getProcessNum();
			return c;
		}

	};

	@Override
	public String toString() {
		return "DeferredReply [isDeferred=" + isDeferred + ", processNum=" + processNum + ", timestamp=" + timestamp
				+ "]";
	}

}
