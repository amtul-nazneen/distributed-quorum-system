package quorum.app.client;

import java.io.DataOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class ClientMutexImpl {
	private int pendingQuorumReply;
	HashMap<Integer, DataOutputStream> docsForQuorum;
	private int processNum;
	// private int messagesSent;
	// private int messagesReceived;
	private MessageCounter messageCounter;

	public ClientMutexImpl(int processNum) {
		super();
		init();
		this.processNum = processNum;
	}

	private void init() {
		this.docsForQuorum = new HashMap<Integer, DataOutputStream>();
		// this.messagesSent = 0;
		// this.messagesReceived = 0;
		this.messageCounter = new MessageCounter();
	}

	/*
	 * public int getMessagesSent() { return messagesSent; }
	 * 
	 * public int getMessagesReceived() { return messagesReceived; }
	 */

	public MessageCounter getMessageCounter() {
		return messageCounter;
	}

	public int getPendingQuorumReply() {
		return pendingQuorumReply;
	}

	public void setPendingQuorumReply(int remreply) {
		this.pendingQuorumReply = remreply;
	}

	public void updatePendingQuorumReply() {
		pendingQuorumReply--;
	}

	public void updateMessagesSent(String entity) {
		// messagesSent = messagesSent + 1;
		// Utils.log("Updated messages sent:" + messagesSent);
		if (Constants.FILE_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesSentFileServer();
		else if (Constants.QUORUM_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesSentQuorumServer();
	}

	public void updateMessagesReceived(String entity) {
		// messagesReceived = messagesReceived + 1;
		// Utils.log("Updated messages received:" + messagesReceived);
		if (Constants.FILE_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesReceivedFileServer();
		else if (Constants.QUORUM_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesReceivedQuorumServer();
	}

	public boolean myCSRequestBegin(Timestamp time, int setPendingQuorumReply) throws Exception {
		setPendingQuorumReply(setPendingQuorumReply);

		for (DataOutputStream dos : docsForQuorum.values()) {
			dos.writeUTF(Constants.REQUEST + "," + time);
			updateMessagesSent(Constants.QUORUM_SERVER);
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
			dos.writeUTF(Constants.RELEASE + "," + releaseTime);
			updateMessagesSent(Constants.QUORUM_SERVER);
		}
	}

	public void mapQuorumDOS(DataOutputStream dos1, DataOutputStream dos2) {// , DataOutputStream dos3, DataOutputStream
																			// dos4,
		// DataOutputStream dos5, DataOutputStream dos6, DataOutputStream dos7) {
		docsForQuorum.clear();
		docsForQuorum.put(1, dos1);
		docsForQuorum.put(2, dos2);
		/*
		 * docsForQuorum.put(3, dos3); docsForQuorum.put(4, dos4); docsForQuorum.put(5,
		 * dos5); docsForQuorum.put(6, dos6); docsForQuorum.put(7, dos7);
		 */
		// TODO: clear hashmap and add quorum worth writers
	}

}
