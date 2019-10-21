package quorum.app.quorumServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

class QuorumRequestHandler extends Thread {
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	String clientHost;
	QuorumMutexImpl quorumMutex;

	public QuorumRequestHandler(Socket s, DataInputStream dis, DataOutputStream dos, QuorumMutexImpl quorumMutex) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.quorumMutex = quorumMutex;
		clientHost = s.getInetAddress().getHostName();
	}

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
					Utils.log("Received REQUEST from :--------->" + Utils.getClientFromHost(clientHost) + " at "
							+ requestTimestamp);
					quorumMutex.updateMessagesReceivedFromClient();

					if (quorumMutex.getState().equalsIgnoreCase(Constants.UNLOCKED)) {
						Utils.log("State is UNLOCKED. Changing to LOCKED and Sending a GRANT");
						/* ---- ------- Setting state to locked------ ------ */
						quorumMutex.setState(Constants.LOCKED);
						Timestamp grantTimestamp = Utils.getTimestamp();
						/* ---- ------- Sending a grant------ ------ */
						dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
						quorumMutex.updateMessagesSentToClient();
						Utils.log("Sent GRANT to :--------->" + Utils.getClientFromHost(clientHost) + " at "
								+ grantTimestamp);
						Utils.log("My State:--------->" + quorumMutex.getState().toUpperCase());
					} else {
						Utils.log("State is LOCKED,Adding to queue");
						quorumMutex.getQueuedRequest().add(new QuorumQueuedRequest(
								Utils.getClientIDFromHost(clientHost), Timestamp.valueOf(requestTimestamp)));
					}
				} else if (Constants.RELEASE.equalsIgnoreCase(operation)) {
					Utils.log("Received RELEASE from :--------->" + Utils.getClientFromHost(clientHost) + " at "
							+ requestTimestamp);
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
									+ Utils.getClientFromID(chosenRequest.getProcessNum()) + " at " + grantTimestamp);
							Utils.log("My State:--------->" + quorumMutex.getState().toUpperCase());
						} else {
							Utils.log("Request queue is empty. Changing state to UNLOCKED");
							/* ---- ------- Setting state to unlocked------ ------ */
							quorumMutex.setState(Constants.UNLOCKED);
							Utils.log("My State:--------->" + quorumMutex.getState().toUpperCase());
						}
					}
				}
				Utils.log(" *** ----------- All Requests Completed, Terminating ----------- *** ");
				Utils.log("Total messages: " + quorumMutex.getTotalMessages());
				Utils.log("Total messages sent client: " + quorumMutex.getMessagesSentClient());
				Utils.log("Total messages received client: " + quorumMutex.getMessagesReceivedClient());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}