package quorum.app.util;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author amtul.nazneen
 */

/**
 * Utility class
 */
public class Utils {

	public static HashMap<String, String> hosttoprocess = new HashMap<String, String>();
	public static HashMap<String, Integer> hosttoclientid = new HashMap<String, Integer>();
	public static HashMap<String, String> serverToName = new HashMap<String, String>();
	public static HashMap<Integer, String> clientidtoprocess = new HashMap<Integer, String>();

	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static String getTimestampForLog() {
		return "[" + (new Timestamp(new Date().getTime())) + "]";
	}

	public static void log(String message) {
		String time = getTimestampForLog();
		if (time.length() == 25)
			System.out.println(time + " " + message);
		else if (time.length() == 24)
			System.out.println(time + "  " + message);
		else
			System.out.println(time + "   " + message);
	}

	public static void storeToOutputFile(String message, int process, String task, String fileName) {
		String file = Constants.HOME + Constants.OUTPUT_FILE;
		try {
			File f = new File(file);
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter filewriter = new BufferedWriter(fw);
			String data = "Process:" + process + " " + task.toUpperCase() + " " + fileName.toUpperCase() + " {{ "
					+ message + " }} ";
			filewriter.write(data + Constants.EOL);
			filewriter.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getClientFromHost(String host) {
		hosttoprocess.put(Constants.CLIENT1, "Process:1");
		hosttoprocess.put(Constants.CLIENT2, "Process:2");
		hosttoprocess.put(Constants.CLIENT3, "Process:3");
		hosttoprocess.put(Constants.CLIENT4, "Process:4");
		hosttoprocess.put(Constants.CLIENT5, "Process:5");
		return hosttoprocess.get(host.toLowerCase());
	}

	public static String getClientFromID(int id) {
		clientidtoprocess.put(1, "Process:1");
		clientidtoprocess.put(2, "Process:2");
		clientidtoprocess.put(3, "Process:3");
		clientidtoprocess.put(4, "Process:4");
		clientidtoprocess.put(5, "Process:5");
		return clientidtoprocess.get(id);
	}

	public static int getClientIDFromHost(String host) {
		hosttoclientid.put(Constants.CLIENT1, 1);
		hosttoclientid.put(Constants.CLIENT2, 2);
		hosttoclientid.put(Constants.CLIENT3, 3);
		hosttoclientid.put(Constants.CLIENT4, 4);
		hosttoclientid.put(Constants.CLIENT5, 5);
		return hosttoclientid.get(host.toLowerCase());
	}

	public static String getQuorumServerFromHost(String host) {
		serverToName.put(Constants.QUORUM1_HOST, "Quorum:1");
		serverToName.put(Constants.QUORUM2_HOST, "Quorum:2");
		serverToName.put(Constants.QUORUM3_HOST, "Quorum:3");
		serverToName.put(Constants.QUORUM4_HOST, "Quorum:4");
		serverToName.put(Constants.QUORUM5_HOST, "Quorum:5");
		serverToName.put(Constants.QUORUM6_HOST, "Quorum:6");
		serverToName.put(Constants.QUORUM7_HOST, "Quorum:7");
		return serverToName.get(host);
	}

	public static String getFileServerFromHost() {
		return "FileServer1";
	}

	public static int compareTimestamp(Timestamp t1, Timestamp t2, boolean print) {
		if (t1 == null && t2 == null)
			return 0;
		if (t1 != null && t2 == null)
			return -1;

		int c = 0;
		if (t1.after(t2)) {
			c = 1;
		} else if (t1.before(t2)) {
			c = -1;
		} else if (t1.equals(t2)) {
			c = 0;
		}
		return c;
	}

	public static void logWithSeparator(String message) {
		String separator = " ********* ******** ******** ";
		System.out.println(getTimestampForLog() + separator + message + separator);
	}

	public static int checkTimeout(Timestamp start, Timestamp end) throws Exception {
		long ms = end.getTime() - start.getTime();
		int seconds = (int) ms / 1000;
		int minutes = (seconds % 3600) / 60;
		return minutes;
	}

	public static int getTimeDifference(Timestamp start, Timestamp end) throws Exception {
		long milliseconds = end.getTime() - start.getTime();
		int seconds = (int) milliseconds / 1000;
		return seconds;
	}

	public static String getSelectedQuorumID(List<Integer> quorums) {
		String quorum = "";
		for (Integer quorumID : quorums)
			quorum = quorum + quorumID + ",";
		return quorum.substring(0, quorum.length() - 1);

	}

	public static void printSelectedQuorum2(HashMap<Integer, DataOutputStream> quorums) {
		String quorum = "";
		for (Integer quorumID : quorums.keySet())
			quorum = quorum + quorumID + ",";
		Utils.log("Selected Quorum in MutexClass:" + " --->" + quorum.substring(0, quorum.length() - 1));

	}

	public static int getWaitTime(int clientId) {
		if (clientId == 1)
			return Constants.CLIENT1_WAIT_TIME;
		else if (clientId == 2)
			return Constants.CLIENT2_WAIT_TIME;
		else if (clientId == 3)
			return Constants.CLIENT3_WAIT_TIME;
		else if (clientId == 4)
			return Constants.CLIENT4_WAIT_TIME;
		else
			return Constants.CLIENT5_WAIT_TIME;
	}
}
