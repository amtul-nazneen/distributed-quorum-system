package quorum.app.server;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class ServerRequestHandler implements Runnable {
	Socket socket;
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
				String tokens[] = message.split(",");
				String clientID = tokens[0];
				String timestamp = tokens[1];
				Utils.log("Received WRITE from:------>" + clientName);
				String accessFile = Constants.FOLDER_PATH + Constants.SERVER_0 + "/" + Constants.FILE0_NAME;
				File f = new File(accessFile);
				FileWriter fw = new FileWriter(f, true);
				BufferedWriter filewriter = new BufferedWriter(fw);
				filewriter.write(Constants.SERVER_WRITE_MESSAGE + Utils.getClientFromID(Integer.valueOf(clientID))
						+ " at " + timestamp + Constants.EOL);
				filewriter.close();
				fw.close();
				Utils.log("Sending SUCCESS to:------>" + clientName);
				dos.writeUTF(Constants.SERVER_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
