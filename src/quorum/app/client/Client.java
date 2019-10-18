package quorum.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Timestamp;

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
		csRequestCount = 1;
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
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM1_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 2
			 * ---------------------------------------------------
			 */
			Socket socketQuorum2 = new Socket(Constants.QUORUM2_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum2 = new DataOutputStream(socketQuorum2.getOutputStream());
			ClientRequestHandler reqHandlerQuorum2 = new ClientRequestHandler(socketQuorum2, clientMutex);
			Thread threadQuroum2 = new Thread(reqHandlerQuorum2);
			threadQuroum2.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM2_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 3
			 * ---------------------------------------------------
			 */
			Socket socketQuorum3 = new Socket(Constants.QUORUM3_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum3 = new DataOutputStream(socketQuorum3.getOutputStream());
			ClientRequestHandler reqHandlerQuorum3 = new ClientRequestHandler(socketQuorum3, clientMutex);
			Thread threadQuroum3 = new Thread(reqHandlerQuorum3);
			threadQuroum3.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM3_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 4
			 * ---------------------------------------------------
			 */
			Socket socketQuorum4 = new Socket(Constants.QUORUM4_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum4 = new DataOutputStream(socketQuorum4.getOutputStream());
			ClientRequestHandler reqHandlerQuorum4 = new ClientRequestHandler(socketQuorum4, clientMutex);
			Thread threadQuroum4 = new Thread(reqHandlerQuorum4);
			threadQuroum4.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM4_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 5
			 * ---------------------------------------------------
			 */
			Socket socketQuorum5 = new Socket(Constants.QUORUM5_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum5 = new DataOutputStream(socketQuorum5.getOutputStream());
			ClientRequestHandler reqHandlerQuorum5 = new ClientRequestHandler(socketQuorum5, clientMutex);
			Thread threadQuroum5 = new Thread(reqHandlerQuorum5);
			threadQuroum5.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM5_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 6
			 * ---------------------------------------------------
			 */
			Socket socketQuorum6 = new Socket(Constants.QUORUM6_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum6 = new DataOutputStream(socketQuorum6.getOutputStream());
			ClientRequestHandler reqHandlerQuorum6 = new ClientRequestHandler(socketQuorum6, clientMutex);
			Thread threadQuroum6 = new Thread(reqHandlerQuorum6);
			threadQuroum6.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM6_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 7
			 * ---------------------------------------------------
			 */
			Socket socketQuorum7 = new Socket(Constants.QUORUM7_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum7 = new DataOutputStream(socketQuorum7.getOutputStream());
			ClientRequestHandler reqHandlerQuorum7 = new ClientRequestHandler(socketQuorum7, clientMutex);
			Thread threadQuroum7 = new Thread(reqHandlerQuorum7);
			threadQuroum7.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM7_HOST));
			/*
			 * ------------------------ CONNECTION TO QUORUM SERVER 8
			 * ---------------------------------------------------
			 */
			Socket socketFileServer = new Socket(Constants.FILESERVER_HOST, Constants.SERVER_PORT);
			DataOutputStream dosFileServer = new DataOutputStream(socketFileServer.getOutputStream());
			DataInputStream disFileServer = new DataInputStream(socketFileServer.getInputStream());
			Utils.log("Connected to: " + Utils.getFileServerFromHost());

			while (true) {
				Thread.sleep((long) (Math.random() * 10000));
				Utils.log("CS Access Requesting------>" + csRequestCount);
				// TODO get the quorum
				// TODO set remreply value in quorum
				int quorumSize = 7;
				Timestamp myRequestTime = Utils.getTimestamp();
				clientMutex.mapQuorumDOS(dosQuorum1, dosQuorum2, dosQuorum3, dosQuorum4, dosQuorum5, dosQuorum6,
						dosQuorum7);

				clientMutex.myCSRequestBegin(myRequestTime, quorumSize);

				executeCS(socketFileServer, dosFileServer, disFileServer);

				Thread.sleep(getClientID() * 3000);
				clientMutex.sendRelease();
				csRequestCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeCS(Socket socketFileServer, DataOutputStream dosFileServer, DataInputStream disFileServer)
			throws Exception {
		Utils.log("===================== Starting  CS_Access: [[[[[[[[[ ---- " + csRequestCount
				+ " ---- ]]]]]]]]] =====================");
		dosFileServer.writeUTF(clientID + "," + Utils.getTimestampForLog());
		String reply = "";
		boolean gotReply = false;
		Utils.log("Wrote to file server, waiting for reply");
		while (!gotReply) {
			reply = disFileServer.readUTF();
			if (reply != null) {
				Utils.log("got reply from server:-->" + "{ " + reply.toUpperCase() + " } ");
				gotReply = true;
			}
		}
		Utils.log("===================== Completed  CS_Access: [[[[[[[[[ ---- " + csRequestCount
				+ " ---- ]]]]]]]]] =====================");
	}
}
