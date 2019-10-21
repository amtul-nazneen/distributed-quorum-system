package quorum.app.quorumServer;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class QuorumMutexImpl {
	private String state;
	ArrayList<QuorumQueuedRequest> queuedRequest;
	HashMap<Integer, DataOutputStream> clientDosMap;
	private int messagesReceivedClient;
	private int messagesSentClient;

	public QuorumMutexImpl() {
		super();
		this.state = Constants.UNLOCKED;
		messagesReceivedClient = 0;
		messagesSentClient = 0;
		this.queuedRequest = new ArrayList<QuorumQueuedRequest>();
		this.clientDosMap = new HashMap<Integer, DataOutputStream>();
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<QuorumQueuedRequest> getQueuedRequest() {
		return queuedRequest;
	}

	public void setQueuedRequest(ArrayList<QuorumQueuedRequest> queuedRequest) {
		this.queuedRequest = queuedRequest;
	}

	public HashMap<Integer, DataOutputStream> getClientDosMap() {
		return clientDosMap;
	}

	public void setClientDosMap(HashMap<Integer, DataOutputStream> clientDosMap) {
		this.clientDosMap = clientDosMap;
	}

	public QuorumQueuedRequest chooseFromDeferredQueue() {
		Collections.sort(queuedRequest, QuorumQueuedRequest.QUORUM_REQ_COMP);
		QuorumQueuedRequest chosenRequest = queuedRequest.get(0);
		Utils.log("Next request served is:----->" + chosenRequest.getProcessNum() + "," + chosenRequest.getTimestamp());
		return chosenRequest;
	}

	public void deleteFromQuorumQueuedRequest() {
		this.queuedRequest.remove(0);
		Utils.log("Removed the served request, QuorumQueue size:-----> " + this.queuedRequest.size());

	}

	public void updateClientDosMap(DataOutputStream dos, int clientId) {
		clientDosMap.put(clientId, dos);
	}

}
