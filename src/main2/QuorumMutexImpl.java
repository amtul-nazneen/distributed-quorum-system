package main2;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import quorum.app.util.Utils;

public class QuorumMutexImpl {
	private String state;
	// ArrayList<String> queue;
	ArrayList<QuorumQueuedRequest> queuedRequest;
	HashMap<Integer, DataOutputStream> clientDosMap;

	public QuorumMutexImpl() {
		super();
		this.state = "UNLOCKED";
		this.queuedRequest = new ArrayList<QuorumQueuedRequest>();
		this.clientDosMap = new HashMap<Integer, DataOutputStream>();
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
		Collections.sort(queuedRequest, QuorumQueuedRequest.DREP_COMP);
		QuorumQueuedRequest chosenRequest = queuedRequest.get(0);
		// DataOutputStream dos = clientDosMap.get(chosenRequest.getProcessNum());
		Utils.log("Next request served is:--->" + chosenRequest.getProcessNum() + "[][] made at "
				+ chosenRequest.getTimestamp());
		return chosenRequest;
	}

	public void deleteFromQuorumQueuedRequest() {
		this.queuedRequest.remove(0);
		Utils.log("Removed the served request, QuorumQueuedRequest size:-- " + this.queuedRequest.size());

	}

	public void updateClientDosMap(DataOutputStream dos, int clientId) {
		clientDosMap.put(clientId, dos);
	}

}
