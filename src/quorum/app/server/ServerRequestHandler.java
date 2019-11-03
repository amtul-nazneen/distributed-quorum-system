package quorum.app.server;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Thread class that handles the file write requests from each connected client
 */
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
	 * connected client and sends reply once the task of write is done It also sends
	 * a complete message when all the requests from a client have been satisfied
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
					Utils.log("Received WRITE     from:------>" + Utils.getClientFromID(clientIDInt) + "[--===== "
							+ clientReqCounter.getClientReqMap().get(clientIDInt) + " =====--]");
					String accessFile = Constants.HOME + Constants.SERVER_0 + "/" + Constants.FILE0_NAME;
					File f = new File(accessFile);
					FileWriter fw = new FileWriter(f, true);
					BufferedWriter filewriter = new BufferedWriter(fw);
					filewriter.write(Constants.SERVER_WRITE_MESSAGE + Utils.getClientFromID(clientIDInt) + " at "
							+ timestamp + Constants.EOL);
					filewriter.close();
					fw.close();

					Utils.log("Sending  SUCCESS   to:-------->" + Utils.getClientFromID(clientIDInt));
					dos.writeUTF(Constants.SERVER_SUCCESS);
					clientReqCounter.updateMessagesSentToClient();
				} else if (Constants.COMPLETE.equalsIgnoreCase(operation)) {
					clientReqCounter.updateClientReqComplete(clientIDInt);
					clientReqCounter.updateMessagesReceivedFromClient();
					Utils.log("Received COMPLETE  from:------>" + Utils.getClientFromID(clientIDInt));
					Utils.log("Sending COMPLETE-ACK to:------>" + Utils.getClientFromID(clientIDInt));
					dos.writeUTF(Constants.COMPLETE_ACK);
					clientReqCounter.updateMessagesSentToClient();
				}
				allClientRequestsCompleted = clientReqCounter.allReqsCompleted();
				if (allClientRequestsCompleted) {
					Utils.log(" *** ----------- All Requests Completed, Terminating ----------- *** ");
					Utils.log("================= DATA COLLECTION FOR FILE SERVER ==================");
					Utils.log("Total Messages: " + clientReqCounter.getTotalMessages());
					Utils.log("Total Messages Sent to all Clients: " + clientReqCounter.getMessagesSentClient());
					Utils.log("Total Messages Received from all Clients: "
							+ clientReqCounter.getMessagesReceivedClient());
					logDataCollectionToFile();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Log Data Collection to the file FileServer.txt
	 * 
	 * @throws Exception
	 */
	private void logDataCollectionToFile() throws Exception {
		String accessFile = Constants.HOME + Constants.SERVER_LOG_FOLDER + Constants.SERVER_LOG_FILE
				+ Constants.FILE_EXT;
		File f = new File(accessFile);
		FileWriter fw = new FileWriter(f, true);
		BufferedWriter filewriter = new BufferedWriter(fw);
		filewriter.write("================= BEGIN DATA COLLECTION FOR FILE SERVER ==================" + Constants.EOL);
		filewriter.write("Total Messages: " + clientReqCounter.getTotalMessages() + Constants.EOL);
		filewriter.write(
				"Total Messages Sent to all Clients: " + clientReqCounter.getMessagesSentClient() + Constants.EOL);
		filewriter.write("Total Messages Received from all Clients: " + clientReqCounter.getMessagesReceivedClient()
				+ Constants.EOL);
		filewriter.write("================= END DATA COLLECTION FOR FILE SERVER ==================" + Constants.EOL);
		filewriter.close();
		fw.close();
	}
}
