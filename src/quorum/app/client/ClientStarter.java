package quorum.app.client;

import quorum.app.util.Utils;

public class ClientStarter {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			Utils.log("No Client ID provided.");
			return;
		}
		String id = args[0];
		/*
		 * if ("1".equals(id)) { Utils.logWithSeparator("Starting Client ID:1"); } if
		 * ("2".equals(id)) { Utils.logWithSeparator("Starting Client ID:2"); }
		 */
		Client client = new Client(Integer.valueOf(id));
		client.startClient();
	}
}
