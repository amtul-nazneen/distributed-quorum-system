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

	/* ------ Section II: Begin of DC machines to program model mapping ------ */
	public static final int SERVER_PORT = 6666;

	public static final String CLIENT1 = "dc11.utdallas.edu";
	public static final String CLIENT2 = "dc12.utdallas.edu";
	public static final String CLIENT3 = "dc13.utdallas.edu";
	public static final String CLIENT4 = "dc14.utdallas.edu";
	public static final String CLIENT5 = "dc15.utdallas.edu";

	public static final String QUORUM1_HOST = "dc01.utdallas.edu";
	public static final String QUORUM2_HOST = "dc02.utdallas.edu";
	public static final String QUORUM3_HOST = "dc03.utdallas.edu";
	public static final String QUORUM4_HOST = "dc04.utdallas.edu";
	public static final String QUORUM5_HOST = "dc05.utdallas.edu";
	public static final String QUORUM6_HOST = "dc06.utdallas.edu";
	public static final String QUORUM7_HOST = "dc07.utdallas.edu";

	public static final String FILESERVER_HOST = "dc10.utdallas.edu";
	/*
	 * ------ Section III: Begin of: Thread.sleep value for each client, between
	 * critical sections ------
	 */
	/*
	 * ------ Section IV: Begin of:Misc. configurations ------
	 */
	public static final boolean ENABLE_SOCKET_CLOSE = false;
	public static final int CLIENT_TIMEOUT = 10;
	public static final int SERVER_TIMEOUT = 20;
	public static final int TOTAL_REQUESTS = 5;
	public static final int TOTAL_CLIENTS = 1;
	/*
	 * ------ Section V: Begin of:Misc. configurations ------
	 */

	public static final String SERVER_WRITE_MESSAGE = "Request from:---->";
	public static final String SERVER_SUCCESS = "success";

	public static final String SERVER_0 = "s0";

	public static final String FOLDER_PATH = "/home/eng/a/axn180041/quorum/";
	public static final String FILE_EXT = ".txt";
	public static final String EOL = "\n";

	public static final String FILE0_NAME = "file0.txt";
	public static final String OUTPUT_FILE = "Output.txt";

	public static final String REQUEST = "REQUEST";

	/*
	 * ------ QUORUM BEGINS
	 */
	public static final String LOCKED = "locked";
	public static final String UNLOCKED = "unlocked";
	public static final String GRANT = "grant";
	public static final String WRITE = "write";
	public static final String RELEASE = "release";
	public static final String COMPLETE = "complete";
	public static final String COMPLETE_ACK = "complete-ack";
	public static final String FILE_SERVER = "file";
	public static final String QUORUM_SERVER = "quorum";

}
