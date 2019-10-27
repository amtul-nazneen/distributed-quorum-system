package quorum.app.client;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import quorum.app.quorumServer.tree.RandomQuorumGenerator;
import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class Client {

	private int clientID;
	private int csRequestCount;

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public Client(int clientID) {
		super();
		this.clientID = clientID;
	}

	public void startClient() throws Exception {
		Utils.logWithSeparator("Starting Client ID:" + clientID);
		csRequestCount = 0;
		try {
			ClientMutexImpl clientMutex = new ClientMutexImpl(getClientID());
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 1
			 * ---------------------------------------------------
			 */
			Socket socketQuorum1 = new Socket(Constants.QUORUM1_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum1 = new DataOutputStream(socketQuorum1.getOutputStream());
			ClientRequestHandler reqHandlerQuorum1 = new ClientRequestHandler(socketQuorum1, clientMutex);
			Thread threadQuroum1 = new Thread(reqHandlerQuorum1);
			threadQuroum1.start();
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 2
			 * ---------------------------------------------------
			 */
			Socket socketQuorum2 = new Socket(Constants.QUORUM2_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum2 = new DataOutputStream(socketQuorum2.getOutputStream());
			ClientRequestHandler reqHandlerQuorum2 = new ClientRequestHandler(socketQuorum2, clientMutex);
			Thread threadQuroum2 = new Thread(reqHandlerQuorum2);
			threadQuroum2.start();
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 3
			 * ---------------------------------------------------
			 */

			Socket socketQuorum3 = new Socket(Constants.QUORUM3_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum3 = new DataOutputStream(socketQuorum3.getOutputStream());
			ClientRequestHandler reqHandlerQuorum3 = new ClientRequestHandler(socketQuorum3, clientMutex);
			Thread threadQuroum3 = new Thread(reqHandlerQuorum3);
			threadQuroum3.start();

			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 4
			 * ---------------------------------------------------
			 */

			Socket socketQuorum4 = new Socket(Constants.QUORUM4_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum4 = new DataOutputStream(socketQuorum4.getOutputStream());
			ClientRequestHandler reqHandlerQuorum4 = new ClientRequestHandler(socketQuorum4, clientMutex);
			Thread threadQuroum4 = new Thread(reqHandlerQuorum4);
			threadQuroum4.start();

			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 5
			 * ---------------------------------------------------
			 */

			Socket socketQuorum5 = new Socket(Constants.QUORUM5_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum5 = new DataOutputStream(socketQuorum5.getOutputStream());
			ClientRequestHandler reqHandlerQuorum5 = new ClientRequestHandler(socketQuorum5, clientMutex);
			Thread threadQuroum5 = new Thread(reqHandlerQuorum5);
			threadQuroum5.start();

			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 6
			 * ---------------------------------------------------
			 */

			Socket socketQuorum6 = new Socket(Constants.QUORUM6_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum6 = new DataOutputStream(socketQuorum6.getOutputStream());
			ClientRequestHandler reqHandlerQuorum6 = new ClientRequestHandler(socketQuorum6, clientMutex);
			Thread threadQuroum6 = new Thread(reqHandlerQuorum6);
			threadQuroum6.start();

			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 7
			 * ---------------------------------------------------
			 */

			Socket socketQuorum7 = new Socket(Constants.QUORUM7_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum7 = new DataOutputStream(socketQuorum7.getOutputStream());
			ClientRequestHandler reqHandlerQuorum7 = new ClientRequestHandler(socketQuorum7, clientMutex);
			Thread threadQuroum7 = new Thread(reqHandlerQuorum7);
			threadQuroum7.start();

			/*
			 * ------------------------ CONNECTION TO FILE SERVER
			 * ---------------------------------------------------
			 */
			@SuppressWarnings("resource")
			Socket socketFileServer = new Socket(Constants.FILESERVER_HOST, Constants.SERVER_PORT);
			DataOutputStream dosFileServer = new DataOutputStream(socketFileServer.getOutputStream());
			DataInputStream disFileServer = new DataInputStream(socketFileServer.getInputStream());

			HashMap<Integer, DataOutputStream> allQuorumServers = new HashMap<Integer, DataOutputStream>();
			allQuorumServers.put(1, dosQuorum1);
			allQuorumServers.put(2, dosQuorum2);
			allQuorumServers.put(3, dosQuorum3);
			allQuorumServers.put(4, dosQuorum4);
			allQuorumServers.put(5, dosQuorum5);
			allQuorumServers.put(6, dosQuorum6);
			allQuorumServers.put(7, dosQuorum7);

			boolean requestsCompleted = false;
			while (!requestsCompleted) {
				csRequestCount++;
				/* Randomize - Exiting and re-requesting */
				Thread.sleep((long) (Math.random() * Utils.getWaitTime(clientID)));
				Utils.log(
						"~~|~~|~~|~~|~~|~~|~~ CS Access Requesting: ------------------------------------>->->->->->->->->->"
								+ csRequestCount);

				List<Integer> quorumList = RandomQuorumGenerator.getQuorum(clientID);
				String selectedQuorums = Utils.getSelectedQuorumID(quorumList);
				Utils.log("Sending requests to randomly chosen quorum:----> { " + selectedQuorums + " }");
				HashMap<Integer, DataOutputStream> quorums = new HashMap<Integer, DataOutputStream>();
				for (Integer quorumId : quorumList) {
					quorums.put(quorumId, allQuorumServers.get(quorumId));
				}

				Timestamp myRequestTime = Utils.getTimestamp();
				clientMutex.mapQuorumDOS(quorums);
				clientMutex.getMessageCounter().resetCSMessages();
				clientMutex.myCSRequestBegin(myRequestTime);
				Timestamp myCSExecTime = Utils.getTimestamp();
				executeCS(dosFileServer, disFileServer, clientMutex);

				Thread.sleep((long) Math.random() * 1000);
				clientMutex.sendRelease();
				Utils.log("@_@_@_@_@_@_@_@ --- Critical Section Data Collection for Client:" + clientID);
				Utils.log(". . . . . . . . . . Total CS Messages Exchanged: "
						+ clientMutex.getMessageCounter().getCSMessages());
				Utils.log(". . . . . . . . . . Total Latency: " + Utils.getTimeDifference(myRequestTime, myCSExecTime)
						+ " sec.");
				if (csRequestCount == Constants.TOTAL_REQUESTS) {
					requestsCompleted = true;
					sendCompleteNotifServer(dosFileServer, clientMutex);
					waitForAckFileServer(disFileServer, clientMutex);
					Utils.log("Reached TERMINATION");
					Utils.log(
							"===================== DATA COLLECTION FOR CLIENT:" + clientID + " ===================== ");
					Utils.log("Total Messages: " + clientMutex.getMessageCounter().getTotalMessages());
					Utils.log("Total Messages Sent: " + +clientMutex.getMessageCounter().getTotalMessagesSent());
					Utils.log("Total Messages Received: " + clientMutex.getMessageCounter().getTotalMessagesReceived());
					Utils.log("Total Messages Received from Quorum Servers: "
							+ clientMutex.getMessageCounter().getMessagesReceivedQuorumServer());
					Utils.log("Total Messages Received from File Server: "
							+ clientMutex.getMessageCounter().getMessagesReceivedFileServer());
					Utils.log("Total Messages Sent to Quorum Servers: "
							+ clientMutex.getMessageCounter().getMessagesSentQuorumServer());
					Utils.log("Total Messages Sent to File Server: "
							+ clientMutex.getMessageCounter().getMessagesSentFileServer());
					logDataCollectionToFile(clientMutex);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeCS(DataOutputStream dosFileServer, DataInputStream disFileServer, ClientMutexImpl clientMutex)
			throws Exception {
		Utils.log("===================== Starting   CS_Access: [[[[[[[[[ ---- " + csRequestCount
				+ " ---- ]]]]]]]]] ===================");
		dosFileServer.writeUTF(Constants.WRITE + "," + clientID + "," + Utils.getTimestampForLog());
		clientMutex.updateMessagesSent(Constants.FILE_SERVER);
		clientMutex.updateCSMessages();
		String reply = "";
		boolean gotReply = false;
		Utils.log("Sent Write Request to FileServer");
		while (!gotReply) {
			reply = disFileServer.readUTF();
			if (reply != null) {
				Utils.log("Received Reply from FileServer:-->" + "{ " + reply.toUpperCase() + " } ");
				clientMutex.updateMessagesReceived(Constants.FILE_SERVER);
				clientMutex.updateCSMessages();
				gotReply = true;
			}
		}
		/* Randomising- time spent in critical section */
		Thread.sleep((long) (Math.random() * Utils.getCSTime(clientID)));
		Utils.log("===================== Completed  CS_Access: [[[[[[[[[ ---- " + csRequestCount
				+ " ---- ]]]]]]]]] ===================");
	}

	private void sendCompleteNotifServer(DataOutputStream dosFileServer, ClientMutexImpl clientMutex) throws Exception {
		Utils.log("Sending COMPLETE notification to FileServer");
		dosFileServer.writeUTF(Constants.COMPLETE + "," + clientID + "," + Utils.getTimestampForLog());
		clientMutex.updateMessagesSent(Constants.FILE_SERVER);
	}

	private void waitForAckFileServer(DataInputStream disFileServer, ClientMutexImpl clientMutex) throws Exception {
		boolean gotAck = false;
		String ack = "";
		while (!gotAck) {
			Thread.sleep((long) Math.random() * 5000);
			ack = disFileServer.readUTF();
			if (ack != null && Constants.COMPLETE_ACK.equalsIgnoreCase(ack)) {
				Utils.log("Got Acknowledgement from FileServer:-->" + "{ " + ack.toUpperCase() + " } ");
				clientMutex.updateMessagesReceived(Constants.FILE_SERVER);
				gotAck = true;
			}
		}
	}

	private void logDataCollectionToFile(ClientMutexImpl clientMutex) throws Exception {
		String accessFile = Constants.HOME + Constants.CLIENT_LOG_FOLDER + Constants.CLIENT_LOG_FILE + clientID
				+ Constants.FILE_EXT;
		File f = new File(accessFile);
		FileWriter fw = new FileWriter(f, true);
		BufferedWriter filewriter = new BufferedWriter(fw);
		filewriter.write("===================== DATA COLLECTION FOR CLIENT:" + clientID + " ===================== "
				+ Constants.EOL);
		filewriter.write("Total Messages: " + clientMutex.getMessageCounter().getTotalMessages() + Constants.EOL);
		filewriter.write(
				"Total Messages Sent: " + +clientMutex.getMessageCounter().getTotalMessagesSent() + Constants.EOL);
		filewriter.write("Total Messages Received: " + clientMutex.getMessageCounter().getTotalMessagesReceived()
				+ Constants.EOL);
		filewriter.write("Total Messages Received from Quorum Servers: "
				+ clientMutex.getMessageCounter().getMessagesReceivedQuorumServer() + Constants.EOL);
		filewriter.write("Total Messages Received from File Server: "
				+ clientMutex.getMessageCounter().getMessagesReceivedFileServer() + Constants.EOL);
		filewriter.write("Total Messages Sent to Quorum Servers: "
				+ clientMutex.getMessageCounter().getMessagesSentQuorumServer() + Constants.EOL);
		filewriter.write("Total Messages Sent to File Server: "
				+ clientMutex.getMessageCounter().getMessagesSentFileServer() + Constants.EOL);
		filewriter.close();
		fw.close();
	}
}
