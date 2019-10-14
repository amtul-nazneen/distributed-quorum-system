package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import quorum.app.util.Constants;

public class CompareFiles {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("No file number provided.");
			return;
		}
		String fno = args[0];
		String file = "";
		if ("1".equals(fno)) {
			file = Constants.FILE1_NAME;
		} else if ("2".equals(fno)) {
			file = Constants.FILE2_NAME;
		} else if ("3".equals(fno)) {
			file = Constants.FILE3_NAME;
		} else {
			System.out.println("Provide 1,2 or 3");
			return;
		}
		File file1 = new File(Constants.FOLDER_PATH + Constants.SERVER_1 + "/" + file);
		File file2 = new File(Constants.FOLDER_PATH + Constants.SERVER_2 + "/" + file);
		File file3 = new File(Constants.FOLDER_PATH + Constants.SERVER_3 + "/" + file);
		boolean compare1and2 = FileUtils.contentEquals(file1, file2);
		boolean compare2and3 = FileUtils.contentEquals(file2, file3);
		boolean compare1and3 = FileUtils.contentEquals(file1, file3);

		System.out.println("Are server1 and server2 files same? " + compare1and2);
		System.out.println("Are server2 and server3 files same? " + compare2and3);
		System.out.println("Are server3 and server1 files same? " + compare1and3);

	}

}
