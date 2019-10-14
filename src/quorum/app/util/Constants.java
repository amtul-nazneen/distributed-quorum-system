package quorum.app.util;

/**
 * @author amtul.nazneen
 */
/**
 * Utility class to hold the constants/configurations
 */
public class Constants {
	/*
	 * ------ Section I: Begin of: No. of random requests to be generated for each
	 * client ------
	 */
	public static final int CLIENT1_CSLIMIT = 25;
	public static final int CLIENT2_CSLIMIT = 25;
	public static final int CLIENT3_CSLIMIT = 25;
	public static final int CLIENT4_CSLIMIT = 25;
	public static final int CLIENT5_CSLIMIT = 25;

	/* ------ Section II: Begin of DC machines to program model mapping ------ */
	public static final int SERVER_PORT = 6666;
	public static final int CLIENT2_PORT = 6662;
	public static final int CLIENT3_PORT = 6663;
	public static final int CLIENT4_PORT = 6664;
	public static final int CLIENT5_PORT = 6665;

	public static final String DC_PROC1 = "dc04.utdallas.edu";
	public static final String DC_PROC2 = "dc05.utdallas.edu";
	public static final String DC_PROC3 = "dc06.utdallas.edu";
	public static final String DC_PROC4 = "dc07.utdallas.edu";
	public static final String DC_PROC5 = "dc08.utdallas.edu";

	public static final String SERVER1_HOST = "dc01.utdallas.edu";
	public static final String SERVER2_HOST = "dc02.utdallas.edu";
	public static final String SERVER3_HOST = "dc03.utdallas.edu";
	/*
	 * ------ Section III: Begin of: Thread.sleep value for each client, between
	 * critical sections ------
	 */
	public static final int CLIENT1_CSEXEC = 3000;
	public static final int CLIENT2_CSEXEC = 2000;
	public static final int CLIENT3_CSEXEC = 3500;
	public static final int CLIENT4_CSEXEC = 4000;
	public static final int CLIENT5_CSEXEC = 5000;
	/*
	 * ------ Section IV: Begin of:Misc. configurations ------
	 */
	public static final boolean ENABLE_SOCKET_CLOSE = true;
	public static final int CLIENT_TIMEOUT = 10;
	public static final int SERVER_TIMEOUT = 20;
	/*
	 * ------ Section V: Begin of:Misc. configurations ------
	 */
	public static final int PROCESS_CHANNELS = 4;
	public static final int TOTAL_CLIENTS = 5;
	public static final int CHECK_PENDING_COUNT = 5;

	public static final String WRITE_MESSAGE = "Written by Process:";

	public static final String SERVER_1 = "s1";
	public static final String SERVER_2 = "s2";
	public static final String SERVER_3 = "s3";

	public static final String FOLDER_PATH = "/home/eng/a/axn180041/mutex/";
	public static final String FILE_EXT = ".txt";
	public static final String EOL = "\n";

	public static final String FILE1_NAME = "file1.txt";
	public static final String FILE2_NAME = "file2.txt";
	public static final String FILE3_NAME = "file3.txt";
	public static final String FILE4_NAME = "file4.txt";
	public static final String OUTPUT_FILE = "Output.txt";

	public static final String READ = "read";
	public static final String WRITE = "write";
	public static final String ENQUIRE = "enquire";
	public static final String REPLY = "REPLY";
	public static final String REQUEST = "REQUEST";

	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String ZONE = "GMT-05:00";

}
