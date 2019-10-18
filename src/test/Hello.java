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
		ci.getClientReqMap().put(1, 20);
		ci.getClientReqMap().put(2, 20);
		ci.getClientReqMap().put(3, 20);
		ci.getClientReqMap().put(4, 20);
		ci.getClientReqMap().put(5, 0);
		for (Integer count : ci.getClientReqMap().values()) {
			System.out.println("count:" + count);
			if (count == 20)
				completed = true;
			else {
				completed = false;
				break;
			}
		}
		return completed;
	}
}
