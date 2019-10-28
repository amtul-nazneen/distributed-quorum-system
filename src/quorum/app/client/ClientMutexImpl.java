package quorum.app.client;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.HashMap;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Thread Class that handles the Mutual Exclusion at Client
 */
public class ClientMutexImpl {
	private int pendingQuorumReply;
	HashMap<Integer, DataOutputStream> docsForQuorum;
	HashMap<Integer, Boolean> quorumReplies;
	private MessageCounter messageCounter;
	private int clientID;

	public ClientMutexImpl(int clientID) {
		super();
		init();
		this.clientID = clientID;
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

	public void updatePendingQuorumReply(int quorumID) {
		pendingQuorumReply--;
		quorumReplies.put(quorumID, true);
	}

	/**
	 * Update the sent messages count for File and Quorum Server
	 * 
	 * @param entity
	 */
	public void updateMessagesSent(String entity) {
		if (Constants.FILE_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesSentFileServer();
		else if (Constants.QUORUM_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesSentQuorumServer();
	}

	/**
	 * Update the received messages count for File and Quorum Server
	 * 
	 * @param entity
	 */
	public void updateMessagesReceived(String entity) {
		if (Constants.FILE_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesReceivedFileServer();
		else if (Constants.QUORUM_SERVER.equalsIgnoreCase(entity))
			this.messageCounter.updateMessagesReceivedQuorumServer();
	}

	public void updateCSMessages() {
		this.messageCounter.setCSMessages(this.messageCounter.getCSMessages() + 1);
	}

	/**
	 * Send request to selected quorum and wait for their reply Detect for deadlock
	 * after a fixed timeout value and log the circumstances in which deadlock
	 * occured
	 * 
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public boolean myCSRequestBegin(Timestamp time) throws Exception {
		setPendingQuorumReply(docsForQuorum.size());

		for (DataOutputStream dos : docsForQuorum.values()) {
			dos.writeUTF(Constants.REQUEST + "," + time);
			updateMessagesSent(Constants.QUORUM_SERVER);
			updateCSMessages();
		}
		Timestamp pollBegin = Utils.getTimestamp();
		while (pendingQuorumReply > 0) {
			try {
				Utils.log("Waiting for Grant....");
				Timestamp current = Utils.getTimestamp();
				int diff = Utils.getTimeDifference(pollBegin, current);
				if (diff >= Constants.DEADLOCK_TIMEOUT) {
					Utils.log("ALERT: Deadlock Detected !");
					Utils.log("===================== DEADLOCK SITUATION:" + clientID + " ===================== ");
					Utils.log("Deadlock occurred at Client:" + clientID + ". Details below.");
					Utils.log("CS Request made at: [" + time + "]");
					Utils.log("Quorum Request Set:" + Utils.getSelectedQuorumIDFromMap(docsForQuorum));
					Utils.log("Received Quorum Replies: " + Utils.getRepliedAndPendingQuorums(quorumReplies).get(0));
					Utils.log("Pending Quorum Replies Possibly Causing Deadlock: "
							+ Utils.getRepliedAndPendingQuorums(quorumReplies).get(1));
					String accessFile = Constants.HOME + Constants.CLIENT_LOG_FOLDER + Constants.CLIENT_LOG_FILE
							+ clientID + Constants.FILE_EXT;
					File f = new File(accessFile);
					FileWriter fw = new FileWriter(f, true);
					BufferedWriter filewriter = new BufferedWriter(fw);
					filewriter.write("===================== DEADLOCK SITUATION:" + clientID + " ===================== "
							+ Constants.EOL);
					filewriter.write("Deadlock occurred at Client:" + clientID + ". Details below." + Constants.EOL);
					filewriter.write("CS Request made at: [" + time + "]" + Constants.EOL);
					filewriter.write(
							"Quorum Request Set:" + Utils.getSelectedQuorumIDFromMap(docsForQuorum) + Constants.EOL);
					filewriter.write("Received Quorum Replies: "
							+ Utils.getRepliedAndPendingQuorums(quorumReplies).get(0) + Constants.EOL);
					filewriter.write("Pending Quorum Replies Possibly Causing Deadlock: "
							+ Utils.getRepliedAndPendingQuorums(quorumReplies).get(1) + Constants.EOL);
					filewriter.close();
					fw.close();

					Thread.sleep(100000);
				}
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * Send release to the quorum set
	 * 
	 * @throws Exception
	 */
	public void sendRelease() throws Exception {
		Utils.log("Sending RELEASE to my quorum");
		Timestamp releaseTime = Utils.getTimestamp();
		for (DataOutputStream dos : docsForQuorum.values()) {
			dos.writeUTF(Constants.RELEASE + "," + releaseTime);
			updateMessagesSent(Constants.QUORUM_SERVER);
			updateCSMessages();
		}
	}

	/**
	 * Set the map to the selected quorum
	 * 
	 * @param quorums
	 */
	public void mapQuorumDOS(HashMap<Integer, DataOutputStream> quorums) {
		docsForQuorum = quorums;
		initialiseQuorumReplies();
	}

	/**
	 * Initialise the quorum replies as false for all the quorum sets
	 */
	private void initialiseQuorumReplies() {
		quorumReplies = new HashMap<Integer, Boolean>();
		for (Integer id : docsForQuorum.keySet())
			quorumReplies.put(id, false);
	}

}
