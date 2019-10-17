package old.quorum.app.server.quorumServers;

import java.net.ServerSocket;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Main class that runs the quorum servers. Provide the server number to start
 * the respective server
 */
public class QuorumServer {
	static int clientId = 1;

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			Utils.log("No Quorum Server ID provided.");
			return;
		}
		String id = args[0];
		String serverName = null;
		if ("1".equals(id)) {
			// serverName = Constants.SERVER_1;
			Utils.logWithSeparator("Starting Server:1");
		} else if ("2".equals(id)) {
			// serverName = Constants.SERVER_2;
			Utils.logWithSeparator("Starting Server:2");
		} else if ("3".equals(id)) {
			// serverName = Constants.SERVER_3;
			Utils.logWithSeparator("Starting Server:3");
		} else if ("4".equals(id)) {
			// serverName = Constants.SERVER_4;
			Utils.logWithSeparator("Starting Server:4");
		}
		ServerSocket ss = null;
		Socket s = null;

		try {
			ss = new ServerSocket(Constants.SERVER_PORT);
			while (true) {
				s = ss.accept();
				Utils.log("Client:" + clientId + " connected");
				QuorumServerHandler clientThread = new QuorumServerHandler(s, serverName, clientId);
				Thread t = new Thread(clientThread);
				t.start();
				clientId++;

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			s.close();
			ss.close();
		}
	}
}