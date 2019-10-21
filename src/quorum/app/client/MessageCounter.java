package quorum.app.client;

public class MessageCounter {
	private int messagesSentFileServer;
	private int messagesSentQuorumServer;
	private int messagesReceivedFileServer;
	private int messagesReceivedQuorumServer;

	public MessageCounter() {
		super();
		this.messagesReceivedFileServer = 0;
		this.messagesReceivedQuorumServer = 0;
		this.messagesSentFileServer = 0;
		this.messagesSentQuorumServer = 0;
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

}
