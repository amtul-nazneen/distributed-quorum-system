package test;

import quorum.app.server.ClientRequestCounter;

public class Hello {
	public static void main(String[] args) {
		System.out.println(allReqsCompleted());
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
