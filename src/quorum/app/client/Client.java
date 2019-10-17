package quorum.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class Client {
	static int clientID;
	static int csRequestCount = 1;

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			Utils.log("No Client ID provided.");
			return;
		}
		String id = args[0];
		if ("1".equals(id)) {
			Utils.logWithSeparator("Starting Client ID:1");
		}
		if ("2".equals(id)) {
			Utils.logWithSeparator("Starting Client ID:2");
		}
		clientID = Integer.valueOf(id);
		try {
			ClientMutexImpl clientMutex = new ClientMutexImpl(Integer.valueOf(id));

			Socket socketQuorum1 = new Socket(Constants.QUORUM1_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum1 = new DataOutputStream(socketQuorum1.getOutputStream());
			ClientRequestHandler reqHandlerQuorum1 = new ClientRequestHandler(socketQuorum1, clientMutex);
			Thread threadQuroum1 = new Thread(reqHandlerQuorum1);
			threadQuroum1.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM1_HOST));

			Socket socketQuorum2 = new Socket(Constants.QUORUM2_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum2 = new DataOutputStream(socketQuorum2.getOutputStream());
			ClientRequestHandler reqHandlerQuorum2 = new ClientRequestHandler(socketQuorum2, clientMutex);
			Thread threadQuroum2 = new Thread(reqHandlerQuorum2);
			threadQuroum2.start();
			Utils.log("Connected to: " + Utils.getQuorumServerFromHost(Constants.QUORUM2_HOST));

			Socket socketFileServer = new Socket(Constants.FILESERVER_HOST, Constants.SERVER_PORT);
			DataOutputStream dosFileServer = new DataOutputStream(socketFileServer.getOutputStream());
			DataInputStream disFileServer = new DataInputStream(socketFileServer.getInputStream());
			Utils.log("Connected to: " + Utils.getFileServerFromHost());

			while (true) {
				Thread.sleep((long) (Math.random() * 10000));
				Utils.log("CS Access Requesting------>" + csRequestCount++);
				// TODO get the quorum
				// TODO set remreply value in quorum
				int quorumSize = 2;
				Timestamp myRequestTime = Utils.getTimestamp();
				clientMutex.mapQuorumDOS(dosQuorum1, dosQuorum2);

				clientMutex.myCSRequestBegin(myRequestTime, quorumSize);

				executeCS(socketFileServer, dosFileServer, disFileServer);

				Thread.sleep(Integer.valueOf(id) * 3000);
				clientMutex.sendRelease();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeCS(Socket socketFileServer, DataOutputStream dosFileServer,
			DataInputStream disFileServer) throws Exception {
		Utils.log("===================== Starting  CS_Access: [[[[[[[[[ ---- " + csRequestCount
				+ " ---- ]]]]]]]]] =====================");
		dosFileServer.writeUTF(clientID + "," + Utils.getTimestampForLog());
		String reply = "";
		boolean gotReply = false;
		Utils.log("Wrote to file server, waiting for reply");
		while (!gotReply) {
			reply = disFileServer.readUTF();
			if (reply != null) {
				Utils.log("got reply from server:-->" + "{ " + reply + " } ");
				// TODO Utils.storeToOutputFile(reply, processnum, Constants.READ, FILE);
				gotReply = true;
			}
		}
		Utils.log("===================== Completed  CS_Access: [[[[[[[[[ ---- " + csRequestCount
				+ " ---- ]]]]]]]]] =====================");
	}
}
