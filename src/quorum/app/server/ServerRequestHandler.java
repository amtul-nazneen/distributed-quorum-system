package quorum.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import quorum.app.util.Utils;

public class ServerRequestHandler implements Runnable {
	Socket socket;
	// BufferedReader reader;
	// PrintWriter writer;
	final DataInputStream dis;
	final DataOutputStream dos;
	String clientName;

	/**
	 * Constructor for creating a handler for communication between the server and
	 * the client. Each connected client is run as a separate thread
	 * 
	 * @param socket
	 * @param serverName
	 * @param clientId
	 */
	public ServerRequestHandler(Socket socket, String clientName, DataInputStream dis, DataOutputStream dos) {
		super();
		this.socket = socket;
		this.clientName = clientName;
		this.dis = dis;
		this.dos = dos;
	}

	/**
	 * Thread run method that keeps checking for incoming messages from the
	 * connected client and sends reply once the task of read/write/enquire is done
	 */
	@Override
	public void run() {
		String message;
		try {
			while (true) {
				message = dis.readUTF();
				Utils.log("received a write from client:------>" + clientName);
				Utils.log("message is: " + message);
				dos.writeUTF("from server, done the write");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
