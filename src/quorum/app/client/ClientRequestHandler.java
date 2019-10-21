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
 * Thread Class that handles incoming and outgoing request from the responsible
 * client
 */
public class ClientRequestHandler implements Runnable {
	DataInputStream dis;
	DataOutputStream dos;
	Socket socket;
	ClientMutexImpl clientMutexImpl;

	/**
	 * Constructor for creating a handler for communication between the clients Each
	 * channel to the other clients, is run as a separate thread
	 * 
	 * @param s:         Socket connection between the clients
	 * @param mutexImpl: mutex object of each client
	 */
	public ClientRequestHandler(Socket s, ClientMutexImpl ci) {
		super();
		this.socket = s;
		this.clientMutexImpl = ci;
		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Thread run method that keeps checking for incoming requests from other
	 * connect client and sends reply to the other client
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
									+ grantTimestamp);
							clientMutexImpl.updatePendingQuorumReply();
							clientMutexImpl.updateMessagesReceived(Constants.QUORUM_SERVER);
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
