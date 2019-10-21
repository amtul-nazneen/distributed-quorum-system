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
	ClientRequestCounter clientReqCounter;

	/**
	 * Constructor for creating a handler for communication between the server and
	 * the client. Each connected client is run as a separate thread
	 * 
	 * @param socket
	 * @param serverName
	 * @param clientId
	 */
	public ServerRequestHandler(Socket socket, String clientName, DataInputStream dis, DataOutputStream dos,
			ClientRequestCounter clientReqCounter) {
		super();
		this.socket = socket;
		this.clientName = clientName;
		this.dis = dis;
		this.dos = dos;
		this.clientReqCounter = clientReqCounter;
	}

	/**
	 * Thread run method that keeps checking for incoming messages from the
	 * connected client and sends reply once the task of read/write/enquire is done
	 */
	@Override
	public void run() {
		String message;
		try {
			boolean allClientRequestsCompleted = false;
			while (true) {
				message = dis.readUTF();
				String tokens[] = message.split(",");
				String operation = tokens[0];
				String clientID = tokens[1];
				String timestamp = tokens[2];
				int clientIDInt = Integer.valueOf(clientID);
				if (Constants.WRITE.equalsIgnoreCase(operation)) {
					clientReqCounter.updateClientReqMap(clientIDInt);
					clientReqCounter.updateMessagesReceivedFromClient();

					Utils.log("Received WRITE from:------>" + Utils.getClientFromID(clientIDInt) + "[--=== "
							+ clientReqCounter.getClientReqMap().get(clientIDInt) + " ===--]");
					String accessFile = Constants.FOLDER_PATH + Constants.SERVER_0 + "/" + Constants.FILE0_NAME;
					File f = new File(accessFile);
					FileWriter fw = new FileWriter(f, true);
					BufferedWriter filewriter = new BufferedWriter(fw);
					filewriter.write(Constants.SERVER_WRITE_MESSAGE + Utils.getClientFromID(clientIDInt) + " at "
							+ timestamp + Constants.EOL);
					filewriter.close();
					fw.close();

					Utils.log("Sending SUCCESS to:------>" + Utils.getClientFromID(clientIDInt));
					dos.writeUTF(Constants.SERVER_SUCCESS);
					clientReqCounter.updateMessagesSentToClient();
				} else if (Constants.COMPLETE.equalsIgnoreCase(operation)) {
					clientReqCounter.updateClientReqComplete(clientIDInt);
					clientReqCounter.updateMessagesReceivedFromClient();
					Utils.log("Received COMPLETE from:------>" + Utils.getClientFromID(clientIDInt));
					Utils.log("Sending COMPLETE-ACK to:------>" + Utils.getClientFromID(clientIDInt));
					dos.writeUTF(Constants.COMPLETE_ACK);
					clientReqCounter.updateMessagesSentToClient();
				}

				allClientRequestsCompleted = clientReqCounter.allReqsCompleted();
				if (allClientRequestsCompleted) {
					Utils.log(" *** ----------- All Requests Completed, Terminating ----------- *** ");
					Utils.log("Total messages: " + clientReqCounter.getTotalMessages());
					Utils.log("Total messages sent client: " + clientReqCounter.getMessagesSentClient());
					Utils.log("Total messages received client: " + clientReqCounter.getMessagesReceivedClient());
				} else {
					Utils.log(" *** ----------- Total Requests Not Completed, Continuing ----------- *** ");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
