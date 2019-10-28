package quorum.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Thread Class that handles incoming request from the quorum
 */
public class ClientRequestHandler implements Runnable {
	DataInputStream dis;
	DataOutputStream dos;
	Socket socket;
	ClientMutexImpl clientMutexImpl;

	/**
	 * Constructor for creating a handler for communication between the clients and
	 * other quorums ,is run as a separate thread
	 * 
	 * @param s
	 * @param clientMutexImpl
	 */
	public ClientRequestHandler(Socket s, ClientMutexImpl clientMutexImpl) {
		super();
		this.socket = s;
		this.clientMutexImpl = clientMutexImpl;
		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Thread run method that keeps checking for grant replies from other connected
	 * quorums
	 */
	@Override
	public void run() {
		{
			try {
				while (true) {
					String received = dis.readUTF();
					if (received != null) {
						String tokens[] = received.split(",");
						String operation = tokens[0];
						String grantTimestamp = tokens[1];
						if (Constants.GRANT.equalsIgnoreCase(operation)) {
							Utils.log("Received GRANT from :------>"
									+ Utils.getQuorumServerFromHost(socket.getInetAddress().getHostName()) + " at "
									+ "[" + grantTimestamp + "]");
							clientMutexImpl.updatePendingQuorumReply(
									Utils.getQuorumIDFromHost(socket.getInetAddress().getHostName()));
							clientMutexImpl.updateMessagesReceived(Constants.QUORUM_SERVER);
							clientMutexImpl.updateCSMessages();
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
