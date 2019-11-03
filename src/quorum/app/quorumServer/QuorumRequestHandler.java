package quorum.app.quorumServer;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.Socket;
import java.sql.Timestamp;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Thread class for handling the requests received from a client by the quorum
 * node
 */
class QuorumRequestHandler extends Thread {
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	String clientHost;
	QuorumMutexImpl quorumMutex;
	int quorumID;

	/**
	 * Constructor for creating the thread handler class when a client connects to q
	 * quorum
	 * 
	 * @param s
	 * @param dis
	 * @param dos
	 * @param quorumMutex
	 * @param quorumID
	 */
	public QuorumRequestHandler(Socket s, DataInputStream dis, DataOutputStream dos, QuorumMutexImpl quorumMutex,
			int quorumID) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.quorumMutex = quorumMutex;
		clientHost = s.getInetAddress().getHostName();
		this.quorumID = quorumID;
	}

	/**
	 * Thread run method For each request received from a client, performs the reply
	 * or defers it based on the quorums lock status
	 */
	@Override
	public void run() {
		String received;
		while (true) {
			try {
				received = dis.readUTF();
				String tokens[] = received.split(",");
				String operation = tokens[0];
				String requestTimestamp = tokens[1];
				if (Constants.REQUEST.equalsIgnoreCase(operation)) {
					Utils.log("Received REQUEST from :--------->" + Utils.getClientFromHost(clientHost) + " at " + "["
							+ requestTimestamp + "]");
					quorumMutex.updateMessagesReceivedFromClient();

					if (quorumMutex.getState().equalsIgnoreCase(Constants.UNLOCKED)) {
						Utils.log("State is UNLOCKED. Changing to LOCKED and Sending a GRANT");
						/* ---- ------- Setting state to locked------ ------ */
						quorumMutex.setState(Constants.LOCKED);
						Timestamp grantTimestamp = Utils.getTimestamp();
						/* ---- ------- Sending a grant------ ------ */
						dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
						quorumMutex.updateMessagesSentToClient();
						Utils.log("Sent GRANT to :--------->" + Utils.getClientFromHost(clientHost) + " at " + "["
								+ grantTimestamp + "]");
						Utils.log("My State: >>" + quorumMutex.getState().toUpperCase());
					} else {
						Utils.log("State is LOCKED. Adding to queue");
						quorumMutex.getQueuedRequest().add(new QuorumQueuedRequest(
								Utils.getClientIDFromHost(clientHost), Timestamp.valueOf(requestTimestamp)));
					}
				} else if (Constants.RELEASE.equalsIgnoreCase(operation)) {
					Utils.log("Received RELEASE from :--------->" + Utils.getClientFromHost(clientHost) + " at " + "["
							+ requestTimestamp + "]");
					quorumMutex.updateMessagesReceivedFromClient();
					if (Constants.LOCKED.equalsIgnoreCase(quorumMutex.getState())) {
						if (quorumMutex.getQueuedRequest() != null && !quorumMutex.getQueuedRequest().isEmpty()) {
							/* ---- ------- Chosing a request from queue------ ------ */
							QuorumQueuedRequest chosenRequest = quorumMutex.chooseFromDeferredQueue();
							DataOutputStream dos = quorumMutex.getClientDosMap().get(chosenRequest.getProcessNum());
							/* ---- ------- Sending grant------ ------ */
							Timestamp grantTimestamp = Utils.getTimestamp();
							dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
							quorumMutex.updateMessagesSentToClient();
							quorumMutex.deleteFromQuorumQueuedRequest();
							Utils.log("State is LOCKED. Picked a request from Queue. Sent GRANT to :--------->"
									+ Utils.getClientFromID(chosenRequest.getProcessNum()) + " at " + "["
									+ grantTimestamp + "]");
							Utils.log("My State: >>" + quorumMutex.getState().toUpperCase());
						} else {
							Utils.log("Request Queue Empty. Changing state to UNLOCKED");
							/* ---- ------- Setting state to unlocked------ ------ */
							quorumMutex.setState(Constants.UNLOCKED);
							Utils.log("My State: >>" + quorumMutex.getState().toUpperCase());
						}
					}
				}
				Utils.log("================= ================= =================");
				Utils.log("Total Messages: " + quorumMutex.getTotalMessages());
				Utils.log("Total Messages Sent to Clients: " + quorumMutex.getMessagesSentClient());
				Utils.log("Total Messages Received from Clients: " + quorumMutex.getMessagesReceivedClient());
				logDataCollectionToFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Logs the data collection to a file Quorum<quorumID>.txt
	 * 
	 * @throws Exception
	 */
	private void logDataCollectionToFile() throws Exception {
		String accessFile = Constants.HOME + Constants.QUORUM_LOG_FOLDER + Constants.QUORUM_LOG_FILE + quorumID
				+ Constants.FILE_EXT;
		File f = new File(accessFile);
		FileWriter fw = new FileWriter(f, false);
		BufferedWriter filewriter = new BufferedWriter(fw);
		filewriter.write("================= BEGIN DATA COLLECTION FOR QUORUM SERVER:" + quorumID + " =================="
				+ Constants.EOL);
		filewriter.write("Total Messages: " + quorumMutex.getTotalMessages() + Constants.EOL);
		filewriter.write("Total Messages Sent to Clients: " + quorumMutex.getMessagesSentClient() + Constants.EOL);
		filewriter.write(
				"Total Messages Received from Clients: " + quorumMutex.getMessagesReceivedClient() + Constants.EOL);
		filewriter.write("================= END DATA COLLECTION FOR QUORUM SERVER:" + quorumID + " =================="
				+ Constants.EOL);
		filewriter.close();
		fw.close();
	}
}