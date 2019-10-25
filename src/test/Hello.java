package test;

import quorum.app.server.ClientRequestCounter;
import quorum.app.util.Constants;

public class Hello {
	public static void main(String[] args) {
		// System.out.println(allReqsCompleted());
		String accessFile = Constants.HOME + Constants.CLIENT_LOG_FOLDER + Constants.CLIENT_LOG_FILE + 1
				+ Constants.FILE_EXT;
		String accessFile2 = Constants.HOME + Constants.QUORUM_LOG_FOLDER + Constants.QUORUM_LOG_FILE + 1
				+ Constants.FILE_EXT;
		String accessFile3 = Constants.HOME + Constants.SERVER_LOG_FOLDER + Constants.SERVER_LOG_FILE
				+ Constants.FILE_EXT;
		System.out.println(accessFile);
		System.out.println(accessFile2);
		System.out.println(accessFile3);
	}

	public static boolean allReqsCompleted() {
		boolean completed = false;
//TODO
		ClientRequestCounter ci = new ClientRequestCounter();
		ci.getClientReqCompleted().put(1, false);
		ci.getClientReqCompleted().put(2, false);
		ci.getClientReqCompleted().put(3, false);
		ci.getClientReqCompleted().put(4, false);
		ci.getClientReqCompleted().put(5, false);
		// boolean completed = false;
		for (Boolean value : ci.getClientReqCompleted().values()) {
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
