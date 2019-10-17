package main2;

import java.io.DataOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class ClientMutexImpl {
	private int pendingQuorumReply;
	HashMap<Integer, DataOutputStream> docsForQuorum;
	private int processNum;

	public int getPendingQuorumReply() {
		return pendingQuorumReply;
	}

	public ClientMutexImpl(int processNum) {
		super();
		init();
		this.processNum = processNum;
	}

	private void init() {
		// this.pendingQuorumReply = 1;
		this.docsForQuorum = new HashMap<Integer, DataOutputStream>();
	}

	public void setPendingQuorumReply(int remreply) {
		this.pendingQuorumReply = remreply;
	}

	public void updatePendingQuorumReply() {
		pendingQuorumReply--;
	}

	public boolean myCSRequestBegin(Timestamp time, int setPendingQuorumReply) throws Exception {
		setPendingQuorumReply(setPendingQuorumReply);

		for (DataOutputStream dos : docsForQuorum.values()) {
			dos.writeUTF(Constants.REQUEST + "," + time);
			if (processNum == 1)
				Thread.sleep(processNum * 3000);
		}
		Utils.log("Sent request to all waiting for replies");
		while (pendingQuorumReply > 0) {
			try {
				Utils.log("sleeping for a while..");
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public void sendRelease() throws Exception {
		Utils.log("Sending RELEASE to my quorum");
		Timestamp releaseTime = Utils.getTimestamp();
		for (DataOutputStream dos : docsForQuorum.values()) {
			dos.writeUTF(Constants.RELEASE + "," + releaseTime);// dos.writeUTF(tosend);
		}
	}

	public void mapQuorumDOS(DataOutputStream dos1, DataOutputStream dos2) {
		docsForQuorum.put(1, dos1);
		docsForQuorum.put(2, dos2);
		// Utils.log("Mapped dos");
		// TODO: clear hashmap and add quorum worth writers
	}

}
