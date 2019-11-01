package test;

import java.util.Scanner;

import quorum.app.server.ClientRequestCounter;
import quorum.app.util.Constants;

public class SampleTest {
	public static void main(String[] args) {
		int num;
		int total = 0;
		int count = 0;
		double avg = 0.0;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Feed me with numbers!");

		while ((num = scanner.nextInt()) > 0) {
			total += num;
			count++;
			System.out.println("Total so far:" + total);
			System.out.println("Count so far:" + count);
		}

		{
			System.out.println("Total so far:" + total);
			System.out.println("Count so far:" + count);
			avg = (double) total / (double) count;
			System.out.println("Avg is:" + (avg));
			// System.out.println("Number is negative! System Shutdown!");
			System.exit(1);
		}

		/*
		 * Random rand = new Random(); int jk = 10; while (jk-- > 0) { int i =
		 * rand.nextInt(3); System.out.println(i); } List<Integer> j = new
		 * ArrayList<Integer>(); j.add(20); j.add(50); j.add(20); j.add(120); j.add(5);
		 */
		// Utils.log("Max:" + Utils.getMax(j));
		// Utils.log("Min:" + Utils.getMin(j));
		// Utils.log("Average:" + Utils.getAverage(j));

		// System.out.println(allReqsCompleted());
		for (int i = 0; i <= 15; i++) {
			// System.out.println(ThreadLocalRandom.current().nextInt(1, 4) * 1000);
			// System.out.println(ThreadLocalRandom.current().nextInt(2, 6));
		}

		String accessFile = Constants.HOME + Constants.CLIENT_LOG_FOLDER + Constants.CLIENT_LOG_FILE + 1
				+ Constants.FILE_EXT;
		String accessFile2 = Constants.HOME + Constants.QUORUM_LOG_FOLDER + Constants.QUORUM_LOG_FILE + 1
				+ Constants.FILE_EXT;
		String accessFile3 = Constants.HOME + Constants.SERVER_LOG_FOLDER + Constants.SERVER_LOG_FILE
				+ Constants.FILE_EXT;
		// System.out.println(accessFile);
		// System.out.println(accessFile2);
		// System.out.println(accessFile3);
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
