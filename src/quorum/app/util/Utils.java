package quorum.app.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * @author amtul.nazneen
 */

/**
 * Utility class
 */
public class Utils {

	public static HashMap<String, String> hosttoprocess = new HashMap<String, String>();
	public static HashMap<String, String> serverToName = new HashMap<String, String>();

	public static String getTimestampForLog() {
		LocalDateTime date = LocalDateTime.now(ZoneId.of(Constants.ZONE));
		return "[" + date.format(DateTimeFormatter.ofPattern(Constants.FORMAT)) + "]";
	}

	public static void log(String message) {
		System.out.println(getTimestampForLog() + " " + message);
	}

	public static void storeToOutputFile(String message, int process, String task, String fileName) {
		String file = Constants.FOLDER_PATH + Constants.OUTPUT_FILE;
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

	public static String getProcessFromHost(String host) {
		hosttoprocess.put(Constants.DC_PROC1, "Process:1");
		hosttoprocess.put(Constants.DC_PROC2, "Process:2");
		hosttoprocess.put(Constants.DC_PROC3, "Process:3");
		hosttoprocess.put(Constants.DC_PROC4, "Process:4");
		hosttoprocess.put(Constants.DC_PROC5, "Process:5");
		return hosttoprocess.get(host.toLowerCase());
	}

	public static String getServerNameFromCode(String code) {
		serverToName.put(Constants.SERVER_1, "Server:1");
		serverToName.put(Constants.SERVER_2, "Server:2");
		serverToName.put(Constants.SERVER_3, "Server:3");
		return serverToName.get(code);
	}

	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
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
}
