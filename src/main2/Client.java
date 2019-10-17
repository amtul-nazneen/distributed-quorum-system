package main2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class Client {
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
		try {
			ClientMutexImpl clientMutex = new ClientMutexImpl(Integer.valueOf(id));
			int csRequestCount = 1;
			Socket socketQuorum1 = new Socket(Constants.QUORUM1_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum1 = new DataOutputStream(socketQuorum1.getOutputStream());
			ClientRequestHandler reqHandlerQuorum1 = new ClientRequestHandler(socketQuorum1, clientMutex);
			Thread threadQuroum1 = new Thread(reqHandlerQuorum1);
			threadQuroum1.start();

			Socket socketQuorum2 = new Socket(Constants.QUORUM2_HOST, Constants.SERVER_PORT);
			DataOutputStream dosQuorum2 = new DataOutputStream(socketQuorum2.getOutputStream());
			ClientRequestHandler reqHandlerQuorum2 = new ClientRequestHandler(socketQuorum2, clientMutex);// CH t2 = new
																											// CH(s2,
																											// ci1);
			Thread threadQuroum2 = new Thread(reqHandlerQuorum2);
			threadQuroum2.start();

			while (true) {
				Thread.sleep((long) (Math.random() * 10000));
				Utils.log("CS Access Requesting------>" + csRequestCount++);
				// TODO get the quorum
				// TODO set remreply value in quorum
				int quorumSize = 2;
				Timestamp myRequestTime = Utils.getTimestamp();
				clientMutex.mapQuorumDOS(dosQuorum1, dosQuorum2);

				clientMutex.myCSRequestBegin(myRequestTime, quorumSize);

				Utils.log("got all replies, entering CS now");
				Thread.sleep(Integer.valueOf(id) * 3000);
				Utils.log("finished CS");

				clientMutex.sendRelease();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
