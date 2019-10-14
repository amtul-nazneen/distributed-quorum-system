package test;

import java.io.File;
import java.sql.Timestamp;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class TestFiles {

	public static void main(String[] args) throws Exception {
		if (Constants.ENABLE_SOCKET_CLOSE) {
			Timestamp start = Utils.getTimestamp();
			Thread.sleep(3000);
			while (Utils.checkTimeout(start, Utils.getTimestamp()) <= 1) {
				Utils.log("sleeping");
				Thread.sleep(20000);
			}
			Utils.log("closing");
		}
		int counter = 0;
		System.out.println(" *********>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (counter + 1) + " Randomly Chosen, ");
		System.out.println(" *********>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "------->" + (counter + 1)
				+ " Randomly Chosen, ");
		int count1 = 0, count2 = 0, count3 = 0;
		for (int i = 1; i <= 20; i++) {
			int randomInt = (int) (30.0 * Math.random());
			if (randomInt <= 10 && randomInt >= 0)
				count1++;
			if (randomInt <= 20 && randomInt >= 11)
				count2++;
			if (randomInt <= 30 && randomInt >= 21)
				count3++;
			// System.out.println(randomInt);
		}
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
		/*
		 * ArrayList<String> files = new ArrayList<String>(); files.add("file2.txt");
		 * files.add("file1.txt"); files.add("file3.txt"); Collections.sort(files); for
		 * (String s : files) System.out.println(s); Utils.logWithSeparator("blah");
		 */
	}

	/*
	 * private void writeToServer() throws Exception {
	 * writeToServer1.println(Constants.WRITE + "," + FILE + "," +
	 * Constants.WRITE_MESSAGE + processnum + " at " +
	 * myMutexImpl.getMyRequestTimestamp()); String reply; boolean gotReply = false;
	 * while (!gotReply) { reply = readFromServer1.readLine(); if (reply != null) {
	 * gotReply = true; } } }
	 */

	public void readFile() {
		String files = "";
		File folder = new File("s1");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (i == 0) {
					files = listOfFiles[i].getName();
				} else {
					files = files + "," + listOfFiles[i].getName();
				}
			}
		}
		System.out.println("Output: " + files);
	}
}
