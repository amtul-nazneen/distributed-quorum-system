package test;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class VerifyMessageCounter {

	public static void main(String[] args) {
		printTotalMessagesClient(2);
		printTotalMessagesFileServer();
	}

	static void printTotalMessagesClient(int quorumSize) {
		Utils.logWithSeparator("Total Messages:CLIENT NODE");
		int csCount = Constants.TOTAL_REQUESTS;
		int write = 1;
		int success = 1;
		int complete = 1;
		int completeAck = 1;

		int totalSentFileServer = csCount * write + complete;
		int totalSentQuorumServer = 2 * csCount * quorumSize;
		int totalReceivedFileServer = csCount * success + completeAck;
		int totalReceivedQuorumServer = csCount * quorumSize;
		int totalSent = totalSentFileServer + totalSentQuorumServer;
		int totalReceived = totalReceivedFileServer + totalReceivedQuorumServer;
		int total = totalSent + totalReceived;

		Utils.log("Total messages: " + total);
		Utils.log("Total messages sent: " + totalSent);
		Utils.log("Total messages received: " + totalReceived);
		Utils.log("Total messages received Quorum: " + totalReceivedQuorumServer);
		Utils.log("Total messages received File: " + totalReceivedFileServer);
		Utils.log("Total messages sent Quorum: " + totalSentQuorumServer);
		Utils.log("Total messages sent File: " + totalSentFileServer);

	}

	static void printTotalMessagesFileServer() {
		Utils.logWithSeparator("Total Messages:SERVER NODE");
		int clients = Constants.TOTAL_CLIENTS;
		int csCount = Constants.TOTAL_REQUESTS;
		int success = 1;
		int write = 1;
		int complete = 1;
		int completeAck = 1;
		int totalSentClient = clients * csCount * success + completeAck * clients;
		int totalReceivedClient = clients * csCount * write + complete * clients;
		int total = totalReceivedClient + totalSentClient;
		Utils.log("Total messages: " + total);
		Utils.log("Total messages sent client: " + totalSentClient);
		Utils.log("Total messages received client: " + totalReceivedClient);
	}
}
