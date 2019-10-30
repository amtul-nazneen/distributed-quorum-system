package quorum.app.client;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amtul.nazneen
 */

/**
 * Class to keep track of the total messages sent and received by each client
 * node from other nodes [quorum and file server]
 */
public class MessageCounter {
	private int messagesSentFileServer;
	private int messagesSentQuorumServer;
	private int messagesReceivedFileServer;
	private int messagesReceivedQuorumServer;
	private int csMessages;
	private List<Integer> latencyList;
	private List<Integer> msgExchangeList;

	public MessageCounter() {
		super();
		this.messagesReceivedFileServer = 0;
		this.messagesReceivedQuorumServer = 0;
		this.messagesSentFileServer = 0;
		this.messagesSentQuorumServer = 0;
		this.csMessages = 0;
		latencyList = new ArrayList<Integer>();
		msgExchangeList = new ArrayList<Integer>();
	}

	public int getMessagesSentFileServer() {
		return messagesSentFileServer;
	}

	public int getMessagesSentQuorumServer() {
		return messagesSentQuorumServer;
	}

	public int getMessagesReceivedFileServer() {
		return messagesReceivedFileServer;
	}

	public int getMessagesReceivedQuorumServer() {
		return messagesReceivedQuorumServer;
	}

	public int getCSMessages() {
		return csMessages;
	}

	public void setCSMessages(int value) {
		this.csMessages = value;
	}

	public void resetCSMessages() {
		this.csMessages = 0;
	}

	public int getTotalMessages() {
		return messagesSentFileServer + messagesSentQuorumServer + messagesReceivedFileServer
				+ messagesReceivedQuorumServer;
	}

	public int getTotalMessagesSent() {
		return messagesSentFileServer + messagesSentQuorumServer;
	}

	public int getTotalMessagesReceived() {
		return messagesReceivedFileServer + messagesReceivedQuorumServer;
	}

	public List<Integer> getLatencyList() {
		return latencyList;
	}

	public List<Integer> getMsgExchangeList() {
		return msgExchangeList;
	}

	public void updateMessagesSentFileServer() {
		messagesSentFileServer = messagesSentFileServer + 1;
	}

	public void updateMessagesSentQuorumServer() {
		messagesSentQuorumServer = messagesSentQuorumServer + 1;
	}

	public void updateMessagesReceivedFileServer() {
		messagesReceivedFileServer = messagesReceivedFileServer + 1;
	}

	public void updateMessagesReceivedQuorumServer() {
		messagesReceivedQuorumServer = messagesReceivedQuorumServer + 1;
	}

	public void updateLatencyList(int latency) {
		this.latencyList.add(latency);
	}

	public void updateMsgExchangeList(int msgsExchanged) {
		this.msgExchangeList.add(msgsExchanged);
	}

}
