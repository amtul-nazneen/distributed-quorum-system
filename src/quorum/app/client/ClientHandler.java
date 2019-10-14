package quorum.app.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;

import quorum.app.impl.MutualExclusionImpl;
import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Thread Class that handles incoming and outgoing request from the responsible
 * client
 */
public class ClientHandler implements Runnable {
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;
	MutualExclusionImpl mutexImpl;

	/**
	 * Constructor for creating a handler for communication between the clients Each
	 * channel to the other clients, is run as a separate thread
	 * 
	 * @param s:         Socket connection between the clients
	 * @param mutexImpl: mutex object of each client
	 */
	public ClientHandler(Socket s, MutualExclusionImpl mutexImpl) {
		super();
		this.socket = s;
		this.mutexImpl = mutexImpl;
		try {
			InputStreamReader iReader = new InputStreamReader(s.getInputStream());
			reader = new BufferedReader(iReader);
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
			String message;
			try {
				while (reader != null && (message = reader.readLine()) != null) {

					String tokens[] = message.split(",");
					String messageType = tokens[0];

					String host = socket.getInetAddress().getHostName().toUpperCase();
					host = Utils.getProcessFromHost(host);

					if (messageType.equals(Constants.REQUEST)) {
						mutexImpl.myReceivedRequest(Timestamp.valueOf(tokens[1]), Integer.parseInt(tokens[2]),
								tokens[3]);
					} else if (messageType.equals(Constants.REPLY)) {
						Utils.log("-->Received REPLY" + " from " + host);
						mutexImpl.myReceivedReply();
					}
				}

			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

}
