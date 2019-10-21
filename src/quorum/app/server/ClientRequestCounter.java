package quorum.app.server;

import java.util.HashMap;

import quorum.app.util.Constants;

public class ClientRequestCounter {
	private HashMap<Integer, Integer> clientReqMap;
	private HashMap<Integer, Boolean> clientReqCompleted;
	private int messagesReceivedClient;
	private int messagesSentClient;

	public ClientRequestCounter() {
		super();
		messagesReceivedClient = 0;
		messagesSentClient = 0;
		clientReqMap = new HashMap<Integer, Integer>();
		clientReqCompleted = new HashMap<Integer, Boolean>();
		initialiseClientReqMap();
		initialiseClientReqComplete();
	}

	public void updateMessagesReceivedFromClient() {
		messagesReceivedClient = messagesReceivedClient + 1;
	}

	public void updateMessagesSentToClient() {
		messagesSentClient = messagesSentClient + 1;
	}

	public int getTotalMessages() {
		return messagesReceivedClient + messagesSentClient;
	}

	public int getMessagesReceivedClient() {
		return messagesReceivedClient;
	}

	public int getMessagesSentClient() {
		return messagesSentClient;
	}

	public HashMap<Integer, Integer> getClientReqMap() {
		return clientReqMap;
	}

	public HashMap<Integer, Boolean> getClientReqCompleted() {
		return clientReqCompleted;
	}

	private void initialiseClientReqMap() {
		for (int i = 1; i <= Constants.TOTAL_CLIENTS; i++)
			clientReqMap.put(i, 0);
	}

	private void initialiseClientReqComplete() {
		for (int i = 1; i <= Constants.TOTAL_CLIENTS; i++)
			clientReqCompleted.put(i, false);
	}

	public void updateClientReqMap(Integer clientID) {
		int currentCount = clientReqMap.get(clientID);
		clientReqMap.put(clientID, currentCount + 1);
	}

	public void updateClientReqComplete(Integer clientID) {
		clientReqCompleted.put(clientID, true);
	}

	public boolean allReqsCompleted() {
		boolean completed = false;
		for (Boolean value : clientReqCompleted.values()) {
			if (value == true)
				completed = true;
			else {
				completed = false;
				break;
			}
		}
		return completed;
	}

}
