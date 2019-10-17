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
	QuorumMutexImpl qi;

	public QuorumRequestHandler(Socket s, DataInputStream dis, DataOutputStream dos, QuorumMutexImpl qi) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.qi = qi;
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
					if (qi.getState().equalsIgnoreCase(Constants.UNLOCKED)) {
						Utils.log("state is unlocked, sending a reply");
						Timestamp grantTimestamp = Utils.getTimestamp();
						dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
						qi.setState(Constants.LOCKED);
						Utils.log("Sent GRANT to :--------->" + Utils.getClientFromHost(clientHost) + " at "
								+ grantTimestamp);
						Utils.log("My state now:--" + qi.getState().toUpperCase());
					} else {
						Utils.log("I'm locked, cannot reply,adding to the queue");
						qi.getQueuedRequest().add(new QuorumQueuedRequest(Utils.getClientIDFromHost(clientHost),
								Timestamp.valueOf(requestTimestamp)));
					}
				} else if (Constants.RELEASE.equalsIgnoreCase(operation)) {
					Utils.log("Received RELEASE from :--------->" + Utils.getClientFromHost(clientHost) + " at "
							+ requestTimestamp);
					qi.setState(Constants.UNLOCKED);
					Utils.log("Choose from request queue another client if not empty");
					if (qi.getQueuedRequest() != null && !qi.getQueuedRequest().isEmpty()) {
						QuorumQueuedRequest chosenRequest = qi.chooseFromDeferredQueue();
						DataOutputStream dos = qi.getClientDosMap().get(chosenRequest.getProcessNum());
						Timestamp grantTimestamp = Utils.getTimestamp();
						dos.writeUTF(Constants.GRANT + "," + grantTimestamp);
						qi.setState(Constants.LOCKED);
						qi.deleteFromQuorumQueuedRequest();
						Utils.log("Sent GRANT to :--------->" + Utils.getClientFromID(chosenRequest.getProcessNum())
								+ " at " + grantTimestamp);
						Utils.log("My state now:--" + qi.getState().toUpperCase());
					} else {
						Utils.log("Request queue is empty, nothing to do, stay unlocked");
						Utils.log("My state now:--" + qi.getState().toUpperCase());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}