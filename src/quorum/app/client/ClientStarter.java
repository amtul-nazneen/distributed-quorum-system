package quorum.app.client;

import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Main Client that starts the requested client for a given ClientID
 */
public class ClientStarter {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			Utils.log("No Client ID provided.");
			return;
		}
		String id = args[0];
		Client client = new Client(Integer.valueOf(id));
		client.startClient();
	}
}
