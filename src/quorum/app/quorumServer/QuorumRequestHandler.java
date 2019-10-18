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
					if (quorumMutex.getState().equalsIgnoreCase(Constants.UNLOCKED)) {
						Utils.log("State is UNLOCKED, Sending a GRANT");
						Timestamp grantTimestamp = Utils.getTimestamp();
						dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
						quorumMutex.setState(Constants.LOCKED);
						Utils.log("Sent GRANT to :--------->" + Utils.getClientFromHost(clientHost) + " at "
								+ grantTimestamp);
						Utils.log("My State:--------->" + quorumMutex.getState().toUpperCase());
					} else {
						Utils.log("State is LOCKED,Adding to queue");
						quorumMutex.getQueuedRequest().add(new QuorumQueuedRequest(Utils.getClientIDFromHost(clientHost),
								Timestamp.valueOf(requestTimestamp)));
					}
				} else if (Constants.RELEASE.equalsIgnoreCase(operation)) {
					Utils.log("Received RELEASE from :--------->" + Utils.getClientFromHost(clientHost) + " at "
							+ requestTimestamp);
					quorumMutex.setState(Constants.UNLOCKED);
					if (quorumMutex.getQueuedRequest() != null && !quorumMutex.getQueuedRequest().isEmpty()) {
						QuorumQueuedRequest chosenRequest = quorumMutex.chooseFromDeferredQueue();
						DataOutputStream dos = quorumMutex.getClientDosMap().get(chosenRequest.getProcessNum());
						Timestamp grantTimestamp = Utils.getTimestamp();
						dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
						quorumMutex.setState(Constants.LOCKED);
						quorumMutex.deleteFromQuorumQueuedRequest();
						Utils.log("Picked a request from Queue");
						Utils.log("Sent GRANT to :--------->" + Utils.getClientFromID(chosenRequest.getProcessNum())
								+ " at " + grantTimestamp);
						Utils.log("My State:--------->" + quorumMutex.getState().toUpperCase());
					} else {
						Utils.log("Request queue is empty. Stay UNLOCKED");
						Utils.log("My State:--------->" + quorumMutex.getState().toUpperCase());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}