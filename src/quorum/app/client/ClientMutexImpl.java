package quorum.app.client;

import java.io.DataOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class ClientMutexImpl {
	private int pendingQuorumReply;
	HashMap<Integer, DataOutputStream> docsForQuorum;
	private MessageCounter messageCounter;

	public ClientMutexImpl(int processNum) {
		super();
		init();
	}

	private void init() {
		this.docsForQuorum = new HashMap<Integer, DataOutputStream>();
		this.messageCounter = new MessageCounter();
	}

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
		if (Constants.FILE_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesSentFileServer();
		else if (Constants.QUORUM_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesSentQuorumServer();
	}

	public void updateMessagesReceived(String entity) {
		if (Constants.FILE_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesReceivedFileServer();
		else if (Constants.QUORUM_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesReceivedQuorumServer();
	}

	public void updateCSMessages() {
		this.messageCounter.setCSMessages(this.messageCounter.getCSMessages() + 1);
	}

	public boolean myCSRequestBegin(Timestamp time) throws Exception {
		setPendingQuorumReply(docsForQuorum.size());

		for (DataOutputStream dos : docsForQuorum.values()) {
			dos.writeUTF(Constants.REQUEST + "," + time);
			updateMessagesSent(Constants.QUORUM_SERVER);
			updateCSMessages();
		}
		while (pendingQuorumReply > 0) {
			try {
				Utils.log("Waiting for Grant....");
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
			updateCSMessages();
		}
	}

	public void mapQuorumDOS(HashMap<Integer, DataOutputStream> quorums) {
		docsForQuorum = quorums;
	}

}
