package quorum.app.server;

import java.util.HashMap;

import quorum.app.util.Constants;

public class ClientRequestCounter {
	private HashMap<Integer, Integer> clientReqMap;

	public ClientRequestCounter() {
		super();
		clientReqMap = new HashMap<Integer, Integer>();
		initialiseClientReqMap();
	}

	public HashMap<Integer, Integer> getClientReqMap() {
		return clientReqMap;
	}

	private void initialiseClientReqMap() {
		clientReqMap.put(1, 0);
		clientReqMap.put(2, 0);
		clientReqMap.put(3, 0);
		clientReqMap.put(4, 0);
		clientReqMap.put(5, 0);
	}

	public void updateClientReqMap(Integer clientID) {
		int currentCount = clientReqMap.get(clientID);
		clientReqMap.put(clientID, currentCount + 1);
	}

	public boolean allReqsCompleted() {
		boolean completed = false;
		for (Integer count : clientReqMap.values()) {
			if (count == Constants.TOTAL_REQUESTS)
				completed = true;
			else {
				completed = false;
				break;
			}
		}
		return completed;
	}
}
